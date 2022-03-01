package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity // JPA 사용시 필수 어노테이션.
// @Table(name = "USER") // 테이블 이름이 다를 경우 설정이 가능한 어노테이션
public class Member {

    @Id
    private Long id;

    //@Column(name = "name") // field와 Column명이 다를 경우 매핑할 Column이름
    @Column(name = "name"
            ,updatable = false // DB에서 강제로 업데이트 하지 않는 이상 JPA를 쓰는 동안은 반영되지 않는다.
            ,nullable = false // DDL 생성 시에 not null 제약 조건이 붙는다. hiberate의 경우 notnull이면 체크를 해준다.
            ,unique = true //한 컬럼에 간단히 유니크 제약 조건을 걸때 사용한다. 하지만 제약조건을 알아 볼 수 없기 때문에 잘 사용하지 않고
                           //@Table의 uniqueConstraints 사용하여 이름까지 직접 지정해준다.
            ,columnDefinition = "varchar(100) default 'EMPTY'" // 문구 그대로 DDL에 삽입되며, 특정 DB에 종속적인 옵션들을 넣을 수 있다.
            )
    private String username;

    //아주 큰 숫자나, 소수점을 사용할 때 이용한다.
    @Column(precision = 19
            ,scale = 2
    )
    private Integer age; // 다른 타입을 사용 가능하다.

    // Enum type이 있는 DB가 있을 수 있지만 왠만한 DB에는 enum type이 없고 enum type을 사용하고 싶을 때 사용하는 어노테이션
    // Default가 ORDINAL이고, 순서를 DB에 저장하는 역할을 하기 때문에 순서가 숫자로 들어간다. 요구사항이 추가 되었을 경우 순서가 보장 되지 않기 때문에 사용에 유의해야 한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 날짜 Type ( DATE 날짜, TIME 시간, TIMESTAMP 날짜시간 )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate testLocalDate;

    private LocalDateTime testLocalDateTime;

    // VARCHAR를 넘어서는 큰 값을 넣을 경우 사용하는 어노테이션, 타입이 문자인 경우 clob으로 생성된다. blob
    @Lob
    private String description;

    // DB 컬럼과 매핑하고 싶지 않을 경우 사용 어노테이션
    @Transient
    private int temp;

    public Member() {
    }

     /*
    @Id
    private Long id;
    @Column(name = "username") // 컬럼명이 다를 경우 설정이 가능한 어노테이션
    @Column(unique = true, length = 10)
    private String name;
    private int age2;

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
    */

}
