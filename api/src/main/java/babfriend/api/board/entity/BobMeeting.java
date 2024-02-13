package babfriend.api.board.entity;


import babfriend.api.common.BaseEntity;
import babfriend.api.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BobMeeting extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "bob_meeting_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User joinUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private BobMeeting(User joinUser, Board board) {
        this.joinUser = joinUser;
        this.board = board;
    }
}
