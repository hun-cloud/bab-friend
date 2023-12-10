package babfriend.api.notification.entity;


import babfriend.api.common.BaseEntity;
import babfriend.api.notification.type.NotificationType;
import babfriend.api.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String content;

    private boolean isRead;

    private boolean isValid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
