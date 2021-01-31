package shoutingMTserver;

public class JsonParser {

    public void parse(String line, WeatherData data) {
        //Remove any whitespace or brackets
        line = line.trim();
        String tag = line.substring(1,line.length()-1).toUpperCase();

        // split the key-value pairs
        String[] stringArray = tag.split(",");

        for (String string: stringArray) {
            String[] kvPaar = string.split(":",2);

            kvPaar[0] = kvPaar[0].replaceAll("\"", "");
            kvPaar[1] = kvPaar[1].replaceAll("\"", "");
            data.add(kvPaar[0], kvPaar[1]);
        }
    }
}

