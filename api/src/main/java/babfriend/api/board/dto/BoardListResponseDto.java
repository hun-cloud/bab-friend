package babfriend.api.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardListResponseDto {

    private List<BoardsSimpleDto> boards;
    private boolean first;
    private boolean last;
    private Long totalElement;
    private boolean empty;

    @Builder
    private BoardListResponseDto(List<BoardsSimpleDto> boards, boolean first, boolean last, Long totalElement, boolean empty) {
        this.boards = boards;
        this.first = first;
        this.last = last;
        this.totalElement = totalElement;
        this.empty = empty;
    }
}
