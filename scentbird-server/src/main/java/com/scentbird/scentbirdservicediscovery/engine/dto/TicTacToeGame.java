package com.scentbird.scentbirdservicediscovery.engine.dto;

public class TicTacToeGame {

  public static String DRAW = "Draw";
  public static String FINISHED = "Finished";
  public static String IN_PROGRESS = "In Progress";

  private String gameId;
  private Character[][] board;
  private String currentPlayer;
  private String status;
  private PlayerInfo playerInfo1;
  private PlayerInfo playerInfo2;

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public Character[][] getBoard() {
    return board;
  }

  public void setBoard(Character[][] board) {
    this.board = board;
  }

  public String getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(String currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public PlayerInfo getPlayer1() {
    return playerInfo1;
  }

  public void setPlayer1(PlayerInfo playerInfo1) {
    this.playerInfo1 = playerInfo1;
  }

  public PlayerInfo getPlayer2() {
    return playerInfo2;
  }

  public void setPlayer2(PlayerInfo playerInfo2) {
    this.playerInfo2 = playerInfo2;
  }

  public String getPrettyStringBoard() {
    StringBuilder builder = new StringBuilder();
    for (Character[] characters : board) {
      for (Character value : characters) {
        builder.append("[").append(value == null ? " " : value).append("]");
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  public boolean isDraw() {
    return DRAW.equals(status);
  }

  public boolean isFinished() {
    return FINISHED.equals(status);
  }
}
