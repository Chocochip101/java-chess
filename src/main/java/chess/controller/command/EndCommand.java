package chess.controller.command;

import chess.dto.GameRequest;
import chess.service.GameService;
import chess.view.OutputView;

public class EndCommand implements Command {

    @Override
    public void execute(final GameRequest request,
                        final OutputView outputView,
                        final GameService gameService) {

    }
}
