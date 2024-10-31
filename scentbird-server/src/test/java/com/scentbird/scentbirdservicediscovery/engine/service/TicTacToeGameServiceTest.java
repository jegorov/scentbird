package com.scentbird.scentbirdservicediscovery.engine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.scentbird.scentbirdservicediscovery.engine.dto.PlayerInfo;
import com.scentbird.scentbirdservicediscovery.engine.dto.TicTacToeGame;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TicTacToeGameServiceTest {

  private TicTacToeGameService gameService;
  private ClientService clientService;

  @BeforeEach
  void setUp() {
    clientService = Mockito.mock(ClientService.class);
    gameService = new TicTacToeGameService(clientService);

    PlayerInfo player1 = new PlayerInfo("player1", "Player One", 'X');
    PlayerInfo player2 = new PlayerInfo("player2", "Player Two", 'O');

    ConcurrentHashMap<String, PlayerInfo> concurrentHashMap = new ConcurrentHashMap<>();
    concurrentHashMap.put("player1", player1);
    concurrentHashMap.put("player2", player2);
    Mockito.when(clientService.getClientsList()).thenReturn(concurrentHashMap);
  }

  @Test
  void testCreateGame() {
    TicTacToeGame game = gameService.createGame("player1", "player2");

    assertNotNull(game);
    assertEquals(3, game.getBoard().length);
    assertEquals(TicTacToeGame.IN_PROGRESS, game.getStatus());
    assertTrue(game.getPlayer1().getType() == 'X' || game.getPlayer1().getType() == 'O');
    assertTrue(game.getPlayer2().getType() == 'X' || game.getPlayer2().getType() == 'O');
    assertNotNull(game.getGameId());
  }

  @Test
  void testMakeMoveAndWin() {
    TicTacToeGame game = gameService.createGame("player1", "player2");

    gameService.makeMove(game.getGameId(), 0, 0, "player1");
    gameService.makeMove(game.getGameId(), 1, 0, "player2");
    gameService.makeMove(game.getGameId(), 0, 1, "player1");
    gameService.makeMove(game.getGameId(), 1, 1, "player2");
    gameService.makeMove(game.getGameId(), 0, 2, "player1");

    assertEquals(TicTacToeGame.FINISHED, game.getStatus());
  }

  @Test
  void testDrawBoardState() {
    TicTacToeGame game = gameService.createGame("player1", "player2");

    Character[][] drawBoard = {
        {'X', 'O', 'X'},
        {'X', 'X', 'O'},
        {'O', 'X', 'O'}
    };
    game.setBoard(drawBoard);

    assertEquals(TicTacToeGame.DRAW, gameService.checkGameStatus(game));
  }

  @Test
  void testMakeMoveInvalidPosition() {
    TicTacToeGame game = gameService.createGame("player1", "player2");

    gameService.makeMove(game.getGameId(), 0, 0, "player1");

    TicTacToeGame invalidMove = gameService.makeMove(game.getGameId(), 0, 0, "player2");

    assertNull(invalidMove);
  }

  @Test
  void testOngoingGame() {
    TicTacToeGame game = gameService.createGame("player1", "player2");

    gameService.makeMove(game.getGameId(), 0, 0, "player1");
    gameService.makeMove(game.getGameId(), 0, 1, "player2");

    assertEquals(TicTacToeGame.IN_PROGRESS, game.getStatus());
  }
}
