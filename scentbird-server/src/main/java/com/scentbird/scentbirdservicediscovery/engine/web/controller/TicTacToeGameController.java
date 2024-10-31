package com.scentbird.scentbirdservicediscovery.web.controller;

import com.scentbird.scentbirdservicediscovery.engine.dto.TicTacToeGame;
import com.scentbird.scentbirdservicediscovery.engine.service.ClientRequestImplService;
import com.scentbird.scentbirdservicediscovery.engine.service.TicTacToeGameService;
import com.scentbird.scentbirdservicediscovery.web.dto.GameMoveDto;
import com.scentbird.scentbirdservicediscovery.web.dto.PlayerDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class TicTacToeGameController {


  private final TicTacToeGameService ticTacToeGameService;
  private final ClientRequestImplService clientRequestService;

  public TicTacToeGameController(TicTacToeGameService ticTacToeGameService,
      ClientRequestImplService clientRequestService) {
    this.ticTacToeGameService = ticTacToeGameService;
    this.clientRequestService = clientRequestService;
  }

  @PostMapping("/request")
  public HttpStatus sendRequest(@RequestHeader("X-PlayerName") String player,
      @RequestBody PlayerDto foePlayer) {
    boolean isFoeAvailable = clientRequestService.getPlayerStatus(foePlayer.getName());
    if (isFoeAvailable) {
      if (clientRequestService.sendRequest(player, foePlayer.getName())) {
        return HttpStatus.OK;
      }
    }
    return HttpStatus.NOT_ACCEPTABLE;
  }

  @PostMapping("/accept")
  public HttpStatus acceptRequest(@RequestHeader("X-PlayerName") String player,
      @RequestBody PlayerDto foePlayer) {
    boolean isFoeStillAvailable = clientRequestService.getPlayerStatus(foePlayer.getName());
    if (isFoeStillAvailable) {
      TicTacToeGame game = ticTacToeGameService.createGame(player, foePlayer.getName());
      String notification = String.format(
          "Game is started!. Players: %s playing with %s and %s playing with %s. Player: %s starts",
          game.getPlayer1().getUniqName(), game.getPlayer1().getType(),
          game.getPlayer2().getUniqName(), game.getPlayer2().getType(), game.getCurrentPlayer());
      clientRequestService.sendGameNotification(game.getPlayer1().getUniqName(), game.getGameId(),
          notification);
      clientRequestService.sendGameNotification(game.getPlayer2().getUniqName(), game.getGameId(),
          notification);
    }
    return HttpStatus.OK;
  }

  @PostMapping("/{gameId}/move")
  public HttpStatus makeMove(@RequestHeader("X-PlayerName") String player,
      @PathVariable String gameId, @RequestBody GameMoveDto moveDto) {
    TicTacToeGame game = ticTacToeGameService.makeMove(gameId, moveDto.getY(), moveDto.getX(),
        player);

    if (game != null) {
      if (game.isFinished()) {
        clientRequestService.sendGameNotification(game.getPlayer1().getUniqName(), gameId,
            "Game has ended with a winner:" + player);
        clientRequestService.sendGameNotification(game.getPlayer2().getUniqName(), gameId,
            "Game has ended with a winner:" + player);

        clientRequestService.finishGame(game.getPlayer2().getUniqName(), gameId);
        clientRequestService.finishGame(game.getPlayer1().getUniqName(), gameId);


      } else if (game.isFinished()) {
        clientRequestService.sendGameNotification(game.getPlayer1().getUniqName(), gameId,
            "Game has ended with a draw");
        clientRequestService.sendGameNotification(game.getPlayer2().getUniqName(), gameId,
            "Game has ended with a draw");

        clientRequestService.finishGame(game.getPlayer2().getUniqName(), gameId);
        clientRequestService.finishGame(game.getPlayer1().getUniqName(), gameId);

      } else {
        String notification = String.format("""
            Foe just made a move!
            Updated board:
            %s
            """, ticTacToeGameService.gameById(gameId).getPrettyStringBoard());
        clientRequestService.sendGameNotification(game.getCurrentPlayer(), game.getGameId(),
            notification);
      }
    }
    return HttpStatus.OK;
  }

  @GetMapping("/{gameId}")
  public String getGame(@PathVariable String gameId) {
    TicTacToeGame game = ticTacToeGameService.gameById(gameId);
    if (game != null) {
      return game.getPrettyStringBoard();
    }
    return null;
  }
}
