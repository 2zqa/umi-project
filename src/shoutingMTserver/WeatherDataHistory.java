package shoutingMTserver;

import java.util.ArrayList;

public class WeatherDataHistory {
    private ArrayList<WeatherData> history;
    private int capacity;

    public WeatherDataHistory(int capacity) {
        history = new ArrayList<WeatherData>();
        this.capacity = capacity;
    }

    public void add(WeatherData weatherData) {
        // If history is full, remove last item before adding new one
        if(history.size() >= capacity) {
            history.remove(0);
        }
        history.add(weatherData);
    }
}
