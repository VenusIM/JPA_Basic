package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // JPA 사용시 필수 어노테이션.
// @Table(name = "USER") // 테이블 이름이 다를 경우 설정이 가능한 어노테이션
public class Member {

    @Id
    private Long id;
    
   // @Column(name = "username") // 컬럼명이 다를 경우 설정이 가능한 어노테이션
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
