GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `chess` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE chess;

CREATE TABLE IF NOT EXISTS user
(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    CHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS room
(
    room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    CHAR(10) NOT NULL,
    turn    CHAR(10) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE If NOT EXISTS piece
(
    piece_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type     CHAR(10) NOT NULL,
    color    CHAR(10) NOT NULL
);

CREATE TABLE If NOT EXISTS board
(
    board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id  BIGINT  NOT NULL,
    square   CHAR(2) NOT NULL,
    piece_id BIGINT,
    FOREIGN KEY (room_id) REFERENCES room (room_id),
    FOREIGN KEY (piece_id) REFERENCES piece (piece_id)
);

INSERT INTO piece (type, color)
VALUES ('PAWN', 'WHITE'),
       ('PAWN', 'BLACK'),
       ('ROOK', 'WHITE'),
       ('ROOK', 'BLACK'),
       ('KNIGHT', 'WHITE'),
       ('KNIGHT', 'BLACK'),
       ('BISHOP', 'WHITE'),
       ('BISHOP', 'BLACK'),
       ('QUEEN', 'WHITE'),
       ('QUEEN', 'BLACK'),
       ('KING', 'WHITE'),
       ('KING', 'BLACK');

