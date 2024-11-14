package com.scentbird.tictactoe_app.engine.handler.console_command.impl;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelpCommandHandler implements CommandHandler {

  private static final Logger LOG = LoggerFactory.getLogger(HelpCommandHandler.class);

  @Override
  public String getCommand() {
    return "help";
  }

  @Override
  public void handle(String input) {
    LOG.warn("""
        Available commands:
        1. register userName
        2. logout
        3. players
        4. request foeName
        5. accept foeName
        6. move x:y
        7. board
        P.S: '%' symbol is restricted to use.
        """);
  }
}