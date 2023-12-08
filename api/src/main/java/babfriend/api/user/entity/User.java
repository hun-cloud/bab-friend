package babfriend.api.user.entity;

import babfriend.api.board.entity.FoodJoin;
import babfriend.api.user.type.BBTI;
import babfriend.api.user.type.GenderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String oauthId;

    private String name;

    private int temperature;

    @Enumerated(EnumType.STRING)
    private BBTI BBTI;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    private int age;

    @OneToMany(mappedBy = "member")
    private List<FoodJoin> foodJoins = new ArrayList<>();
}
