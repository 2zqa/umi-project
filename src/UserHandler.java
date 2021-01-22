import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("measurement")) {
            System.out.println("End Element :" + qName);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (length > 0) {


            if (bstn) {
                //System.out.println("Stn: " + new String(ch, start, length));
                //weatherData.setStation = new String(ch, start, length);
                bstn = false;
            } else if (bdate) {
                System.out.println("Date: " + new String(ch, start, length));
                bdate = false;
            } else if (btime) {
                System.out.println("Time: " + new String(ch, start, length));
                btime = false;
            } else if (btemp) {
                System.out.println("Temp: " + new String(ch, start, length));
                btemp = false;
            } else if (bdewp) {
                System.out.println("Dewp: " + new String(ch, start, length));
                bdewp = false;
            } else if (bstp) {
                System.out.println("Stp: " + new String(ch, start, length));
                bstp = false;
            } else if (bslp) {
                System.out.println("Slp: " + new String(ch, start, length));
                bslp = false;
            } else if (bvisib) {
                System.out.println("Visib: " + new String(ch, start, length));
                bvisib = false;
            } else if (bwdsp) {
                System.out.println("Wdsp: " + new String(ch, start, length));
                bwdsp = false;
            } else if (bprcp) {
                System.out.println("Prcp: " + new String(ch, start, length));
                bprcp = false;
            } else if (bsndp) {
                System.out.println("Sndp: " + new String(ch, start, length));
                bsndp = false;
            } else if (bfrshtt) {
                System.out.println("frshtt: " + new String(ch, start, length));
                bfrshtt = false;
            } else if (bcldc) {
                System.out.println("Cldc: " + new String(ch, start, length));
                bcldc = false;
            } else if (bwnddir) {
                System.out.println("Wnddir: " + new String(ch, start, length));
                bwnddir = false;
            }
        }

    }
}