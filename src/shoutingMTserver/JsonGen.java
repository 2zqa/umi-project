package shoutingMTserver;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

public class JsonGen {
    public static final String ROOT_FOLDER = "weatherdata-mounting-point";
    private String path = System.getProperty("user.dir").concat(File.separator+ROOT_FOLDER+File.separator); // /home/user/
    private HashMap<String, ArrayList<WeatherData>> weatherDataMap;

    //Scheduler
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public JsonGen() {
        weatherDataMap = new HashMap<String, ArrayList<WeatherData>>();
    }

    /**
     * Adds weatherdata object to array
     *
     * @param data
     */
    public void addWeatherData(WeatherData data) {
        String stationNumber = data.get("STN");
        if (weatherDataMap.containsKey(stationNumber)) {
            weatherDataMap.get(stationNumber).add(data);
        } else {
            ArrayList<WeatherData> weatherDataArrayList = new ArrayList<WeatherData>();
            weatherDataArrayList.add(data);
            weatherDataMap.put(stationNumber, weatherDataArrayList);
        }
    }

    public void removeAllData() {
        weatherDataMap.clear();
    }

    //TODO: itereer door keyset, pak array die bij stationsnummer hoort, maak json bestand met al die weatherdataobjecten en schrijf naar bestand (met juist mapje)
    public void toJson(String filename) {

        for (String stationNumber : weatherDataMap.keySet()) {
            // Create directory if it does not exist
            String folder = path.concat(File.separator + stationNumber);
            File file = new File(folder); // bv C:\Users\mshko\12710\
            //System.out.println(file.getAbsolutePath());
            file.mkdirs();

            // Iterate through stations
            ArrayList<WeatherData> stations = weatherDataMap.get(stationNumber);

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(folder+File.separator+filename))) {
                writer.write("[");
                for (int i = 0; i < stations.size(); i++) {
                    WeatherData data = stations.get(i);
                    if(i != 0){
                        writer.write(",");
                    }
                    writer.write("{");
                    writer.write("\"stn\":\"" + data.get("STN") + "\"");
                    writer.write(", ");
                    writer.write("\"date\":\"" + data.get("DATE") + "\"");
                    writer.write(", ");
                    writer.write("\"time\":\"" + data.get("TIME") + "\"");
                    writer.write(", ");
                    writer.write("\"temp\":\"" + data.get("TEMP") + "\"");
                    writer.write(", ");
                    writer.write("\"dewp\":\"" + data.get("DEWP") + "\"");
                    writer.write(", ");
                    writer.write("\"stp\":\"" + data.get("STP") + "\"");
                    writer.write(", ");
                    writer.write("\"slp\":\"" + data.get("SLP") + "\"");
                    writer.write(", ");
                    writer.write("\"visib\":\"" + data.get("VISIB") + "\"");
                    writer.write(", ");
                    writer.write("\"wdsp\":\"" + data.get("WDSP") + "\"");
                    writer.write(", ");
                    writer.write("\"prcp\":\"" + data.get("PRCP") + "\"");
                    writer.write(", ");
                    writer.write("\"sndp\":\"" + data.get("SNDP") + "\"");
                    writer.write(", ");
                    writer.write("\"frshtt\":\"" + data.get("FRSHTT") + "\"");
                    writer.write(", ");
                    writer.write("\"cldc\":\"" + data.get("CLDC") + "\"");
                    writer.write(", ");
                    writer.write("\"wnddir\":\"" + data.get("WNDDIR") + "\"");
                    writer.write("}");
                }
            writer.write("]");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    // dumps all JSON data to files every second
    public void dumpJsonEverySecond() {
        final Runnable beeper = new Runnable() {
            public void run() {
                // json bestand maken en de generator legen BELANGRIJK: zoals de data nu wordt opgeslagen is niet handig, moet nog ff nadenken over naamgeving
                toJson(Instant.now().toString().replace( ":" , "" ) + ".json");
                removeAllData();
            }
        };
        final ScheduledFuture<?> writerHandle =
                scheduler.scheduleAtFixedRate(beeper, 1, 1, MINUTES);
//        scheduler.schedule(new Runnable() {
//            public void run() {
//                writerHandle.cancel(true);
//            }
//        }, 60 * 60, SECONDS);
    }
}