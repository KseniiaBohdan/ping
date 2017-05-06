package uzparser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uzparser.info.Stations.getStationByName;

public class Parser {
    String html = "";
    String cookie = "";
    String token = "";
    String error = "";

    Map<String, List<String>> headers = null;

    void fetchHtml() {
        try {
            URL url = new URL("http://booking.uz.gov.ua/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            headers = conn.getHeaderFields();
            String line;
            while ((line = rd.readLine()) != null) {
                html += line;
            }
            rd.close();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }

    void parseCookie() {
        List<String> cookies = headers.get("Set-Cookie");
        for (String current_cookie : cookies) {
            if (current_cookie.startsWith("_gv_sessid")) {
                cookie = current_cookie;
                break;
            }
        }
    }

    void parseToken() {
        String adapter = "var token;localStorage={setItem:function(key, value){if(key==='gv-token')token=value}};";
        Pattern pattern = Pattern.compile("\\$\\$_=.*~\\[\\];.*\"\"\\)\\(\\)\\)\\(\\);");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String obfuscated = matcher.group(0);
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("JavaScript");
            try {
                engine.eval(adapter + obfuscated);
            } catch (ScriptException e) {
                error = e.getMessage();
            }
            token = engine.get("token").toString();
        }
    }

    public String getData(String fromName, String toName, String date) {
        fetchHtml();
        parseCookie();
        parseToken();
        String from = getStationByName(fromName);
        String to = "2208340";//getStationId(toName);
        String json = "";
        try {
            URL url = new URL("http://booking.uz.gov.ua/en/purchase/search/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Cookie", cookie);
            conn.setRequestProperty("GV-Token", token);
            conn.setRequestProperty("GV-Ajax", "1");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Referer", "http://booking.uz.gov.ua/en/");
            conn.setRequestMethod("POST");
            String urlParameters = MessageFormat.format("station_id_from={0}&station_id_till={1}&date_dep={2}" +
                    "&time_dep=00:00&time_dep_till=24:00", from, to, date);
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                json += line;
            }
            rd.close();
        } catch (Exception e) {
            error = e.getMessage();
        }
        return json;
    }
}
