package chess.controller.command;

import chess.domain.game.Game;
import chess.dto.GameRequest;
import chess.service.GameService;
import chess.view.OutputView;

public interface Command {
    void execute(Game game, GameRequest request, OutputView outputView, GameService gameService);
}

