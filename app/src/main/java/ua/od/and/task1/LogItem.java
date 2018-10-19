package ua.od.and.task1;

import android.location.Location;

public class LogItem {
    private Location location;
    private String label;
    private int distanceFromPreviousLocationM;

    LogItem(Location location, String label, int distanceFromPreviousLocationM) {
        this.location = location;
        this.label = label;
        this.distanceFromPreviousLocationM = distanceFromPreviousLocationM;
    }

    public Location getLocation() {
        return location;
    }

    public String getLabel() {
        return label;
    }

    public int getDistanceFromPreviousLocationM() {
        return distanceFromPreviousLocationM;
    }
}
