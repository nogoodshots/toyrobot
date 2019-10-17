package net.nogoodshots.toyrobot;

import org.junit.*;

public class GameTest {

    @Test
    public void GIVEN_new_game_WHEN_getPieces_THEN_return_empty_list() {
        final Game game = new Game();
        Assert.assertTrue(game.getPieces().isEmpty());
    }


}
