package babfriend.api.board.dto;

import babfriend.api.board.entity.Board;
import babfriend.api.board.type.CategoryType;
import babfriend.api.common.service.FileUtils;
import babfriend.api.user.type.GenderType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writerImageUrl;
    private String writer;
    private String location;
    private CategoryType categoryType;
    private LocalDateTime eatTime;
    private boolean alcohol;
    private int currentJoin;
    private int joinLimit;
    private boolean ageGroupLimit;
    private Integer up;
    private Integer down;
    private GenderType genderType;
    private boolean fix;
    private LocalDateTime lastModifiedAt;
    private String linkUrl;
    private int priceRange;
    private String writerEmail;
    private boolean changed;
    private List<BoardCommentResponseDto> boardComments;

    @Builder
    private BoardResponseDto(Long id, String title, String content, String writerImageUrl, String writer, String location, CategoryType categoryType, LocalDateTime eatTime, boolean alcohol, int currentJoin, int joinLimit, boolean ageGroupLimit, Integer up, Integer down, GenderType genderType, boolean fix, LocalDateTime lastModifiedAt, String linkUrl, int priceRange, String writerEmail, boolean changed, List<BoardCommentResponseDto> boardComments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerImageUrl = writerImageUrl;
        this.writer = writer;
        this.location = location;
        this.categoryType = categoryType;
        this.eatTime = eatTime;
        this.alcohol = alcohol;
        this.currentJoin = currentJoin;
        this.joinLimit = joinLimit;
        this.ageGroupLimit = ageGroupLimit;
        this.up = up;
        this.down = down;
        this.genderType = genderType;
        this.fix = fix;
        this.lastModifiedAt = lastModifiedAt;
        this.linkUrl = linkUrl;
        this.priceRange = priceRange;
        this.writerEmail = writerEmail;
        this.changed = changed;
        this.boardComments = boardComments;
    }

    public static BoardResponseDto of(Board board) {

        String profileImageUrl = board.getBabManager().getProfileImageUrl();

        if (profileImageUrl != null && !profileImageUrl.startsWith("http")) {
            profileImageUrl = FileUtils.url + "/image/" + profileImageUrl;
        }

        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerImageUrl(profileImageUrl)
                .writer(board.getBabManager().getNickName())
                .location(board.getLocation())
                .categoryType(board.getCategoryType())
                .alcohol(board.isAlcohol())
                .currentJoin(board.getCurrentJoin())
                .joinLimit(board.getJoinLimit())
                .ageGroupLimit(board.isAgeGroupLimit())
                .up(board.isAgeGroupLimit() ? board.getBabManager().getBirthYear() - 4 : 0)
                .down(board.isAgeGroupLimit() ? board.getBabManager().getBirthYear() + 4 : 0)
                .genderType(board.getGenderType())
                .eatTime(board.getEatTime())
                .fix(board.isFix())
                .lastModifiedAt(board.getLastModifiedAt())
                .linkUrl(board.getLinkUrl())
                .priceRange(board.getPriceRange())
                .writerEmail(board.getBabManager().getEmail())
                .changed(!board.getCreatedAt().isEqual(board.getLastModifiedAt()))
                .boardComments(BoardCommentResponseDto.ofList(board.getBoardComments()))
                .build();

    }
}
