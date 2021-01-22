public class WeatherData {

    String station;
    String date;
    String time;
    float temperature;
    float dewPoint;
    float stationLevelAirPressure;
    float seaLevelAirPressure;
    float visibility;
    float windSpeed;
    float precipitation; // means rainfall
    float snowDepth;
    byte events; // freezing, raining, snowing, hailing, storming, tornado
    float cloudCoverage;
    int windDirection;

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


}
