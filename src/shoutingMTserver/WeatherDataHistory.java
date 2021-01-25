package shoutingMTserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class WeatherDataHistory implements Iterable<WeatherData> {
    private ArrayList<WeatherData> history;
    private int capacity;

    public WeatherDataHistory(int capacity) {
        history = new ArrayList<WeatherData>();
        this.capacity = capacity;
    }

    /**
     * Adds data to history. Cannot be run at the same time or the capacity could overload
     * @param weatherData weatherdata object to add
     */
    public synchronized void add(WeatherData weatherData) {
        // If history is full, remove last item before adding new one
        if(history.size() >= capacity) {
            history.remove(0);
        }
        history.add(weatherData);
    }

    public int size() {
        return history.size();
    }


    @Override
    public Iterator<WeatherData> iterator() {
        return history.iterator();
    }
}
