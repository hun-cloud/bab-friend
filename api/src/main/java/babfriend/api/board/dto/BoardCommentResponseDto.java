package babfriend.api.board.dto;

import babfriend.api.board.entity.BoardComment;
import babfriend.api.common.service.FileUtils;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardCommentResponseDto {

    private Long id;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
    private String profileImageUrl;

    @Builder
    private BoardCommentResponseDto(Long id, String writer, String content, LocalDateTime createdAt, String profileImageUrl) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.profileImageUrl = profileImageUrl;
    }

    public static List<BoardCommentResponseDto> ofList(List<BoardComment> boardComments) {
        return boardComments.stream().map(BoardCommentResponseDto::of)
                .toList();
    }

    private static BoardCommentResponseDto of(BoardComment boardComment) {

        String profileImageUrl = boardComment.getUser().getProfileImageUrl();

        if (profileImageUrl != null && !profileImageUrl.startsWith("http")) {
            profileImageUrl = FileUtils.url + "/image/" + profileImageUrl;
        }

        return BoardCommentResponseDto.builder()
                .id(boardComment.getId())
                .writer(boardComment.getUser().getNickName())
                .content(boardComment.getContent())
                .createdAt(boardComment.getCreatedAt())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
