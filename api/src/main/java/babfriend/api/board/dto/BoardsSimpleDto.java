package babfriend.api.board.dto;

import babfriend.api.board.entity.Board;
import babfriend.api.board.entity.BobMeeting;
import babfriend.api.board.entity.QBoard;
import babfriend.api.board.entity.QBobMeeting;
import babfriend.api.board.type.CategoryType;
import babfriend.api.common.service.FileUtils;
import babfriend.api.user.type.GenderType;
import com.querydsl.core.Tuple;
import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.common.metrics.internals.IntGaugeSuite;

import java.time.LocalDateTime;

import static babfriend.api.board.entity.QBoard.board;
import static babfriend.api.board.entity.QBobMeeting.bobMeeting;

@Getter
public class BoardsSimpleDto {

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

    @Builder
    private BoardsSimpleDto(Long id, String title, String content, String writerImageUrl, String writer, String location, CategoryType categoryType, LocalDateTime eatTime, boolean alcohol, int currentJoin, int joinLimit, boolean ageGroupLimit, int up, int down, GenderType genderType, boolean fix, LocalDateTime lastModifiedAt) {
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
    }

    public static BoardsSimpleDto of(Board board) {

        String profileImageUrl = board.getBabManager().getProfileImageUrl();

        if (profileImageUrl != null && !profileImageUrl.startsWith("http")) {
            profileImageUrl = FileUtils.url + "/image/" + profileImageUrl;
        }

        return BoardsSimpleDto.builder()
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
                .build();
    }

    public static BoardsSimpleDto of(Tuple tuple) {

        Board board = tuple.get(QBoard.board);

        String profileImageUrl = board.getBabManager().getProfileImageUrl();

        if (profileImageUrl != null && !profileImageUrl.startsWith("http")) {
            profileImageUrl = FileUtils.url + "/image/" + profileImageUrl;
        }

        return BoardsSimpleDto.builder()
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
                .build();
    }
}
