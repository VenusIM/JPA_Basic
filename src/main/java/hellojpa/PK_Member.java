package hellojpa;

import javax.persistence.*;

@Entity
public class PK_Member {
    // 직접 할당
    @Id
    //직접 할당하는 것이 아닌 자동 할당
    @GeneratedValue(strategy =
            GenerationType.IDENTITY // 데이터베이스에 위임, 예) MySQL의 AUTO_INCREMENT => MySQL5Dialect 일 때 에러 발생

            // GenerationType.SEQUENCE //
            // GenerationType.TABLE
            // GenerationType.AUTO
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
}
