package chess.repository;

import chess.database.JdbcConnectionPool;
import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.pieces.piece.Type;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.SquareRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BoardDao implements BoardRepository {
    private final JdbcConnectionPool connectionPool;

    public BoardDao(final JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void save(final Square square, final Type type, final Color color, final long roomId) {
        final String query = "INSERT INTO board (room_id, square, piece_id) VALUES (?, ?, (SELECT piece_id FROM piece WHERE type = ? AND color = ? LIMIT 1))";
        final Connection connection = connectionPool.getConnection();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, square.getName());
            preparedStatement.setString(3, type.name());
            preparedStatement.setString(4, color.name());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void save(final Square square, final long pieceId, final long roomId) {
        final String query = "INSERT INTO board (room_id, square, piece_id) VALUES (?, ?, ?)";
        final Connection connection = connectionPool.getConnection();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, roomId);
            preparedStatement.setString(2, square.getName());
            preparedStatement.setLong(3, pieceId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<Long> findPieceIdBySquare(final Square square, final long roomId) {
        final String query = "SELECT piece_id FROM board WHERE square = ? AND room_id = ?";
        final Connection connection = connectionPool.getConnection();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, square.getName());
            preparedStatement.setLong(2, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("piece_id"));
            }
            return Optional.empty();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteBySquare(final Square square, final long roomId) {
        final String query = "DELETE FROM board WHERE square = ? AND room_id = ?";
        final Connection connection = connectionPool.getConnection();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, square.getName());
            preparedStatement.setLong(2, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Map<Square, Piece> findAllByRoomId(final long roomId) {
        final String query = "SELECT square, p.type, p.color FROM board b JOIN piece p on b.piece_id = p.piece_id WHERE room_id = ?";
        final Connection connection = connectionPool.getConnection();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createPieces(resultSet);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Map<Square, Piece> createPieces(final ResultSet resultSet) throws SQLException {
        Map<Square, Piece> pieces = new HashMap<>();
        while (resultSet.next()) {
            pieces.put(createSquare(resultSet), createPiece(resultSet));
        }
        return pieces;
    }

    private Square createSquare(ResultSet resultSet) throws SQLException {
        SquareRequest square = SquareRequest.from(resultSet.getString("square"));
        return Square.of(File.from(square.file()), Rank.from(square.rank()));
    }

    private Piece createPiece(ResultSet resultSet) throws SQLException {
        Type type = Type.valueOf(resultSet.getString("p.type"));
        Color color = Color.valueOf(resultSet.getString("p.color"));
        return Piece.of(color, type);
    }
}
