package ci.weget.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.entites.abonnement.Abonnement;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AbonnementTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AbonnementRepository abonnementRepository;
    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
      //  Abonnement alex = new Abonnement();
      //  entityManager.persist(alex);
      //  entityManager.flush();
     
        
    }
}
