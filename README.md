# thoughtworks-interview


This is old interview I did with Thought Works back in 2016.

ASSUMPTIONS:

1. there is no Networking event if there are no sessions
2. there is no lunch if no afternoon sessions are scheduled



DESIGN:

Talk - simple POJO that holds talk name and duration in minutes
Track - class that holds a collection of scheduled talks
        main API:  canAdd(Talk)
                   add(Talk)

TrackFactory - class that creates a talk from a single line in the input file.
               It is responsible for validating that line and throws InvalidInputException
               when line is malformed, for instance
               - talk name is missing
               - talk duration is missing
               - talk duration is negative number

TrackManager - class responsible for reading input file and scheduling talks.
               It also validates input file and throws InvalidInputException
                if file is unreadable or empty.

Application - class with main method.





USAGE:

java conference.Application <input_file_path> [<output_file_path>]

where

    <input_file_path> is required and specifies path to the file with the list of talks
    <output_file_path> is optional and specifies path to the file with the resulting tracks of sessions.
                       if not provided tracks will be sent to system output only