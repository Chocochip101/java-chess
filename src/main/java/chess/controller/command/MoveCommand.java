package chess.controller.command;

import chess.domain.game.Game;
import chess.dto.GameRequest;
import chess.dto.MoveRequest;
import chess.dto.SquareRequest;
import chess.service.GameService;
import chess.view.OutputView;

public class MoveCommand implements Command {

    @Override
    public void execute(final Game game,
                        final GameRequest request,
                        final OutputView outputView,
                        final GameService gameService) {
        MoveRequest moveRequest = request.getMoveRequest();
        SquareRequest source = SquareRequest.from(moveRequest.source());
        SquareRequest target = SquareRequest.from(moveRequest.target());
        gameService.move(game, source, target);
    }
}
