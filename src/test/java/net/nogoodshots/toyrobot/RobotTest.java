package net.nogoodshots.toyrobot;

import net.nogoodshots.toyrobot.actions.impl.LeftTurnAction;
import net.nogoodshots.toyrobot.actions.impl.MoveAction;
import net.nogoodshots.toyrobot.actions.impl.PlaceAction;
import net.nogoodshots.toyrobot.actions.impl.ReportAction;
import net.nogoodshots.toyrobot.actions.impl.RightTurnAction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RobotTest {

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_board_WHEN_new_THEN_throw_exception() {
        new Robot(null);
    }

    @Test
    public void GIVEN_new_robot_WHEN_getBoard_THEN_return_Board() {
        final Board board = new Board(3, 3);

        assertNotNull(new Robot(board));
    }

    @Test
    public void GIVEN_new_robot_WHEN_getBoard_THEN_return_board() {
        final Board board = new Board(3, 3);

        final Robot robot = new Robot(board);
        assertEquals(board, robot.getBoard());
    }

    @Test
    public void GIVEN_new_robot_WHEN_getBoardPosition_THEN_return_empty_BoardPosition() {
        final Board board = new Board(3, 3);

        final Robot robot = new Robot(board);
        assertFalse(robot.getBoardPosition().isPresent());
    }


    @Test
    public void GIVEN_new_robot_WHEN_getFacingDirection_THEN_return_empty_Direction() {
        final Board board = new Board(3, 3);

        final Robot robot = new Robot(board);
        assertFalse(robot.getFacingDirection().isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_action_WHEN_performAction_THEN_throw_exception() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));

        robot.performAction(null);
    }

    @Test
    public void GIVEN_unplaced_robot_WHEN_performAction_THEN_return_ActionIgnored() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));

        assertTrue(robot.performAction(new ReportAction()) instanceof ActionIgnored);
        assertTrue(robot.performAction(new LeftTurnAction()) instanceof ActionIgnored);
        assertTrue(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertTrue(robot.performAction(new MoveAction()) instanceof ActionIgnored);
    }

    @Test
    public void GIVEN_unplaced_robot_WHEN_performAction_place_THEN_has_position() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));
        final Outcome outcome = robot.performAction(new PlaceAction(2, 1, Direction.NORTH));

        assertNotNull(outcome);
        assertFalse(outcome instanceof ActionIgnored);
        final BoardPosition expected = new BoardPosition(2, 1);
        assertEquals(expected, robot.getBoardPosition().get());
    }

    @Test
    public void GIVEN_unplaced_robot_WHEN_performAction_place_THEN_has_Direction() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));
        final Outcome outcome = robot.performAction(new PlaceAction(2, 1, Direction.SOUTH));

        assertNotNull(outcome);
        assertFalse(outcome instanceof ActionIgnored);
        assertEquals(Direction.SOUTH, robot.getFacingDirection().get());
    }

    @Test
    public void GIVEN_placed_robot_WHEN_performAction_report_THEN_returns_outcome() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));
        assertNotNull(robot.performAction(new PlaceAction(2, 1, Direction.SOUTH)));
        final Outcome outcome = robot.performAction(new ReportAction());

        assertNotNull(outcome);
        assertFalse(outcome instanceof ActionIgnored);
        assertEquals("2,1,SOUTH", outcome.getMessage().get());
        // and it has not moved
        final BoardPosition expectedPosition = new BoardPosition(2, 1);
        assertEquals(expectedPosition, robot.getBoardPosition().get());
    }

    @Test
    public void GIVEN_placed_robot_WHEN_performAction_left_THEN_has_new_direction() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));
        assertNotNull(robot.performAction(new PlaceAction(2, 1, Direction.SOUTH)));

        assertFalse(robot.performAction(new LeftTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.EAST, robot.getFacingDirection().get());
        // and it has not moved
        final BoardPosition expectedPosition = new BoardPosition(2, 1);
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new LeftTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.NORTH, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new LeftTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.WEST, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new LeftTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.SOUTH, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());
    }

    @Test
    public void GIVEN_placed_robot_WHEN_performAction_right_THEN_has_new_direction() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));
        assertNotNull(robot.performAction(new PlaceAction(2, 1, Direction.EAST)));

        assertFalse(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.SOUTH, robot.getFacingDirection().get());
        // and it has not moved
        final BoardPosition expectedPosition = new BoardPosition(2, 1);
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.WEST, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.NORTH, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertEquals(Direction.EAST, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());
    }

    @Test
    public void GIVEN_placed_robot_WHEN_performAction_move_THEN_has_new_position() throws Exception {
        final Robot robot = new Robot(new Board(3, 3));
        assertNotNull(robot.performAction(new PlaceAction(0, 0, Direction.EAST)));

        assertFalse(robot.performAction(new MoveAction()) instanceof ActionIgnored);
        assertEquals(new BoardPosition(1, 0), robot.getBoardPosition().get());

        assertFalse(robot.performAction(new MoveAction()) instanceof ActionIgnored);
        assertEquals(new BoardPosition(2, 0), robot.getBoardPosition().get());

        assertFalse(robot.performAction(new LeftTurnAction()) instanceof ActionIgnored);
        assertFalse(robot.performAction(new MoveAction()) instanceof ActionIgnored);
        assertEquals(new BoardPosition(2, 1), robot.getBoardPosition().get());
    }

    @Test
    public void GIVEN_placed_robot_WHEN_performAction_move_THEN_ignore() throws Exception {
        final Robot robot = new Robot(new Board(1, 1));
        assertNotNull(robot.performAction(new PlaceAction(0, 0, Direction.EAST)));

        final BoardPosition expectedPosition = new BoardPosition(0, 0);

        assertTrue(robot.performAction(new MoveAction()) instanceof ActionIgnored);
        assertEquals(Direction.EAST, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertTrue(robot.performAction(new MoveAction()) instanceof ActionIgnored);
        assertEquals(Direction.SOUTH, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertTrue(robot.performAction(new MoveAction()) instanceof ActionIgnored);
        assertEquals(Direction.WEST, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());

        assertFalse(robot.performAction(new RightTurnAction()) instanceof ActionIgnored);
        assertTrue(robot.performAction(new MoveAction()) instanceof ActionIgnored);
        assertEquals(Direction.NORTH, robot.getFacingDirection().get());
        assertEquals(expectedPosition, robot.getBoardPosition().get());
    }
}
