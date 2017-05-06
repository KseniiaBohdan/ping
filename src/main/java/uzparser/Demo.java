package uzparser;

class Demo {

    private static final String STATION_FROM = "Odesa-Holovna";
    private static final String STATION_TO = "Cherkasy";
    private static final String DATE = "05.05.2017";

    public static void main(String[] args) {
        Parser parser = new Parser();
        EmailSender sender = new EmailSender();

            String data = parser.getData(STATION_FROM, STATION_TO, DATE);
            sender.send(createResponse(data));

    }

    private static String createResponse(String data){
//
//        JSONObject json = new JSONObject(data);
//        String s  = json.getJSONArray("value").toString();
//        s = s.substring(1, s.length()-1);
//        json = new JSONObject(s);
//
        StringBuilder response = new StringBuilder();
        response.append("From station : " + STATION_FROM + "/n" + "To station : " + STATION_TO + "/n" + "Date : " + DATE + "/n" + "Time : ");

        return response.toString();
    }
}
