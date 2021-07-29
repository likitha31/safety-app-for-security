package latrobesafety.mad.security;


import java.io.Serializable;
import java.util.Date;

public class PickUp extends Request implements Serializable {

    private int hour;
    private int minute;
    private String building;


    public PickUp(String message, int hour, int minute,
                  String name, String building, Date date)
    {
        super(name,2,message,date);
        this.building = building;
        this.hour = hour;
        this.minute = minute;

    }

    public PickUp(){};

    public int getHour()
    {return hour;}
    public int getMinute()
    {return minute;}
    public String getBuilding()
    {return building;}
    public void setBuilding(String building) {
        this.building = building;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String toString()
    {
        return super.toString() + "\nTime: " + hour + ":" + minute + "\nBuilding: " + building;
    }


}


