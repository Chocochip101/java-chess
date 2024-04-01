package chess.controller.command;

import chess.dto.GameRequest;
import chess.service.GameService;
import chess.view.OutputView;

public interface Command {
    void execute(GameRequest request, OutputView outputView, GameService gameService);
}

