import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

        Graph g = Graph.readGraph(in, false);

        System.out.println("Start time in nanoseconds: "+System.nanoTime());
        List<Edge> EulerPath = EulerTrail.findEulerTour(g);
        System.out.println("End time in nanoseconds: "+System.nanoTime());

        if (!EulerPath.isEmpty()) {
                System.out.println("Valid Tour & Euler Path :");
                for (Edge e : EulerPath) {
                    System.out.println(e);
                }

        } else {
            System.out.println("Not a valid Euler!");
        }

    }
}