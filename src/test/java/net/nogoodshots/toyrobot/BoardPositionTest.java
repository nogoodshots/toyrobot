package net.nogoodshots.toyrobot;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardPositionTest {

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_x_WHEN_new_THEN_throw_NullPointerException() {
        new BoardPosition(null, Integer.valueOf(1));
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_null_y_WHEN_new_THEN_throw_NullPointerException() {
        new BoardPosition(null, Integer.valueOf(1));
    }

    @Test(expected = NullPointerException.class)
    public void GIVEN_nulls_WHEN_new_THEN_throw_NullPointerException() {
        new BoardPosition(null, null);
    }
    @Test
    public void GIVEN_x_y_WHEN_new_THEN_create() {
        final BoardPosition pos = new BoardPosition(Integer.valueOf(1), Integer.valueOf(1));
        assertNotNull(pos);
        assertEquals(Integer.valueOf(1), pos.getX());
        assertEquals(Integer.valueOf(1), pos.getY());
    }

}