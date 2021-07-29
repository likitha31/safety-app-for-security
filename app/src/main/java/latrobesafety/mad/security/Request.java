package latrobesafety.mad.security;
import java.io.Serializable;
import java.util.Date;


public class Request implements Serializable
{
    private Date date;
    private int priority; //value 1 = immediate response, 2 = pick up request
    private String message;
    private STATUS status;
    private int refNum;
    private String name;

    public static enum STATUS {
        ACTIVE,
        ON_GOING,
        COMPLETE,
    };

    public Request()
    {
        //need empty constructor
    }

    public Request(String name, int priority, String message, Date date)
    {
        this.date = date;
        this.priority = priority;
        this.message = message;
        this.name = name;
        this.status = STATUS.ACTIVE;
        this.refNum = (int) Math.floor(Math.random() * 1000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCurrentDate() {
        return date;
    }

    public void setCurrentDate(Date currentDate) {
        this.date= currentDate;
    }

    public void setRefNum(int refNum) {
        this.refNum = refNum;
    }

    public int getPriority()
    {
        return priority;
    }

    public int getRefNum() {
        return refNum;
    }

    public String getMessage()
    {
        return message;
    }

    public STATUS getStatus()
    {
        return status;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(STATUS status)
    {
        this.status = status;
    }

    public String toString()
        { return "Name: " + name + "Priority: " + priority + "\n" +
        "Message: " + message +
                "\nSTATUS: " + status + "\nReference_Number: " + refNum;
        }

}
