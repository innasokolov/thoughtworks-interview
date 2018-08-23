package conference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by innasokolov on 8/25/16.
 */
public class Track {
    private Date startTime;
    private Date lunchTime;
    private Date earliestNetworkEventTime;
    private Date latestNetworkEventTime;
    private Date currentMorningOpenStart;
    private Date currentAfternoonOpenStart;
    private List<TrackEntry> morningTalks, afternoonTalks;

    public Track() {
        super();
        init();
    }
    private void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        startTime = calendar.getTime();
        currentMorningOpenStart = calendar.getTime();
        morningTalks = new ArrayList<TrackEntry>();
        calendar.set(Calendar.AM_PM, Calendar.PM);
        calendar.set(Calendar.HOUR, 0);
        lunchTime = calendar.getTime();
        calendar.set(Calendar.HOUR, 1);
        currentAfternoonOpenStart = calendar.getTime();
        calendar.set(Calendar.HOUR, 4);
        earliestNetworkEventTime = calendar.getTime();
        calendar.set(Calendar.HOUR, 5);
        latestNetworkEventTime = calendar.getTime();
        afternoonTalks = new ArrayList<TrackEntry>();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(Date lunchTime) {
        this.lunchTime = lunchTime;
    }

    public Date getEarliestNetworkEventTime() {
        return earliestNetworkEventTime;
    }

    public void setEarliestNetworkEventTime(Date earliestNetworkEventTime) {
        this.earliestNetworkEventTime = earliestNetworkEventTime;
    }

    public Date getLatestNetworkEventTime() {
        return latestNetworkEventTime;
    }

    public void setLatestNetworkEventTime(Date latestNetworkEventTime) {
        this.latestNetworkEventTime = latestNetworkEventTime;
    }

    public void add(Talk talk) throws TrackIsFullException {
        if (canAdd(currentMorningOpenStart, lunchTime, talk)) {
            morningTalks.add(new TrackEntry(currentMorningOpenStart, talk));
            currentMorningOpenStart = newCurrentTime(currentMorningOpenStart, Calendar.MINUTE, talk.getDurationInMinutes());
            return;
        } else if (canAdd(currentAfternoonOpenStart, latestNetworkEventTime, talk)) {
            afternoonTalks.add(new TrackEntry(currentAfternoonOpenStart, talk));
            currentAfternoonOpenStart = newCurrentTime(currentAfternoonOpenStart, Calendar.MINUTE, talk.getDurationInMinutes());
            return;
        } else {
            throw new TrackIsFullException("Unable to add a talk: " + talk.toString() + ". Track is full.");
        }
    }

    private Date newCurrentTime(Date currentTime, int field, int value) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentTime);
        c.add(field, value);
        return c.getTime();
    }

    public boolean canAdd(Talk talk) {
        return canAdd(currentMorningOpenStart, lunchTime, talk) || canAdd(currentAfternoonOpenStart, latestNetworkEventTime, talk);
    }

    private boolean canAdd(Date startTime, Date endTime, Talk talk) {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        c.add(Calendar.MINUTE, talk.getDurationInMinutes());

        if (c.getTime().after(endTime)) return false;
        else return true;
    }

    public List<TrackEntry> getTalks() {
        List<TrackEntry> result = new ArrayList<TrackEntry>();
        if (!morningTalks.isEmpty()) result.addAll(morningTalks);
        if (!afternoonTalks.isEmpty()) {
            TrackEntry lunch = new TrackEntry(lunchTime, new Talk("Lunch", 60));
            if (!result.contains(lunch)) result.add(lunch);
            result.addAll(afternoonTalks);
        }
        Date networkingEventTime;
        if (currentAfternoonOpenStart.before(earliestNetworkEventTime)) {
            networkingEventTime = earliestNetworkEventTime;
        } else {
            networkingEventTime = latestNetworkEventTime;
        }
        TrackEntry networkingEvent = new TrackEntry(networkingEventTime, new Talk("Networking Event", 60));
        if (!result.isEmpty() && !result.contains(networkingEvent)) result.add(networkingEvent);
        return result;
    }

    public static class TrackEntry {
        private Date startTime;
        private Talk talk;

        public TrackEntry() {
            super();
        }

        public TrackEntry(Date startTime, Talk talk) {
            this.startTime = startTime;
            this.talk = talk;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Talk getTalk() {
            return talk;
        }

        public void setTalk(Talk talk) {
            this.talk = talk;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TrackEntry)) return false;

            TrackEntry that = (TrackEntry) o;

            if (!startTime.equals(that.startTime)) return false;
            return talk.equals(that.talk);

        }

        @Override
        public int hashCode() {
            int result = startTime.hashCode();
            result = 31 * result + talk.hashCode();
            return result;
        }

        @Override
        public String toString() {
            DateFormat format = new SimpleDateFormat("hh:mm a");
            return format.format(startTime) +
                    "    " + talk.getName();
        }
    }
}
