package com.scentbird.tictactoe_app.engine.web.controller;

import com.scentbird.tictactoe_app.engine.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

  private static final Logger LOG = LoggerFactory.getLogger(GameController.class);
  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/{gameId}/notification/{notification}")
  public HttpStatus notitication(@PathVariable String gameId, @PathVariable String notification) {
    gameService.setGameRunning(true);
    gameService.setGameId(gameId);
    LOG.warn(notification);
    return HttpStatus.OK;
  }

  @PostMapping("/{gameId}/finish")
  public void finishGame(@PathVariable String gameId) {
    if (gameService.getGameId() != null && gameService.getGameId().equals(gameId)) {
      LOG.warn("Game has been finished");
      System.exit(0);
    }
  }

}
