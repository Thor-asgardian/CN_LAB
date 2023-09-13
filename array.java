import java.util.Arrays;
import java.util.Comparator;

// Define a Frame class with frame number and other attributes
class Frame {
    int frameNumber;
    // Add other attributes as needed

    public Frame(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    // Add getters and setters for other attributes as needed

    @Override
    public String toString() {
        return "Frame " + frameNumber;
    }
}

public class FrameSorting {
    public static void main(String[] args) {
        // Create an array of frames
        Frame[] frames = {
            new Frame(3),
            new Frame(1),
            new Frame(4),
            new Frame(2),
            // Add more frames as needed
        };

        // Sort the frames based on their frame numbers
        Arrays.sort(frames, Comparator.comparingInt(frame -> frame.frameNumber));

        // Print the sorted frames
        System.out.println("Sorted Frames:");
        for (Frame frame : frames) {
            System.out.println(frame);
        }
    }
}
