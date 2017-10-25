package googlemap.mysqlx18.com.googlemap;

import java.util.Date;

/**
 * Created by marct_000 on 10/25/2017.
 */

public class GPSData {

    String userID;
    double longitude;
    double latitude;
    double speed;
    double altitude;
    long gpsTime;
    Date gpsDate;

    public GPSData(){

    }

    public GPSData(String userID, double longitude, double latitude, double speed, double altitude, long gpsTime, Date gpsDate) {
        this.userID = userID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
        this.altitude = altitude;
        this.gpsTime = gpsTime;
        this.gpsDate = gpsDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public long getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(long gpsTime) {
        this.gpsTime = gpsTime;
    }

    public Date getGpsDate() {
        return gpsDate;
    }

    public void setGpsDate(Date gpsDate) {
        this.gpsDate = gpsDate;
    }
}
