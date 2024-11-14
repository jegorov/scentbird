package com.scentbird.tictactoe_app.engine.handler.console_command.impl;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import com.scentbird.tictactoe_app.engine.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BoardCommandHandler implements CommandHandler {

  private static final Logger LOG = LoggerFactory.getLogger(BoardCommandHandler.class);
  private final GameService game;

  public BoardCommandHandler(GameService game) {
    this.game = game;
  }

  @Override
  public String getCommand() {
    return "accept";
  }

  @Override
  public void handle(String input) {
    LOG.warn(game.getBoard());
  }
}
