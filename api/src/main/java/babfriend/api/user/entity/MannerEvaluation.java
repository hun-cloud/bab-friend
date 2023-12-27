package babfriend.api.user.entity;

import babfriend.api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MannerEvaluation extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "manner_evaluation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bab_manager_id")
    private User babManager;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "evaluator_id")
    private User evaluator;

    private Integer score;
}
