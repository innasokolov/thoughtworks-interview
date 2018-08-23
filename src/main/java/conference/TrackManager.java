package conference;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by innasokolov on 8/25/16.
 */
public class TrackManager {

    private LinkedList<Track> tracks;

    public TrackManager() {
        super();
        tracks = new LinkedList<Track>();
    }
    public void init(File file) throws InvalidInputException, TrackIsFullException {
        InputStream inputStream = null;
        try {
            if (!file.canRead()) throw new InvalidInputException("Unable to read the file: " + file.getPath());
            if (file.length() == 0) throw new InvalidInputException("Your file is empty");
            inputStream = new FileInputStream(file);
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(inputStream));
            String nextLine;
            Talk talk;
            boolean added;
            Track track;
            while ((nextLine = reader.readLine()) != null) {
                added = false;
                talk = TalkFactory.getFactory().createTalk(reader.getLineNumber(), nextLine);
                for (Track nextTrack : getTracks()) {
                    if (nextTrack.canAdd(talk)) {
                        nextTrack.add(talk);
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    track = new Track();
                    track.add(talk);
                    tracks.add(track);
                }
            }
            inputStream.close();
        } catch (IOException e) {
            System.out.println("Error handling input stream: " + e);
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                //ignore;
            }
        }
    }

    public LinkedList<Track> getTracks() {
        return tracks;
    }
}
