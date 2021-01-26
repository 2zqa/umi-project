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
            FileWriter writer = new FileWriter(filename);
            writer.write("[");
            for (int i = 0; i < weatherdata.size(); i++) {
                WeatherData data = weatherdata.get(i);
                if(i != 0){
                    writer.write(",");
                }
                writer.write("{");
                writer.write("\"stn\": \"" + data.get("STN") + "\"");
                writer.write(", ");
                writer.write("\"date\": \"" + data.get("DATE") + "\"");
                writer.write(", ");
                writer.write("\"time\": \"" + data.get("TIME") + "\"");
                writer.write(", ");
                writer.write("\"temp\": \"" + data.get("TEMP") + "\"");
                writer.write(", ");
                writer.write("\"dewp\": \"" + data.get("DEWP") + "\"");
                writer.write(", ");
                writer.write("\"stp\": \"" + data.get("STP") + "\"");
                writer.write(", ");
                writer.write("\"slp\": \"" + data.get("SLP") + "\"");
                writer.write(", ");
                writer.write("\"visib\": \"" + data.get("VISIB") + "\"");
                writer.write(", ");
                writer.write("\"wdsp\": \"" + data.get("WDSP") + "\"");
                writer.write(", ");
                writer.write("\"prcp\": \"" + data.get("PRCP") + "\"");
                writer.write(", ");
                writer.write("\"sndp\": \"" + data.get("SNDP") + "\"");
                writer.write(", ");
                writer.write("\"frshtt\": \"" + data.get("FRSHTT") + "\"");
                writer.write(", ");
                writer.write("\"cldc\": \"" + data.get("CLDC") + "\"");
                writer.write(", ");
                writer.write("\"wnddir\": \"" + data.get("WNDDIR") + "\"");
                writer.write("}");
            }
            writer.write("]");
            writer.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}