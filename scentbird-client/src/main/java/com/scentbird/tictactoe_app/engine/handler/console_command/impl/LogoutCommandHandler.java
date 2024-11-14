package com.scentbird.tictactoe_app.engine.handler.console_command.impl;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import com.scentbird.tictactoe_app.engine.service.GameRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogoutCommandHandler implements CommandHandler {

  private static final Logger LOG = LoggerFactory.getLogger(LogoutCommandHandler.class);
  private final GameRequestService preGameActionService;

  public LogoutCommandHandler(GameRequestService preGameActionService) {
    this.preGameActionService = preGameActionService;
  }

  @Override
  public String getCommand() {
    return "logout";
  }

  @Override
  public void handle(String input) {
    preGameActionService.unregisterUser();
    LOG.warn("Successfully logged out");
  }
}
