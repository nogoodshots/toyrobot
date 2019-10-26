package net.nogoodshots.toyrobot;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void GIVEN_no_args_WHEN_new_THEN_default_Board_created() {
        final Board b = new Board();
        assertEquals(5, b.getX());
        assertEquals(5, b.getY());
    }

    @Test
    public void GIVEN_specific_bounds_WHEN_new_THEN_Board_created() {
        final Board b = new Board(10, 100);
        assertEquals(10, b.getX());
        assertEquals(100, b.getY());
    }

    @Test(expected = IllegalArgumentException.class)
    public void GIVEN_illegal_x_WHEN_new_THEN_throw_exception() {
        new Board(-10, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void GIVEN_illegal_y_WHEN_new_THEN_throw_exception() {
        new Board(10, -100);
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_WHEN_occupy_THEN_throw_NullPointerException() throws Exception {
        final Board board = new Board(5, 5);
        board.occupy(null);
    }

    @Test(expected = IllegalActionException.class)
    public void GIVEN_illegal_position_WHEN_occupy_THEN_throw_exception() throws Exception {
        final Board board = new Board(5, 5);
        final BoardPosition position = new BoardPosition(20, 2);
        board.occupy(position);
    }

    @Test
    public void GIVEN_legal_position_WHEN_occupy_THEN_succeed() throws Exception {
        final Board board = new Board(5, 5);
        final BoardPosition position = new BoardPosition(2, 2);
        board.occupy(position);
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_from_position_WHEN_changePosition_THEN_throw_exception() throws Exception {
        final Board board = new Board(5, 5);
        final BoardPosition position = new BoardPosition(2, 2);
        board.changePosition(null, position);
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_to_position_WHEN_changePosition_THEN_throw_exception() throws Exception {
        final Board board = new Board(5, 5);
        final BoardPosition position = new BoardPosition(2, 2);
        board.changePosition(position, null);
    }

    @Test
    public void GIVEN_valid_positions_WHEN_changePosition_THEN_succed() throws Exception {
        final Board board = new Board(5, 5);
        board.changePosition(new BoardPosition(2, 2), new BoardPosition(3, 3));
    }

    @Test(expected = IllegalActionException.class)
    public void GIVEN_invalid_to_position_WHEN_changePosition_THEN_throw_exception() throws Exception {
        final Board board = new Board(5, 5);
        board.changePosition(new BoardPosition(2, 2), new BoardPosition(6, 6));
    }

    @Test
    public void GIVEN_direction_WHEN_getRelativePosition_THEN_return_new_position() {
        final Board board = new Board(1, 1);
        final BoardPosition position = new BoardPosition(0, 0);

        assertEquals(new BoardPosition(0, 1), board.getRelativePosition(position, Direction.NORTH, 1));
        assertEquals(new BoardPosition(0, -1), board.getRelativePosition(position, Direction.NORTH, -1));
        assertEquals(new BoardPosition(2, 0), board.getRelativePosition(position, Direction.EAST, 2));
        assertEquals(new BoardPosition(-2, 0), board.getRelativePosition(position, Direction.EAST, -2));
        assertEquals(new BoardPosition(0, -3), board.getRelativePosition(position, Direction.SOUTH, 3));
        assertEquals(new BoardPosition(0, 3), board.getRelativePosition(position, Direction.SOUTH, -3));
        assertEquals(new BoardPosition(-4, 0), board.getRelativePosition(position, Direction.WEST, 4));
        assertEquals(new BoardPosition(4, 0), board.getRelativePosition(position, Direction.WEST, -4));
    }

    @Test
    public void equalityTests() {
        final Board a = new Board(50, 50);
        final Board b = new Board(50, 50);
        final Board c = new Board(10, 10);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(c));
        assertFalse(c.equals(a));
    }

}