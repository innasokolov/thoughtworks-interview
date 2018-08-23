package conference;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * Created by innasokolov on 8/25/16.
 */
public class TrackTest {

    @Test
    public void testInstantiateTrackWithDefaults() throws Exception {
        Track track = new Track();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(track.getStartTime());
        Assert.assertEquals(9, calendar.get(Calendar.HOUR));
        calendar.setTime(track.getLunchTime());
        Assert.assertEquals(0, calendar.get(Calendar.MINUTE));
        calendar.setTime(track.getEarliestNetworkEventTime());
        Assert.assertEquals(4, calendar.get(Calendar.HOUR));
        calendar.setTime(track.getLatestNetworkEventTime());
        Assert.assertEquals(5, calendar.get(Calendar.HOUR));
    }

    @Test
    public void testCanAdd() throws Exception {
        Track track = new Track();

        Talk bigTalk = new Talk("All Morning", 180);
        Assert.assertTrue(track.canAdd(bigTalk));
        track.add(bigTalk);
        bigTalk = new Talk("All Afternoon", 180);
        Assert.assertTrue(track.canAdd(bigTalk));
        track.add(bigTalk);
        bigTalk = new Talk("One last talk please", 180);
        Assert.assertFalse(track.canAdd(bigTalk));
    }

    @Test
    public void testAddSuccess() throws Exception {
        Track track = new Track();

        Talk bigTalk = new Talk("All Morning", 180);
        track.add(bigTalk);
        Assert.assertEquals(2, track.getTalks().size()); // + 1 network event

        bigTalk = new Talk("All Afternoon", 180);
        track.add(bigTalk);

        Assert.assertEquals(4, track.getTalks().size()); // + 1 network event + 1 lunch
    }

    @Test(expected = TrackIsFullException.class)
    public void testAddException() throws Exception {
        Track track = new Track();

        Talk bigTalk = new Talk("All Morning", 180);
        track.add(bigTalk);
        bigTalk = new Talk("All Afternoon", 180);
        track.add(bigTalk);
        bigTalk = new Talk("One last talk please", 180);
        track.add(bigTalk);
    }

    @Test
    public void testGet() throws Exception {
        Track track = new Track();

        Assert.assertEquals(0, track.getTalks().size());

        Talk bigTalk = new Talk("All Morning", 180);
        track.add(bigTalk);

        Assert.assertEquals(2, track.getTalks().size());
        Assert.assertEquals("All Morning", track.getTalks().get(0).getTalk().getName());
        Assert.assertEquals("Networking Event", track.getTalks().get(1).getTalk().getName());

        bigTalk = new Talk("All Afternoon", 180);
        track.add(bigTalk);

        Assert.assertEquals(4, track.getTalks().size());
        Assert.assertEquals("All Morning", track.getTalks().get(0).getTalk().getName());
        Assert.assertEquals("Lunch", track.getTalks().get(1).getTalk().getName());
        Assert.assertEquals("All Afternoon", track.getTalks().get(2).getTalk().getName());
        Assert.assertEquals("Networking Event", track.getTalks().get(3).getTalk().getName());
    }
}
