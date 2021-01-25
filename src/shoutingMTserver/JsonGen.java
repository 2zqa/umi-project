package shoutingMTserver;

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
                writer.write("\"stn\": \"" + data.getStation() + "\"");
                writer.write(", ");
                writer.write("\"date\": \"" + data.getDate() + "\"");
                writer.write(", ");
                writer.write("\"time\": \"" + data.getTime() + "\"");
                writer.write(", ");
                writer.write("\"temp\": \"" + data.getTemperature() + "\"");
                writer.write(", ");
                writer.write("\"dewp\": \"" + data.getDewPoint() + "\"");
                writer.write(", ");
                writer.write("\"stp\": \"" + data.getStationLevelAirPressure() + "\"");
                writer.write(", ");
                writer.write("\"slp\": \"" + data.getSeaLevelAirPressure() + "\"");
                writer.write(", ");
                writer.write("\"visib\": \"" + data.getVisibility() + "\"");
                writer.write(", ");
                writer.write("\"wdsp\": \"" + data.getWindSpeed() + "\"");
                writer.write(", ");
                writer.write("\"prcp\": \"" + data.getPrecipitation() + "\"");
                writer.write(", ");
                writer.write("\"sndp\": \"" + data.getSnowDepth() + "\"");
                writer.write(", ");
                writer.write("\"frshtt\": \"" + data.getEvents() + "\"");
                writer.write(", ");
                writer.write("\"cldc\": \"" + data.getCloudCoverage() + "\"");
                writer.write(", ");
                writer.write("\"wnddir\": \"" + data.getWindDirection() + "\"");
                writer.write("}");
            }
            writer.write("]");
            writer.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}