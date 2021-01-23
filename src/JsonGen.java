import java.util.ArrayList;
import java.io.*;

public class JsonGen {
    private ArrayList<WeatherData> weatherdata;

    public JsonGen() {
        weatherdata = new ArrayList<WeatherData>();
    }

    public void addWeatherData(WeatherData data) {
        weatherdata.add(data);
    }

    public void removeAllData() {
        weatherdata.clear();
    }

    public void toJson(String filename) {
        try {
            //File file = new File(filename);
            FileWriter writer = new FileWriter(filename);
            writer.write("[");
            for (int i = 0; i < weatherdata.size(); i++) {
                WeatherData data = weatherdata.get(i);
                if(i != 0){
                    writer.write(",");
                }
                writer.write("{");
                writer.write("stn: " + data.station);
                writer.write(", ");
                writer.write("date: " + data.date);
                writer.write(", ");
                writer.write("time: " + data.time);
                writer.write(", ");
                writer.write("temp: " + data.temperature);
                writer.write(", ");
                writer.write("dewp: " + data.dewPoint);
                writer.write(", ");
                writer.write("stp: " + data.stationLevelAirPressure);
                writer.write(", ");
                writer.write("slp: " + data.seaLevelAirPressure);
                writer.write(", ");
                writer.write("visib: " + data.visibility);
                writer.write(", ");
                writer.write("wdsp: " + data.windSpeed);
                writer.write(", ");
                writer.write("prcp: " + data.precipitation);
                writer.write(", ");
                writer.write("sndp: " + data.snowDepth);
                writer.write(", ");
                writer.write("frshtt: " + data.events);
                writer.write(", ");
                writer.write("cldc: " + data.cloudCoverage);
                writer.write(", ");
                writer.write("wnddir: " + data.windDirection);
                writer.write("}");
            }
            writer.write("]");
            writer.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}