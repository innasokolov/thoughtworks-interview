package conference;

import java.io.*;

/**
 * Created by innasokolov on 8/25/16.
 */
public class Application {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java conference.Application <input_file_path> [<output_file_path>]");
            System.exit(1);
        }
        FileWriter outputFile = null;
        if (args.length > 1) {
            outputFile = new FileWriter(args[1]);
        }
        File inputFile = new File(args[0]);

        TrackManager manager = null;
        try {
            manager = new TrackManager();
            manager.init(inputFile);
        } catch (InvalidInputException e) {
            System.out.println("Check your input: " + e.getMessage());
            System.exit(1);
        }
        int i = 1;
        for (Track nextTrack : manager.getTracks()) {
            output("Track " + i++ + "\r\n", outputFile);
            for (Track.TrackEntry nextEntry : nextTrack.getTalks()) {
                output(nextEntry.toString(), outputFile);
            }
            output("\r\n", outputFile);
        }
        if (outputFile != null) outputFile.close();
    }

    private static void output(String line, FileWriter writer) throws IOException {
        System.out.println(line);
        if (writer != null) {
            writer.write(line + "\n");
            writer.flush();
        }
    }
}
