package shoutingMTserver;

import java.util.Locale;

/**
 * This class is heavily aimed at parsing very specific weatherdata formats.
 */
public class WeatherDataXMLParser {

    /**
     * Takes a line, for example <STN>1039834</STN> and save it in data class
     * @param line
     */
    public void parse(String line, WeatherData data) {
        // If there is no data, stop
        if(!line.matches(".*\\d.*")) {
            //System.out.println("Geen getal in " + line);
            return;
        }

        //Remove any whitespace
        line = line.trim();

        // Gets tagname
        String tag = line.substring(1,line.indexOf(">")).toUpperCase();

        // Gets value by removing tags
        String value = line.replaceAll("<.+?>", "");

        data.add(tag, value);
    }
}
