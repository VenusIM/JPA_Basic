package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain_PermanenceContext {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
           /*
           ========== 비영속, 영속, 준영속, 삭제 ==========

            //비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            //영속
            
            Before와 After와 무관하게 쿼리가 전송된다.
            영속 상태가 된다고 바로 쿼리가 날라가는 것이 아니고
            transaction을 commit하는 시점에 query가 전송된다.
           
            System.out.println("=== BEFORE ===");
            entityManager.persist(member);
            System.out.println("=== AFTER ===");
            
            //준영속
            entityManager.detach(member);

            //삭제
            entityManager.remove(member);
            */

            /*
            ========== 1차 캐시 ==========
            //Select Query가 날라가지 않았다.
            
            왜?
            저장할 떄 1차 캐시에서 저장하고, 같은 PK로 조회했기 떄문에 DB에서
            조회하는 것이 아니라 1차 캐시에서 조회했기 떄문이다.
            

            Member findMember = entityManager.find(Member.class, 101L);

            System.out.println("findMember.id = "+findMember.getId());
            System.out.println("findMember.name = "+findMember.getName());
            */

            /*
            ========== 동일 트랜젝션 내에서 동일성 보장 ==========

            두번째 부터는 1차 캐시로 부터 가져온다. 따라서 Query는 한번만 전송된다.

            Member findMember1 = entityManager.find(Member.class, 101L);
            Member findMember2 = entityManager.find(Member.class, 101L);

            // 동일성 보장
            System.out.println("result = "+(findMember1 == findMember2)); // true
            */

            /*
            ========== 트랜잭션을 지원하는 쓰기 지연 ==========
            순간 순간 DB에 데이터를 전송하면 최적화 할 수 있는 여지가 없다.
            xml에 batch size를 지정해 줌으로서 사이즈 만큼 쌓아두었다가 한번에 쿼리를 전송할 수 있다.
            이러한 옵션 하나로 성능을 먹고 들어갈 수 있다.

            Member member1 = new Member(150l, "A");
            Member member2 = new Member(160L, "B");

            entityManager.persist(member1);
            entityManager.persist(member2);

            System.out.println("=============");

            */

            Member member = entityManager.find(Member.class, 150L);
            member.setName("ZZ");

            entityTransaction.commit();

        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
        }
    }
