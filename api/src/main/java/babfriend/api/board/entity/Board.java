package babfriend.api.board.entity;


import babfriend.api.board.type.CategoryType;
import babfriend.api.user.entity.User;
import babfriend.api.user.type.GenderType;
import jakarta.persistence.*;
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
public class Board {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "babManger_id")
    private User babManager;

    @OneToMany(mappedBy = "joinUser")
    private List<FoodJoin> joinUsers = new ArrayList<>();

    @Enumerated(STRING)
    private CategoryType categoryType;

    @Enumerated(STRING)
    private GenderType genderType;

    private String title;
    private String content;
    private String linkUrl;

    private LocalDateTime eatTime;
    private int joinLimit;
    private boolean fix;
    private int priceRange;
    private boolean alcohol;
    private boolean ageGroupLimit;
}
