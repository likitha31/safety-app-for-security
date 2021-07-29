package latrobesafety.mad.security;

import java.util.Date;

public class Emergency extends Request {

    private String lat;
    private String lon;



    public Emergency( String name,String message,String lat,String lon, Date date)
    {
        super(name,1,message,date);
        //this.location = location;
        this.lat = lat;
        this.lon = lon;
    }

    public Emergency(){};

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
/* public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }*/

}
