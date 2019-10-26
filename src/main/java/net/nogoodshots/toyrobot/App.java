package net.nogoodshots.toyrobot;

import net.nogoodshots.toyrobot.actions.impl.ActionFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main App to run the ToyRobot Simulation from.
 *
 */
public class App 
{
    private final Simulation simulation;
    private final ActionFactory actionFactory;
    private final BufferedReader bufferedReader;

    public static void main( String[] args )
    {
        App app;
        if (args != null && args.length > 0) {
            // Determine an input file from arg[0]
            app = new App(new File(args[0]));
        } else {
            app = new App();
        }
        app.run();
    }

    public App() {
        this(null);
    }

    public App(final File actionInputFile) {
        // Prepare BufferedReader to read from StdIn or File
        if (actionInputFile != null) {
            if (actionInputFile.exists() && actionInputFile.isFile()) {
                try {
                    bufferedReader = new BufferedReader(new FileReader(actionInputFile));
                } catch (IOException e) {
                    throw new IllegalArgumentException(String.format("Cannot access file %s", actionInputFile.getAbsoluteFile()), e);
                }
            } else {
                throw new IllegalArgumentException(String.format("%s is not a valid existing file", actionInputFile.getAbsolutePath()));
            }
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }
        // Construct a Simulation with a default Board and StdOut as the default
        // OutputStream
        simulation = new Simulation(new Board());
        actionFactory = new ActionFactory(new ActionHandler() {
            @Override
            public void handleAction(Action action) {
                try {
                    final Outcome outcome = simulation.takeAction(action);
                } catch (IOException e) {
                    throw new RuntimeException("Exiting: Internal error", e);
                }
            }
        });
    }


    public void run() {
        try {
            act();
            System.out.println("Exiting");
        } catch (IOException ioe) {
            System.err.println(String.format("Exiting - error: %s", ioe.getMessage()));
        }
    }

    private void act() throws IOException {
        String inputLine;
        do {
            inputLine = bufferedReader.readLine();
            if (inputLine != null) {
                System.out.println(inputLine);
                actionFactory.parseActions(new ByteArrayInputStream(inputLine.getBytes()));
            }
        } while (inputLine != null);
    }

}
