import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpURLConnectionExample {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        HttpURLConnectionExample http = new HttpURLConnectionExample();

       // System.out.println("Testing 1 - Send Http GET request");
       // http.sendGet();
        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://booking.uz.gov.ua/ru?station_from=Харьков&station_till=Славянск";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Accept", "*/*");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        request.addHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Content-Length", "162");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("Cookie", "_gv_sessid=qtfuit5p2515gik6bjcb60ulh2; HTTPSERVERID=server2; _uz_cart_personal_email=ks.13.bogdan%40gmail.com; full-version=1; _gv_lang=en; _ga=GA1.3.498215433.1488207379; _gat=1");
        request.addHeader("GV-Ajax", "1");
        request.addHeader("GV-Referer", "http://booking.uz.gov.ua/en/");
        request.addHeader("GV-Screen", "1920x1080");
        request.addHeader("GV-Token", "31f157dc25e2148711a1710d898841a1");
        request.addHeader("Host", "booking.uz.gov.ua");
        request.addHeader("Origin", "//booking.uz.gov.ua");
        request.addHeader("Referer", "//booking.uz.gov.ua/en/");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        HttpResponse response = client.execute(request);

        System.out.println("RQ LINE : " + request.getURI());
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "http://booking.uz.gov.ua/en/";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
//        urlParameters.add(new BasicNameValuePair("cn", ""));
//        urlParameters.add(new BasicNameValuePair("locale", ""));
//        urlParameters.add(new BasicNameValuePair("caller", ""));
//        urlParameters.add(new BasicNameValuePair("num", "12345"));



        urlParameters.add(new BasicNameValuePair("station_id_from", "2204001"));
        urlParameters.add(new BasicNameValuePair("station_id_till", "2218000"));
        urlParameters.add(new BasicNameValuePair("station_from", "Kharkiv"));
        urlParameters.add(new BasicNameValuePair("station_till", "Lviv"));
        urlParameters.add(new BasicNameValuePair("date_dep", "03.15.2017"));
        urlParameters.add(new BasicNameValuePair("time_dep", "00:00"));
        urlParameters.add(new BasicNameValuePair("time_dep_till", ""));
        urlParameters.add(new BasicNameValuePair("another_ec", ""));
        urlParameters.add(new BasicNameValuePair("search", ""));


        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(urlParameters);
        urlEncodedFormEntity.setContentEncoding("utf-8");
        urlEncodedFormEntity.setContentType("text/html; charset=utf-8");
        System.out.println("ENCODING : "+urlEncodedFormEntity.getContentEncoding());
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
    }

}