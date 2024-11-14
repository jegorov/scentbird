package com.scentbird.tictactoe_app.engine.handler.console_command.impl;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import com.scentbird.tictactoe_app.engine.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MoveCommandHandler implements CommandHandler {

  private static final Logger LOG = LoggerFactory.getLogger(MoveCommandHandler.class);
  private final GameService game;

  public MoveCommandHandler(GameService game) {
    this.game = game;
  }

  @Override
  public String getCommand() {
    return "move";
  }


  @Override
  public void handle(String input) {
    String[] coordinates = input.split("%")[1].split(":");
    int x = Integer.parseInt(coordinates[0]);
    int y = Integer.parseInt(coordinates[1]);
    game.doMove(x, y);
    LOG.warn(game.getBoard());
  }
}
