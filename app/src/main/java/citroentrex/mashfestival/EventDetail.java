package citroentrex.mashfestival;

import java.util.Date;

public class EventDetail {
    private String eventName;
    private long eventTime;

    public EventDetail(String eventName, long eventTime) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        eventTime = new Date().getTime();
    }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public long getEventTime() { return eventTime; }
    public void setEventTime(long eventTime) { this.eventTime = eventTime; }
}
