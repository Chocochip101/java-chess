package chess.controller.command;

import chess.dto.GameRequest;
import chess.service.GameService;
import chess.view.OutputView;

public class StartCommand implements Command {
    private static final String GAME_ALREADY_START = "게임이 이미 진행중입니다.";

    @Override
    public void execute(final GameRequest request,
                        final OutputView outputView,
                        final GameService gameService) {
        throw new IllegalArgumentException(GAME_ALREADY_START);
    }
}
