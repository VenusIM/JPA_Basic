package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain_EntityManagerFactory {
    public static void main(String[] args) {

        //xml에서 기술했던 persistence name을 넣어준다.
        //Web 서버가 올라오는 시점에 하나만 생성 되는 것이고
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        /*
        ★★한 트랜젝션마다 EntityManager을 만들어 줘야한다.
        고객의 요청이 올때마다 사용했다 버렸다 동작한다.
        쓰레드간 공유를 하면 안된다. 장애 발생 우려★★
        */
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //실제 동작 코드 작성 구역
        //★★JPA의 모든 변경은 트랜잭션 안에서 작업을 해야한다.★★

        // 견고한 코딩 스프링이 지원하여 고려하지 않아도 되나 참고하자.
        try {
            // 정상적인 로직 수행시 커밋

            // ========== Create ==========
            /*
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            entityManager.persist(member);
            */

            // ========== Read ==========
            //특정 인원을 조회

            /*
            Member findMember = entityManager.find(Member.class, 1L);
            System.out.println("findMember = "+ findMember.getId());
            System.out.println("findMember = "+ findMember.getName());
            */

            // ★★ 조건에 맞는 인원을 조회 JPQL ★★

            /*
            대상이 테이블이 아닌 객체이다. 테이블이 대상이 된다는 것은 JPA의 사상을 꺠는 것과 마찮가지이다.
            객체지향 쿼리라 볼수 있고 방언에 맞춰 각 DB에 맞게 번역해준다. 페이징 처리 등 여러가지로 유용하다.
            실제 물리적인 테이블을 대상으로 쿼리를 날리게 되면 해당 DB에 종속적인 개발이 되기 떼문에
            JPAsms SQL를 추상화한 JPQL이라는 객체 지향 쿼리 언어를 제공한다.
            */

            List<Member> result = entityManager.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for(Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            // ========== Delete ==========
//            entityManager.remove(findMember);

            //Update
            // JPA를 통해서 Entity를 가져오면 JPA가 Entity를 관리한다.
            // 변경이 됬는지 않됬는지 Transation을 Commit하는 시점에 체크를 한다.
            // 변경 사항이 있으면 Commit 직전에 Update 쿼리를 날리고 Commit한다.

            /*
            Member findMember = entityManager.find(Member.class, 1L);
            findMember.setName("HelloJPA");
            */

            entityTransaction.commit();

        } catch (Exception e) {
            // 예외사항 발생시 롤백
            entityTransaction.rollback();
        } finally {
            // Entity Manager를 닫아준다.
            entityManager.close();
        }
        //실제 application 이 완전히 끝나면 EntityMenageFactory 를 닫아줘야 한다.
        entityManagerFactory.close();

    }
}
