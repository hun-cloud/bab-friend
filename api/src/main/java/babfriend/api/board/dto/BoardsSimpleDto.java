package babfriend.api.board.dto;

import babfriend.api.board.entity.Board;
import babfriend.api.board.type.CategoryType;
import babfriend.api.common.service.FileUtils;
import babfriend.api.user.type.GenderType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardsSimpleDto {

    private Long id;
    private String title;
    private String content;
    private String writerImageUrl;
    private String writer;
    private String shortendLocation;
    private CategoryType categoryType;
    private LocalDateTime eatTime;
    private boolean alcohol;
    private int currentJoin;
    private int joinLimit;
    private boolean ageGroupLimit;
    private int up;
    private int down;
    private GenderType genderType;
    private boolean fix;
    private LocalDateTime lastModifiedAt;

    @Builder
    private BoardsSimpleDto(Long id, String title, String content, String writerImageUrl, String writer, String shortendLocation, CategoryType categoryType, LocalDateTime eatTime, boolean alcohol, int currentJoin, int joinLimit, boolean ageGroupLimit, int up, int down, GenderType genderType, boolean fix, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerImageUrl = writerImageUrl;
        this.writer = writer;
        this.shortendLocation = shortendLocation;
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
    }

    public static BoardsSimpleDto of(Board board) {
        return BoardsSimpleDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerImageUrl(FileUtils.url + board.getBabManager().getProfileImageUrl())
                .writer(board.getBabManager().getNickName())
                .shortendLocation(board.getLocation())
                .categoryType(board.getCategoryType())
                .alcohol(board.isAlcohol())
                .currentJoin(board.getCurrentJoin())
                .joinLimit(board.getJoinLimit())
                .ageGroupLimit(board.isAgeGroupLimit())
                .up(board.isAgeGroupLimit() ? board.getBabManager().getBirthYear() - 4 : null)
                .down(board.isAgeGroupLimit() ? board.getBabManager().getBirthYear() + 4 : null)
                .genderType(board.getGenderType())
                .eatTime(board.getEatTime())
                .fix(board.isFix())
                .lastModifiedAt(board.getLastModifiedAt())
                .build();
    }
}
