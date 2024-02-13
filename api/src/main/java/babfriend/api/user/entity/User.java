package babfriend.api.user.entity;

import babfriend.api.board.entity.Board;
import babfriend.api.board.entity.BoardComment;
import babfriend.api.board.entity.BobMeeting;
import babfriend.api.common.BaseEntity;
import babfriend.api.notification.entity.Notification;
import babfriend.api.review.entity.EatReview;
import babfriend.api.user.dto.UserUpdateDto;
import babfriend.api.user.type.BBTI;
import babfriend.api.user.type.GenderType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String oauthId;

    private String email;

    private String name;

    private String nickName;

    private Integer temperature;

    @Enumerated(EnumType.STRING)
    private BBTI bbti;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    private Integer birthYear;

    private String profileImageUrl;

    @OneToMany(mappedBy = "user")
    private List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "babManager")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "reviewee")
    private List<EatReview> receivedReviews = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer")
    private List<EatReview> sendReviews = new ArrayList<>();

    @OneToMany(mappedBy = "joinUser")
    private List<BobMeeting> babMeetings = new ArrayList<>();

    @Builder
    private User(String email, String name, String nickName, GenderType genderType, int birthYear, String profileImageUrl) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.genderType = genderType;
        this.birthYear = birthYear;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateUserInfo(String nickName, String path) {
        this.nickName = nickName;

        if (path != null) {
            this.profileImageUrl = path;
        }
    }

    @PrePersist
    public void prePersist() {
        this.temperature = this.temperature == null ? 34 : this.temperature;
    }
}
