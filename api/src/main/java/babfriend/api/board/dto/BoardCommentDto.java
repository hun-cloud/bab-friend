package babfriend.api.board.dto;

import babfriend.api.board.entity.Board;
import babfriend.api.board.entity.BoardComment;
import babfriend.api.user.entity.User;
import lombok.Getter;

@Getter
public class BoardCommentDto {
    private String content;

    public BoardComment toEntity(User user, Board board) {
        return BoardComment.builder()
                .user(user)
                .board(board)
                .content(content)
                .build();
    }
}
