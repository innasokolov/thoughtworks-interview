package conference;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by innasokolov on 8/25/16.
 */
public class TalkFactoryTest {

    @Test
    public void testCreateValidTalkDurationInMins() throws Exception {
        String line = "Writing Fast Tests Against Enterprise Rails 60min";
        Talk result = TalkFactory.getFactory().createTalk(2, line);
        Assert.assertEquals(60, result.getDurationInMinutes());
        Assert.assertEquals("Writing Fast Tests Against Enterprise Rails", result.getName());
    }

    @Test
    public void testCreateValidTalkDurationLightning() throws Exception {
        String line = "Writing Fast Tests Against Enterprise Rails lightning";
        Talk result = TalkFactory.getFactory().createTalk(2, line);
        Assert.assertEquals(5, result.getDurationInMinutes());
        Assert.assertEquals("Writing Fast Tests Against Enterprise Rails", result.getName());
    }

    @Test(expected = InvalidInputException.class)
    public void testCreateEmptyLine() throws Exception {
        String line = "";
        TalkFactory.getFactory().createTalk(2, line);
    }

    @Test
    public void testCreateNoName() throws Exception {
        try {
            String line = "50min";
            TalkFactory.getFactory().createTalk(2, line);
        } catch (InvalidInputException e) {
            Assert.assertEquals("Talk name is missing in line number 2", e.getMessage());
            return;
        }
        Assert.fail("Should have thrown missing name exception");
    }

    @Test
    public void testCreateInvalidMissingDuration() throws Exception {
        try {
            String line = "Writing Fast Tests Against Enterprise Rails";
            TalkFactory.getFactory().createTalk(2, line);
        } catch (InvalidInputException e) {
            Assert.assertEquals("Talk duration is missing/inavlid in line number 2", e.getMessage());
            return;
        }
        Assert.fail("Should have thrown invalid/missing duration exception");
    }

    @Test
    public void testCreateNonPositiveDuration() throws Exception {
        try {
            String line = "Writing Fast Tests Against Enterprise Rails -2min";
            TalkFactory.getFactory().createTalk(2, line);
        } catch (InvalidInputException e) {
            Assert.assertEquals("Talk duration in line 2 should be positive number: -2", e.getMessage());
            return;
        }
        Assert.fail("Should have thrown non positive duration exception");
    }
}
