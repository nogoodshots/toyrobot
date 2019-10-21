package net.nogoodshots.toyrobot.actions.impl;

import net.nogoodshots.toyrobot.Action;
import net.nogoodshots.toyrobot.ActionHandler;
import net.nogoodshots.toyrobot.Direction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActionFactoryTest {

    private ActionFactory factory;

    private PrintWriter printWriter;
    private OutputStream os;

    @Mock
    private ActionHandler mockHandler;

    @Before
    public void setup() {
        os = new ByteArrayOutputStream(1024);
        printWriter = new PrintWriter(os);

        factory = new ActionFactory(mockHandler);
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_WHEN_parse_THEN_throw_NullPointerException() throws Exception {
        factory.parseActions(null);
    }

    @Test(expected = IllegalCommandException.class)
    public void GIVEN_blank_line_WHEN_parse_THEN_throw_exception() throws Exception {
        printWriter.println(" ");
        printWriter.flush();

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler, never()).handleAction(any(Action.class));
    }

    @Test
    public void GIVEN_comment_WHEN_parse_THEN_return_no_action() throws Exception {
        {
            printWriter.println("# Comment ");
            printWriter.flush();

            factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
            verify(mockHandler, never()).handleAction(any(Action.class));
            ((ByteArrayOutputStream) os).reset();
        }
        { // add a leading space and remove space following comment marker '#'
            printWriter.println(" #Comment ");
            printWriter.flush();

            factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
            verify(mockHandler, never()).handleAction(any(Action.class));
            ((ByteArrayOutputStream) os).reset();
        }
    }

    @Test
    public void GIVEN_place_WHEN_parse_THEN_return_PlaceAction() throws Exception {
        final String input = "PLACE 1, 2, NORTH";
        printWriter.println(input);
        printWriter.flush();

        final Action expected = new PlaceAction(1, 2, Direction.NORTH);
        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler).handleAction(eq(expected));
    }

    @Test(expected = IllegalCommandException.class)
    public void GIVEN_lowercase_place_WHEN_parse_THEN_() throws Exception {
        final String input = "place 1, 2, NORTH";
        printWriter.println(input);
        printWriter.flush();

        final Action expected = new PlaceAction(1, 2, Direction.NORTH);
        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
    }

    @Test(expected = IllegalCommandException.class)
    public void GIVEN_incomplete_place_WHEN_parse_THEN_throw_exception() throws Exception {
            final String input = "PLACE 1, 2,";
            printWriter.println(input);
            printWriter.flush();

            factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
    }

    @Test(expected = IllegalCommandException.class)
    public void GIVEN_place_with_unknown_direction_WHEN_parse_THEN_throw_exception() throws Exception {
        final String input = "PLACE 1, 2, north";
        printWriter.println(input);
        printWriter.flush();

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
    }

    @Test(expected = IllegalCommandException.class)
    public void GIVEN_place_with_non_integer_WHEN_parse_THEN_throw_exception() throws Exception {
        final String input = "PLACE 1, y, NORTH";
        printWriter.println(input);
        printWriter.flush();

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
    }

    @Test(expected = IllegalCommandException.class)
    public void GIVEN_no_args_place_WHEN_parse_THEN_throw_exception() throws Exception {
        final String input = "PLACE ";
        printWriter.println(input);
        printWriter.flush();

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler, never()).handleAction(any(Action.class));
    }

    @Test
    public void GIVEN_report_WHEN_parse_THEN_handleAction() throws Exception {
        final String input = "REPORT";
        printWriter.println(input);
        printWriter.flush();

        ArgumentCaptor<Action> argument = ArgumentCaptor.forClass(Action.class);

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler).handleAction(argument.capture());
        assertTrue(argument.getValue() instanceof ReportAction);
    }

    @Test
    public void GIVEN_move_WHEN_parse_THEN_handleAction() throws Exception {
        final String input = "MOVE";
        printWriter.println(input);
        printWriter.flush();

        ArgumentCaptor<Action> argument = ArgumentCaptor.forClass(Action.class);

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler).handleAction(argument.capture());
        assertTrue(argument.getValue() instanceof MoveAction);
    }

    @Test
    public void GIVEN_left_WHEN_parse_THEN_handleAction() throws Exception {
        final String input = "LEFT";
        printWriter.println(input);
        printWriter.flush();

        ArgumentCaptor<Action> argument = ArgumentCaptor.forClass(Action.class);

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler).handleAction(argument.capture());
        assertTrue(argument.getValue() instanceof LeftTurnAction);
    }

    @Test
    public void GIVEN_right_WHEN_parse_THEN_handleAction() throws Exception {
        final String input = "RIGHT";
        printWriter.println(input);
        printWriter.flush();

        ArgumentCaptor<Action> argument = ArgumentCaptor.forClass(Action.class);

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler).handleAction(argument.capture());
        assertTrue(argument.getValue() instanceof RightTurnAction);
    }

    @Test
    public void GIVEN_multiple_lines_WHEN_parse_THEN_call_handleAction_multiple_times() throws Exception {
        final String input = "REPORT\n# Mix things up\n REPORT\n";
        printWriter.println(input);
        printWriter.flush();

        factory.parseActions(new ByteArrayInputStream(((ByteArrayOutputStream)os).toByteArray()));
        verify(mockHandler, times(2)).handleAction(any(Action.class));
    }

}