package net.nogoodshots.toyrobot;

import net.nogoodshots.toyrobot.actions.impl.PlaceAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.OutputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SimulationTest {

    @Mock
    private OutputStream mockOutputStream;

    private Simulation simulation;

    @Before
    public void setup() {
        simulation = new Simulation(new Board(), mockOutputStream);
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_new_sim_with_null_board_WHEN_new_THEN_throw_NullPointerException() {
        new Simulation(null, mockOutputStream);
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_new_sim_with_null_outputStream_WHEN_new_THEN_throw_NullPointerException() {
        new Simulation(new Board(), null);
    }

    @Test
    public void GIVEN_new_sim_default_output_stream_WHEN_new_THEN_create_simulation() {
        assertNotNull(new Simulation(new Board()));
    }

    @Test
    public void GIVEN_new_sim_WHEN_getRobot_THEN_return_empty_list() {
        assertFalse(simulation.getRobot().isPresent());
    }

    @Test
    public void GIVEN_new_sim_WHEN_takeAction_RobotAction_THEN_do_nothing() throws Exception{
        {
            final Outcome outcome = simulation.takeAction(new RobotAction() {
            });
            assertFalse(outcome.getMessage().isPresent());
            assertTrue(outcome instanceof ActionIgnored);
        }
    }

    @Test
    public void GIVEN_new_sim_WHEN_takeAction_unknown_action_THEN_do_nothing() throws Exception {
        final Outcome outcome = simulation.takeAction(new Action(){});
        assertFalse(outcome.getMessage().isPresent());
        assertTrue(outcome instanceof ActionIgnored);
    }

    @Test
    public void GIVEN_new_sim_WHEN_takeAction_RobotAction_THEN_do_something() throws Exception {
        final Outcome outcome = simulation.takeAction(new PlaceAction(1, 2, Direction.NORTH));
        assertFalse(outcome.getMessage().isPresent());
        assertFalse(outcome instanceof ActionIgnored);
    }

}
