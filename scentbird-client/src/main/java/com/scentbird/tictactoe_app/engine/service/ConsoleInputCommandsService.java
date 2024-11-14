package com.scentbird.tictactoe_app.engine.service;

import com.scentbird.tictactoe_app.engine.handler.console_command.CommandHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsoleInputCommandsService {

  private final Map<String, CommandHandler> commandHandlers;
  Logger LOG = LoggerFactory.getLogger(ConsoleInputCommandsService.class);

  public ConsoleInputCommandsService(List<CommandHandler> handlers) {
    this.commandHandlers = new HashMap<>();

    for (CommandHandler handler : handlers) {
      commandHandlers.put(handler.getCommand(), handler);
    }
  }

  public void run() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      LOG.warn("Enter a command (type 'help' to get commands): ");
      String input = scanner.nextLine().toLowerCase().replaceAll("\\s+", "%");

      if (input.contains("%")) {
        continue;
      }

      String commandKey = input.split("%")[0];
      Optional<CommandHandler> handler = Optional.ofNullable(commandHandlers.get(commandKey));

      if (handler.isPresent()) {
        try {
          handler.get().handle(input);
        } catch (Exception e) {
          LOG.error("Something is not OK", e);
        }
      } else {
        LOG.warn("Command {} not found", commandKey);
      }
    }
  }
}
