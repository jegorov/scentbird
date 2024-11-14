package com.scentbird.tictactoe_app.engine.handler.console_command.impl;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import com.scentbird.tictactoe_app.engine.service.GameRequestService;
import com.scentbird.tictactoe_app.engine.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AcceptCommandHandler implements CommandHandler {


  private static final Logger LOG = LoggerFactory.getLogger(AcceptCommandHandler.class);
  private final GameRequestService preGameActionService;
  private final GameService game;

  public AcceptCommandHandler(GameRequestService preGameActionService, GameService game) {
    this.preGameActionService = preGameActionService;
    this.game = game;
  }

  @Override
  public String getCommand() {
    return "accept";
  }

  @Override
  public void handle(String input) {
    if (!game.isGameRunning()) {
      String foeName = input.split("%")[1];
      preGameActionService.acceptGameRequest(foeName);
    } else {
      LOG.error("Game is already started");
    }
  }
}
