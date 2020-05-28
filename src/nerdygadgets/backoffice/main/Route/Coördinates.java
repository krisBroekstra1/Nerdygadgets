package nerdygadgets.backoffice.main.Route;

public class Coördinates {
    private double longtitude;
    private double latitude;

    public Coördinates(double longtitude, double latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
