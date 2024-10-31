package com.scentbird.scentbirdservicediscovery.engine.service;


import com.scentbird.scentbirdservicediscovery.engine.dto.PlayerInfo;
import com.scentbird.scentbirdservicediscovery.engine.dto.TicTacToeGame;
import java.util.HashMap;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class TicTacToeGameService {

  HashMap<String, TicTacToeGame> games = new HashMap<>();

  private final ClientService clientService;

  public TicTacToeGameService(ClientService clientService) {
    this.clientService = clientService;
  }

  public TicTacToeGame createGame(String player1, String player2) {
    clientService.changeClientAvailability(player1, false);
    clientService.changeClientAvailability(player2, false);
    TicTacToeGame game = new TicTacToeGame();
    game.setGameId(generateTicTacToeGameId());
    game.setBoard(new Character[3][3]);
    game.setCurrentPlayer(player1);
    game.setStatus(TicTacToeGame.IN_PROGRESS);

    int playerType = new Random().nextInt(2) + 1;

    PlayerInfo p1 = clientService.getClientsList().get(player1);
    clientService.changeClientAvailability(player1, false);
    p1.setType(playerType == 1 ? 'X' : 'O');
    PlayerInfo p2 = clientService.getClientsList().get(player2);
    p2.setType(playerType == 1 ? 'O' : 'X');
    clientService.changeClientAvailability(player2, false);
    game.setPlayer1(p1);
    game.setPlayer2(p2);
    games.put(game.getGameId(), game);
    return game;
  }

  public TicTacToeGame makeMove(String gameId, int row, int col, String player) {
    TicTacToeGame game = games.get(gameId);
    // Validate move
    if (game.getBoard()[row][col] == null && game.getCurrentPlayer().equals(player)) {
      game.getBoard()[row][col] = clientService.getClientsList().get(player).getType();

      String gameStatus = checkGameStatus(game);

      if (TicTacToeGame.IN_PROGRESS.equals(gameStatus)) {
        game.setCurrentPlayer(
            game.getPlayer1().getUniqName().equals(player) ? game.getPlayer2().getUniqName()
                : game.getPlayer1().getUniqName());
      } else {
        game.setStatus(gameStatus);
      }

      return game;

    }
    return null;
  }

  public TicTacToeGame gameById(String gameId) {
    return games.get(gameId);
  }

  // Additional methods like checkWin(), checkDraw(), etc.
  private String generateTicTacToeGameId() {
    return java.util.UUID.randomUUID().toString();
  }

  public String checkGameStatus(TicTacToeGame game) {
    Character[][] board = game.getBoard();
    // Check rows for a win
    for (int i = 0; i < 3; i++) {
      if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
        return TicTacToeGame.FINISHED;
      }
    }

    // Check columns for a win
    for (int i = 0; i < 3; i++) {
      if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
        return TicTacToeGame.FINISHED;
      }
    }

    // Check diagonals for a win
    if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
      return TicTacToeGame.FINISHED;
    }
    if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
      return TicTacToeGame.FINISHED;
    }

    // Check for any rows, columns, or diagonals with a winning potential
    boolean hasPotential = false;

    // Check rows
    for (int i = 0; i < 3; i++) {
      if ((board[i][0] == null || board[i][0] == board[i][1] || board[i][0] == board[i][2]) &&
          (board[i][1] == null || board[i][1] == board[i][0] || board[i][1] == board[i][2]) &&
          (board[i][2] == null || board[i][2] == board[i][0] || board[i][2] == board[i][1])) {
        hasPotential = true;
      }
    }

    // Check columns
    for (int i = 0; i < 3; i++) {
      if ((board[0][i] == null || board[0][i] == board[1][i] || board[0][i] == board[2][i]) &&
          (board[1][i] == null || board[1][i] == board[0][i] || board[1][i] == board[2][i]) &&
          (board[2][i] == null || board[2][i] == board[0][i] || board[2][i] == board[1][i])) {
        hasPotential = true;
      }
    }

    // Check diagonals
    if ((board[0][0] == null || board[0][0] == board[1][1] || board[0][0] == board[2][2]) &&
        (board[1][1] == null || board[1][1] == board[0][0] || board[1][1] == board[2][2]) &&
        (board[2][2] == null || board[2][2] == board[0][0] || board[2][2] == board[1][1])) {
      hasPotential = true;
    }
    if ((board[0][2] == null || board[0][2] == board[1][1] || board[0][2] == board[2][0]) &&
        (board[1][1] == null || board[1][1] == board[0][2] || board[1][1] == board[2][0]) &&
        (board[2][0] == null || board[2][0] == board[0][2] || board[2][0] == board[1][1])) {
      hasPotential = true;
    }

    // If there are empty cells but no potential for a win, it's a draw
    if (!hasPotential) {
      return TicTacToeGame.DRAW;
    }

    // Otherwise, the game is still ongoing
    return TicTacToeGame.IN_PROGRESS;
  }
}
