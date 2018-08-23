package conference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by innasokolov on 8/25/16.
 */
public class TrackManagerTest {

    private File goodFile;
    private File emptyFile;
    private File malformedFile;

    @Before
    public void setUp() throws Exception {
        goodFile = new File("src/test/resources/test.txt");
        emptyFile = new File("src/test/resources/empty_list.txt");
        malformedFile = new File("src/test/resources/malformed.txt");
    }

    @Test
    public void testInit() throws Exception {
        TrackManager manager = new TrackManager();
        manager.init(goodFile);
        int i = 0;
        for (Track nextTrack : manager.getTracks()) {
            i+=nextTrack.getTalks().size();
        }
        Assert.assertEquals(23, i); // 19 from the file + 2 lunches and + 2 final networking events
    }

    @Test
    public void testEmptyFile() throws Exception {
        try {
            TrackManager manager = new TrackManager();
            manager.init(emptyFile);
        } catch (InvalidInputException e) {
            Assert.assertEquals("Your file is empty", e.getMessage());
            return;
        }
        Assert.fail("Should have thrown empty file error");
    }

    @Test(expected = InvalidInputException.class)
    public void testMalformedFile() throws Exception {
        TrackManager manager = new TrackManager();
        manager.init(malformedFile);
    }
}
