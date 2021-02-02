package shoutingMTserver;

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
        int firstOpenBracketIndex = line.indexOf('<');
        if(firstOpenBracketIndex == -1) {
            return;
        }
        int lastOpenBracketIndex = line.lastIndexOf('<');
        // If there is only one open bracket, we don't need to parse the line
        if(lastOpenBracketIndex == firstOpenBracketIndex) {
            return;
        }
        int firstCloseBracketIndex = line.indexOf('>');

        // Gets tagname
        String tag = line.substring(firstOpenBracketIndex+1,firstCloseBracketIndex);

        // Gets value by removing tags
        String value = line.substring(firstCloseBracketIndex+1,lastOpenBracketIndex);

        data.add(tag, value);
    }
}
