package conference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by innasokolov on 8/25/16.
 */
public class TalkFactory {
    private static final String lightning = "lightning";

    private static TalkFactory factory = new TalkFactory();

    public static TalkFactory getFactory() {
        return factory;
    }

    public static void setFactory(TalkFactory aFactory) {
        factory = aFactory;
    }

    public static void reset() {
        setFactory(new TalkFactory());
    }

    public Talk createTalk(int lineNumber, String textLine) {
        Talk talk;
        if (textLine == null || textLine.isEmpty()) throw  new InvalidInputException("Line " + lineNumber + " is empty");
        String[] tokens = textLine.trim().split("\\s");
        StringBuffer name = new StringBuffer();
        int durationInMinutes = 0;

        Pattern p = Pattern.compile("^-??\\d+min$");
        Matcher m;

        int i = 0;
        while (i < tokens.length - 1) {
            name.append(tokens[i] + " ");
            i++;
        }
        m = p.matcher(tokens[i]);
        if (m.matches()) durationInMinutes = createDuration(tokens[i]);
        else if (lightning.equals(tokens[i])) durationInMinutes = 5;
        else throw new InvalidInputException("Talk duration is missing/inavlid in line number " + lineNumber);

        if (durationInMinutes <= 0) throw new InvalidInputException("Talk duration in line " + lineNumber + " should be positive number: " + durationInMinutes);
        if (name.length() <= 0) throw new InvalidInputException("Talk name is missing in line number " + lineNumber);
        talk = new Talk(name.toString().trim(), durationInMinutes);
        return talk;
    }

    private int createDuration(String value) {
        return Integer.valueOf(value.replace("min", "")).intValue();
    }

}
