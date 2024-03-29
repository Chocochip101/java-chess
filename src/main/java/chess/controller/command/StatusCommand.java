package chess.controller.command;

import chess.domain.game.Game;
import chess.dto.GameRequest;
import chess.service.GameService;
import chess.view.OutputView;

public class StatusCommand implements Command {

    @Override
    public void execute(final Game game,
                        final GameRequest request,
                        final OutputView outputView,
                        final GameService gameService) {
        outputView.printStatus(game.getResult());
    }
}
