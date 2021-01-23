package shoutingMTserver;

import java.util.LinkedList;

public class WeatherData {
    String station; // stn
    String date;
    String time;
    float temperature; // temp
    float dewPoint; // dewp
    float stationLevelAirPressure; // stp
    float seaLevelAirPressure; // slp
    float visibility; // visib
    float windSpeed; // wdsp
    float precipitation; // prcp, means rainfall
    float snowDepth; // sndp
    byte events; // frshtt, means: freeze, rain, snow, hail, thunder, tornado
    float cloudCoverage; // cldc
    int windDirection; // wnddir

    // Sea of setters
    public void setStation(String station) {
        this.station = station;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    public void setDewPoint(float dewPoint) {
        this.dewPoint = dewPoint;
    }
    public void setStationLevelAirPressure(float stationLevelAirPressure) {
        this.stationLevelAirPressure = stationLevelAirPressure;
    }
    public void setSeaLevelAirPressure(float seaLevelAirPressure) {
        this.seaLevelAirPressure = seaLevelAirPressure;
    }
    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }
    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }
    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }
    public void setSnowDepth(float snowDepth) {
        this.snowDepth = snowDepth;
    }
    public void setEvents(byte events) {
        this.events = events;
    }
    public void setCloudCoverage(float cloudCoverage) {
        this.cloudCoverage = cloudCoverage;
    }
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * Exports the object as a JSON file in the correct folder
     */
    public void export() {
        // Todo: voor Tom

        // belangrijk: repairData moet aangeroepen worden
    }

    /**
     * Checks all fields for unrealistic or missing data and extrapolates a new value.
     *
     * Official description:
     * Een meetwaarde voor de temperatuur wordt als irreëel beschouwd indien ze 20% of meer groter is of kleiner is dan
     * wat men kan verwachten op basis van extrapolatie van de dertig voorafgaande temperatuurmetingen.
     * In dat geval wordt de geëxtrapoleerde waarde ±20% voor de temperatuur opgeslagen.
     * Voor de andere meetwaarden wordt deze handelswijze niet toegepast.
     */
    public void repairData() {
        // Todo: voor Janine en Marijn

        // Moeten array hebben van 30 vorige weatherdata klassen (die de meetdata bevat, dus)

        // Pseudocode:
        // Als getTemperature mist:
        // anders, als absolute waarde van (getTemperature-avgTemperature)/avgTemperature > 0.2 is, dan:
        //    temperatuur wordt "geëxtrapoleerde waarde ±20% voor de temperatuur" ???
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "station='" + station + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", temperature=" + temperature +
                ", dewPoint=" + dewPoint +
                ", stationLevelAirPressure=" + stationLevelAirPressure +
                ", seaLevelAirPressure=" + seaLevelAirPressure +
                ", visibility=" + visibility +
                ", windSpeed=" + windSpeed +
                ", precipitation=" + precipitation +
                ", snowDepth=" + snowDepth +
                ", events=" + events +
                ", cloudCoverage=" + cloudCoverage +
                ", windDirection=" + windDirection +
                '}';
    }
}
