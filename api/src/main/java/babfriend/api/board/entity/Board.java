package babfriend.api.board.entity;


import babfriend.api.board.type.CategoryType;
import babfriend.api.common.BaseEntity;
import babfriend.api.user.entity.User;
import babfriend.api.user.type.GenderType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bab_manger_id")
    private User babManager;

    @OneToMany(mappedBy = "board")
    private List<BobMeeting> bobMeetings = new ArrayList<>();

    @Enumerated(STRING)
    private CategoryType categoryType;

    @Enumerated(STRING)
    private GenderType genderType;

    private String title;
    private String content;
    private String linkUrl;
    private String location;

    private LocalDateTime eatTime;
    private Integer joinLimit;
    private boolean fix;
    private Integer priceRange;
    private boolean alcohol;
    private boolean ageGroupLimit;

    @Builder
    private Board(User babManager, CategoryType categoryType, GenderType genderType, String title, String content, String linkUrl, String location, LocalDateTime eatTime, Integer joinLimit, boolean fix, Integer priceRange, boolean alcohol, boolean ageGroupLimit) {
        this.babManager = babManager;
        this.categoryType = categoryType;
        this.genderType = genderType;
        this.title = title;
        this.content = content;
        this.linkUrl = linkUrl;
        this.location = location;
        this.eatTime = eatTime;
        this.joinLimit = joinLimit;
        this.fix = fix;
        this.priceRange = priceRange;
        this.alcohol = alcohol;
        this.ageGroupLimit = ageGroupLimit;
    }
}
