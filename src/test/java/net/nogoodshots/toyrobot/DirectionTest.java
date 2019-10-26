package net.nogoodshots.toyrobot;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void testNorth() {
        assertEquals(Direction.EAST, Direction.NORTH.right());
        assertEquals(Direction.WEST, Direction.NORTH.left());
    }

    @Test
    public void testSouth() {
        assertEquals(Direction.WEST, Direction.SOUTH.right());
        assertEquals(Direction.EAST, Direction.SOUTH.left());
    }

    @Test
    public void testeast() {
        assertEquals(Direction.SOUTH, Direction.EAST.right());
        assertEquals(Direction.NORTH, Direction.EAST.left());
    }
    @Test
    public void testWest() {
        assertEquals(Direction.NORTH, Direction.WEST.right());
        assertEquals(Direction.SOUTH, Direction.WEST.left());
    }

}