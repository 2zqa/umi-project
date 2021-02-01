package shoutingMTserver;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.io.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

public class JsonGen {
    public static final String ROOT_FOLDER = "weatherdata-mounting-point";
    public static final String PATH = System.getProperty("user.dir").concat(File.separator+ROOT_FOLDER+File.separator); // /home/user/
    private HashMap<String, WeatherData> weatherDataMap;

    //Scheduler
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public JsonGen() {
        weatherDataMap = new HashMap<String, WeatherData>();
    }

    /**
     * Adds weatherdata object if its stationNumber isn't already saved
     *
     * @param data
     */
    public synchronized void addWeatherData(WeatherData data) {
        String stationNumber = data.get("STN");
        if(!weatherDataMap.containsKey(stationNumber)) {
            boolean successful = data.repair();
            if(successful) {
                weatherDataMap.put(stationNumber, data);
            } else {
                System.err.println("Fout bij repareren... wordt niet toegevoegd!");
            }
        }
    }

    public void removeAllData() {
        weatherDataMap.clear();
    }

    public void toJson(String filename) {
        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        System.out.println(date +" - Writing "+weatherDataMap.size()+" files");

        for (String stationNumber : weatherDataMap.keySet()) {
            // Create directory if it does not exist
            String folder = PATH.concat(File.separator + stationNumber);
            File file = new File(folder); // bv C:\Users\mshko\12710\
            //System.out.println(file.getAbsolutePath());
            file.mkdirs();

            // Get station
            WeatherData data = weatherDataMap.get(stationNumber);

            try(BufferedWriter writer = new BufferedWriter(new FileWriter(folder+File.separator+filename))) {
                writer.write("{");
                writer.write("\"stn\":\"" + data.get("STN") + "\"");
                writer.write(",");
                writer.write("\"date\":\"" + data.get("DATE") + "\"");
                writer.write(",");
                writer.write("\"time\":\"" + data.get("TIME") + "\"");
                writer.write(",");
                writer.write("\"temp\":\"" + data.get("TEMP") + "\"");
                writer.write(",");
                writer.write("\"dewp\":\"" + data.get("DEWP") + "\"");
                writer.write(",");
                writer.write("\"stp\":\"" + data.get("STP") + "\"");
                writer.write(",");
                writer.write("\"slp\":\"" + data.get("SLP") + "\"");
                writer.write(",");
                writer.write("\"visib\":\"" + data.get("VISIB") + "\"");
                writer.write(",");
                writer.write("\"wdsp\":\"" + data.get("WDSP") + "\"");
                writer.write(",");
                writer.write("\"prcp\":\"" + data.get("PRCP") + "\"");
                writer.write(",");
                writer.write("\"sndp\":\"" + data.get("SNDP") + "\"");
                writer.write(",");
                writer.write("\"frshtt\":\"" + data.get("FRSHTT") + "\"");
                writer.write(",");
                writer.write("\"cldc\":\"" + data.get("CLDC") + "\"");
                writer.write(",");
                writer.write("\"wnddir\":\"" + data.get("WNDDIR") + "\"");
                writer.write("}");
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes the weatherDataMap info to their respective files
     *
     */
    public void toJson() {
        final Runnable beeper = new Runnable() {
            public void run() {
                // json bestand maken en de generator legen BELANGRIJK: zoals de data nu wordt opgeslagen is niet handig, moet nog ff nadenken over naamgeving
                toJson(Instant.now().toString().replace( ":" , "" ) + ".json");
                removeAllData();
            }
        };
        final ScheduledFuture<?> writerHandle =
                scheduler.scheduleAtFixedRate(beeper, 20, 120, SECONDS);
    }
}