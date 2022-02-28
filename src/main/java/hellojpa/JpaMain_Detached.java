package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain_Detached {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            Member member = entityManager.find(Member.class, 150L);
            member.setName("AAAA");

            //JPA에서 특정 Entity를 더 이상 관리하지 않는다.
            entityManager.detach(member);

            // 영속성 컨텍스트 전체 초기화 데이터를 변경해도 데이터가 더이상 변경되지 않는다.
            entityManager.clear();

            // 영속성 컨텍스트를 종료
            entityManager.close();

            // 영속성 컨텍스트가 완전히 초기화 되었기 때문에 영속성 컨텍스트 1차 캐시 대상이 된다.
            // 테스트 케이스를 작성할 때 가시적으로 확인시 유용하게 사용할 수 있다.
            Member member2 = entityManager.find(Member.class, 150L);



            System.out.println("--------------");
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
