package shoutingMTserver;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Populates WeatherData objects with data
 */
class UserHandler extends DefaultHandler {

    boolean bstn = false;
    boolean bdate = false;
    boolean btime = false;
    boolean btemp = false;
    boolean bdewp = false;
    boolean bstp = false;
    boolean bslp = false;
    boolean bvisib = false;
    boolean bwdsp = false;
    boolean bprcp = false;
    boolean bsndp = false;
    boolean bfrshtt = false;
    boolean bcldc = false;
    boolean bwnddir = false;
    WeatherData weatherData = new WeatherData();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("stn")) {
            bstn = true;
        } else if (qName.equalsIgnoreCase("date")) {
            bdate = true;
        } else if (qName.equalsIgnoreCase("time")) {
            btime = true;
        } else if (qName.equalsIgnoreCase("temp")) {
            btemp = true;
        } else if (qName.equalsIgnoreCase("dewp")) {
            bdewp = true;
        } else if (qName.equalsIgnoreCase("stp")) {
            bstp = true;
        } else if (qName.equalsIgnoreCase("slp")) {
            bslp = true;
        } else if (qName.equalsIgnoreCase("visib")) {
            bvisib = true;
        } else if (qName.equalsIgnoreCase("wdsp")) {
            bwdsp = true;
        } else if (qName.equalsIgnoreCase("prcp")) {
            bprcp = true;
        } else if (qName.equalsIgnoreCase("sndp")) {
            bsndp = true;
        } else if (qName.equalsIgnoreCase("frshtt")) {
            bfrshtt = true;
        } else if (qName.equalsIgnoreCase("cldc")) {
            bcldc = true;
        } else if (qName.equalsIgnoreCase("wnddir")) {
            bwnddir = true;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End of file. Weatherdata: (NOTICE! Still raw, untampered data) \n" + weatherData + "\n");
        WeatherData.previousData.add(weatherData);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // Stop if not a value
        String rawString = new String(ch, start, length);
        if(rawString.trim().isEmpty()) {
            return;
        }

        // Populating weatherData object
        if (bstn) {
            //System.out.println("Stn: " + new String(ch, start, length));
            weatherData.setStation(rawString);
            bstn = false;
        } else if (bdate) {
            weatherData.setDate(rawString);
            bdate = false;
        } else if (btime) {
            weatherData.setTime(rawString);
            btime = false;
        } else {
            float floatValue = Float.parseFloat(new String(ch, start, length));
            if (btemp) {
                weatherData.setTemperature(floatValue);
                btemp = false;
            } else if (bdewp) {
                weatherData.setDewPoint(floatValue);
                bdewp = false;
            } else if (bstp) {
                weatherData.setStationLevelAirPressure(floatValue);
                bstp = false;
            } else if (bslp) {
                weatherData.setSeaLevelAirPressure(floatValue);
                bslp = false;
            } else if (bvisib) {
                weatherData.setVisibility(floatValue);
                bvisib = false;
            } else if (bwdsp) {
                weatherData.setWindSpeed(floatValue);
                bwdsp = false;
            } else if (bprcp) {
                weatherData.setPrecipitation(floatValue);
                bprcp = false;
            } else if (bsndp) {
                weatherData.setSnowDepth(floatValue);
                bsndp = false;
            } else if (bfrshtt) {
                try {
                    weatherData.setEvents(Byte.parseByte(rawString, 2));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid event-data: \"" + rawString + "\". Field will remain empty!");
                }
                bfrshtt = false;
            } else if (bcldc) {
                weatherData.setCloudCoverage(floatValue);
                bcldc = false;
            } else if (bwnddir) {
                weatherData.setWindDirection(Integer.parseInt(rawString));
                bwnddir = false;
            }
        }

    }
}