package shoutingMTserver;

import java.io.*;
import java.util.*;

public class WeatherData {

    private HashMap<String, String> attributes;
    private JsonParser jsonParser = new JsonParser();

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

    /**
     * Repairs any missing or over the top values
     * Forced to query thirty weatherdatastations at all times, otherwise cant check unreasonable data
     *
     * Will return false if there is no previous data to repair with and data is missing
     */
    public boolean repair() {
        String stationNumber = attributes.get("STN");
        boolean needsRepairing = false;

        // Check if there is previous data to repair with
        ArrayList<WeatherData> oldWeatherData = getWeatherDataArrayByStationNumber(stationNumber, 30);
        boolean canRepair = true;
        if(oldWeatherData.size() == 0) {
            canRepair = false;
        }

        // Use a copy
        for(String tag : new HashSet<>(attributes.keySet())) {
            // Skip stn, date and time
            if (tag.equals("stn") || tag.equals("date") || tag.equals("time")) {
                continue;
            }

            // Check if value is empty; if so, add new data
            String value = attributes.get(tag);
            if(value == null && canRepair) { // value = null, canRepair = true; (wel vorige data beschikbaar)
                String newValue;
                switch(tag) {
                    case "FRSHTT":
                        newValue = extrapolateByteValue(tag, oldWeatherData);
                        break;
                    case "WNDDIR":
                        newValue = String.valueOf(extrapolateIntValue(tag, oldWeatherData)); //TODO: check of extrapolatie werkt (als tijd over is)
                        break;
                    default:
                        newValue = String.valueOf(extrapolateFloatValue(tag, oldWeatherData));
                        break;

                }
                if(newValue != null && newValue.isEmpty()) {
                    System.err.println("Nieuwe waarde voor "+ tag +" is niet goed geextrapoleerd. Dit zou niet moeten gebeuren!");
                    continue;
                } else {
                    //System.out.println("Nieuwe waarde geëxtrapoleerd voor "+tag+": "+newValue);
                    needsRepairing = true;
                }
                this.add(tag, newValue);
            } else if(value == null) {
                needsRepairing = true;
            }

            // Als data vet groot of vet klein is, fix het
            if(!needsRepairing && canRepair && tag.equals("TEMP")) {
                float avg = extrapolateFloatValue(tag, oldWeatherData);
                float newVal = Float.parseFloat(attributes.get(tag));
                if(avg > newVal*1.2) {
                    newVal = (float) (newVal*1.2);
                    this.add(tag, String.valueOf(newVal));
                } else if(avg < newVal*0.8) {
                    newVal = (float) (newVal * 0.8);
                    this.add(tag, String.valueOf(newVal));
                }
            }
        }

        if(!canRepair && needsRepairing) {
            return false;
        } else {
            return true;
        }
    }

    private float extrapolateFloatValue(String tag, ArrayList<WeatherData> weatherDataArrayList) {
        switch(weatherDataArrayList.size()) {
            case 0:
                return 0;
            case 1:
                return Float.parseFloat(weatherDataArrayList.get(0).get(tag));
            default:
                float total = 0;
                int i = 0;
                while(i < weatherDataArrayList.size()) {
                    // gets the i'th weatherdata and then gets the specified tag (like DEWP)
                    total += Float.parseFloat(weatherDataArrayList.get(i).get(tag));
                    i++;
                }
                return total/i;
        }
    }

    private String extrapolateByteValue(String tag, ArrayList<WeatherData> weatherDataArrayList) {
        // dit kan in theorie niet gebeuren
        if(weatherDataArrayList.size() == 0) {
            return null;
        }
        String value = weatherDataArrayList.get(0).get(tag);
        return value;
    }



    /**
     * Takes a list of weatherdata stations and calculates a new value based on the previous ones
     * @param tag what field to calculate from
     * @param weatherDataArrayList the list of weatherdata objects
     * @return
     */
    private int extrapolateIntValue(String tag, ArrayList<WeatherData> weatherDataArrayList) {
        if(weatherDataArrayList.size() == 0) {
            return 0;
        }
        int total = 0;
        int i = 0;
        while(i < weatherDataArrayList.size()) {
            // gets the i'th weatherdata and then gets the specified tag (like WNDDIR)
            total += Integer.parseInt(weatherDataArrayList.get(i).get(tag));
            i++;
        }

        return total/i;
    }

    public ArrayList<WeatherData> getWeatherDataArrayByStationNumber(String stnummer, int maxFiles) {
        ArrayList<WeatherData> dataArray = new ArrayList<WeatherData>();

        File path = new File(JsonGen.PATH + stnummer);
        File[] directoryListing = path.listFiles();
        if (directoryListing != null) {
            Arrays.sort(directoryListing, Collections.reverseOrder());
            int fileCount = directoryListing.length;
            if(fileCount > maxFiles) {
                fileCount = maxFiles;
            }
            for (int i = 0; i < fileCount; i++) {
                File child = directoryListing[i];
                try(BufferedReader reader = new BufferedReader(new FileReader(child))) {
                    // Get first line of file (there should be only one anyway)
                    String text = reader.readLine();

                    // Parse and add to array
                    WeatherData data = new WeatherData();
                    jsonParser.parse(text, data);
                    dataArray.add(data);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataArray;
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
