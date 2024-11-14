package com.scentbird.tictactoe_app.engine.handler.console_command.impl;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import com.scentbird.tictactoe_app.engine.service.GameRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PlayersCommandHandler implements CommandHandler {

  private static final Logger LOG = LoggerFactory.getLogger(PlayersCommandHandler.class);
  private final GameRequestService preGameActionService;

  public PlayersCommandHandler(GameRequestService preGameActionService) {
    this.preGameActionService = preGameActionService;
  }

  @Override
  public String getCommand() {
    return "players";
  }

  @Override
  public void handle(String input) {
    LOG.warn("Players list: \n {}", preGameActionService.findPlayers());
  }
}
