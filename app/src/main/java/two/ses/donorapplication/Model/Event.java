package two.ses.donorapplication.Model;

public class Event {
    private String eventID, date, time, userID, charityID;
    public Event(){

    }
    public Event(String eventID, String date, String time, String userID, String charityID){
        this.eventID = eventID;
        this.date = date;
        this.time = time;
        this.userID = userID;
        this.charityID = charityID;
    }

    public String getCharityID() {
        return charityID;
    }

    public void setCharityID(String charityID) {
        this.charityID = charityID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEventID(String eventID) { this.eventID = eventID; }

    public String getEventID() { return eventID; }

}
