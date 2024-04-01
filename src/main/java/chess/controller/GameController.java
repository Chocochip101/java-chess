package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.EndCommand;
import chess.controller.command.MoveCommand;
import chess.controller.command.StartCommand;
import chess.controller.command.StatusCommand;
import chess.domain.game.GameResult;
import chess.dto.GameCommand;
import chess.dto.GameRequest;
import chess.service.GameService;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameController {
    private static final String FIRST_START_EXCEPTION = "게임 시작을 위해 start를 입력해주세요.";

    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;
    private final Map<GameCommand, Command> commands = new HashMap<>();

    public GameController(final InputView inputView, final OutputView outputView, final GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;

        commands.put(GameCommand.START, new StartCommand());
        commands.put(GameCommand.STATUS, new StatusCommand());
        commands.put(GameCommand.MOVE, new MoveCommand());
        commands.put(GameCommand.END, new EndCommand());
    }

    public void start(final long roomId) {
        gameService.loadGame(roomId);
        GameResult gameResult = gameService.getResult();

        if (gameResult.isGameOver()) {
            outputView.printAlreadyOver();
            outputView.printBoard(gameService.getGameStatus());
            outputView.printStatus(gameResult);
            return;
        }

        outputView.printGameStartMessage();
        GameCommand gameCommand = requestUntilValidated(this::readStartCommand);
        if (gameCommand == GameCommand.START) {
            play();
        }
    }

    private GameCommand readStartCommand() {
        GameRequest request = inputView.readStartCommand();
        GameCommand command = request.getCommand();

        if (command == GameCommand.START) {
            return command;
        }
        if (command == GameCommand.END) {
            return command;
        }
        throw new IllegalArgumentException(FIRST_START_EXCEPTION);
    }

    private void play() {
        outputView.printBoard(gameService.getGameStatus());
        outputView.printTurn(gameService.getTurn());

        while (requestUntilValidated(this::playOneRound) != GameCommand.END) {
            outputView.printBoard(gameService.getGameStatus());
            outputView.printTurn(gameService.getTurn());
        }
        printEndStatus();
    }

    private GameCommand playOneRound() {
        GameRequest request = inputView.readStartCommand();
        Command command = commands.get(request.getCommand());
        command.execute(request, outputView, gameService);
        return request.getCommand();
    }

    private void printEndStatus() {
        outputView.printGameFinish();
        outputView.printBoard(gameService.getGameStatus());
        outputView.printStatus(gameService.getResult());
    }

    private <T> T requestUntilValidated(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return requestUntilValidated(supplier);
        }
    }
}
