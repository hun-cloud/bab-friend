package babfriend.api.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class UserImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_image_id")
    private Long id;

    private String url;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateUrl(String url) {
        this.url = url;
    }
}
