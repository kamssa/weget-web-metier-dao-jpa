package ci.weget.web.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	 Optional<Role> findByName(RoleName roleName);
		
}
