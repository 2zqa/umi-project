package shoutingMTserver;

import java.util.HashMap;

public class WeatherData {

    private HashMap<String, String> attributes;

    public WeatherData() {
        attributes = new HashMap<String, String>();
        attributes.put("STN", null);
        attributes.put("DATE", null);
        attributes.put("TIME", null);
        attributes.put("TEMP", null);
        attributes.put("DEWP", null);
        attributes.put("STP", null);
        attributes.put("SLP", null);
        attributes.put("VISIB", null);
        attributes.put("WDSP", null);
        attributes.put("PRCP", null);
        attributes.put("SNDP", null);
        attributes.put("FRSHTT", null);
        attributes.put("CLDC", null);
        attributes.put("WNDDIR", null);
    }

    /**
     * Adds value to the class if the tag exists.
     * @param tag can be STN, DATE, TIME, TEMP, DEWP, STP, SLP, VISIB, WDSP, PRCP, SNDP, FRSHTT, CLDC or WNDDIR
     * @param value the value
     */
    public void add(String tag, String value) {
        attributes.replace(tag, value);
    }

    public String get(String tag) {
        return attributes.get(tag);
    }

    // Repairs any missing or over the top values
    public void repair() {
        for(String tag : attributes.keySet()) {
            if(tag == null){
                int intTag = Integer.parseInt(tag);
                int together = 0;
                for(int i = 0; i<=9; i++){
                    int newTogether = together + intTag;
                    together = newTogether;
                int mean = together/10;
                String newTag = String.valueOf(mean);
                add(tag, newTag);
                }
            }
        }
    }

    public void export() {

    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(String key : attributes.keySet()) {
            String value = attributes.get(key);
            output.append(key + ":" + value + " ");
        }

        return output.toString();
    }
}
