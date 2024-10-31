package com.scentbird.tictactoe_app.engine.service;

import com.scentbird.tictactoe_app.engine.service.impl.TicTacToeServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GameService {

  private boolean isGameRunning;
  private String gameId;

  private final TicTacToeServiceImpl gameLogicService;

  public GameService(TicTacToeServiceImpl gameLogicService) {
    this.gameLogicService = gameLogicService;
  }

  public void setGameRunning(boolean gameRunning) {
    isGameRunning = gameRunning;
  }

  public boolean isGameRunning() {
    return isGameRunning;
  }

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public String getBoard() {
    return gameLogicService.getCurrentBoard(gameId);
  }

  public void doMove(int x, int y) {
    if (isGameRunning()) {
      if (x < 0 || x > 2) {
        throw new RuntimeException("x cannot be more than 2 and less then 0");
      }
      if (y < 0 || y > 2) {
        throw new RuntimeException("y cannot be more than 2 and less then 0");
      }
      gameLogicService.doMove(gameId, x, y);
    } else {
      throw new RuntimeException("Game is not started");
    }
  }
}
