package net.nogoodshots.toyrobot;

import net.nogoodshots.toyrobot.actions.impl.ActionFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOG = Logger.getLogger(App.class.getName());

    private Simulation simulation;
    private ActionFactory actionFactory;

    public static void main( String[] args )
    {
        final App app = new App();
        app.run();
    }

    public App() {
        simulation = new Simulation(new Board());
        actionFactory = new ActionFactory(new ActionHandler() {
            @Override
            public void handleAction(Action action) {
                final Outcome outcome = simulation.takeAction(action);
                if (outcome.getMessage().isPresent()) {
                    System.out.println(outcome.getMessage().get());
                }
            }
        });
    }

    public void run() {
        try {
            readStdin();
        } catch (IOException ioe) {
            System.err.println(String.format("Exiting - error: %s", ioe.getMessage()));
        }
    }

    public void readStdin() throws IOException {
        //Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String inputLine;
        do {
            inputLine = reader.readLine();
            LOG.info(String.format("Read %s", inputLine));
            if (inputLine != null) {
                actionFactory.parseActions(new ByteArrayInputStream(inputLine.getBytes()));
            }
        } while (inputLine != null);
        LOG.info(String.format("Exiting"));
    }

    public void readInputFile() {

    }
}
