package uzparser.info;

import java.util.HashMap;
import java.util.Map;

public final class Stations {

    public static final Map<String, String> station = new HashMap() {
        {
            put("Odesa-Holovna", "2208001");
            put("Cherkasy", "2208340");
            put("", "");
            put("", "");
            put("", "");
            put("", "");
            put("", "");
            put("", "");
            put("", "");
            put("", "");
        }
    };

    public static Map<String, String> getStation() {
        return station;
    }

    public static String getStationByName(String name) {
        return station.get(name);
    }
}
