import java.util.Scanner;
import java.util.Random;

public class SlidingWindowProtocol {
    static int windowSize, totalFrames, sendBase, nextSeqNum;
    static boolean[] acknowledged;

    // Simulate sending a frame
    static void sendFrame(int frameNumber) {
        System.out.println("Sending frame " + frameNumber);
    }

    // Simulate receiving acknowledgment with random loss
    static int receiveAck() {
        Random rand = new Random();
        // 90% chance of receiving ACK successfully
        if (rand.nextInt(100) < 90) {
            int ackFrame = sendBase + rand.nextInt(windowSize);
            System.out.println("Acknowledgment received for frame " + ackFrame);
            return ackFrame;
        } else {
            System.out.println("Acknowledgment lost!");
            return -1; // No acknowledgment
        }
    }

    // Slide the window when an ACK is received
    static void slideWindow(int ackFrame) {
        while (sendBase <= ackFrame && sendBase < totalFrames) {
            acknowledged[sendBase] = true;
            System.out.println("Frame " + sendBase + " acknowledged.");
            sendBase++;
        }
    }

    // Run the sliding window protocol
    static void runSlidingWindow() {
        while (sendBase < totalFrames) {
            // Send frames within the window
            while (nextSeqNum < sendBase + windowSize && nextSeqNum < totalFrames) {
                sendFrame(nextSeqNum);
                nextSeqNum++;
            }

            // Simulate receiving an acknowledgment
            int ackFrame = receiveAck();
            if (ackFrame != -1) {
                slideWindow(ackFrame);
            } else {
                System.out.println("Timeout! Resending frames...");
            }

            // Simulate delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the window size and total number of frames
        System.out.println("Enter the window size: ");
        windowSize = sc.nextInt();

        System.out.println("Enter the total number of frames to be sent: ");
        totalFrames = sc.nextInt();

        // Initialize variables
        acknowledged = new boolean[totalFrames];
        sendBase = 0;
        nextSeqNum = 0;

        // Run the sliding window protocol
        runSlidingWindow();

        sc.close();
    }
}