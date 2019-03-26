package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.RoleRepository;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;
import ci.weget.web.exception.InvalideTogetException;


@Service
public class RoleMetierImpl implements IRoleMetier{
   
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role creer(Role entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return roleRepository.save(entity);
	}

	@Override
	public Role modifier(Role modif) throws InvalideTogetException {
		Optional<Role> role = roleRepository.findById(modif.getId());

		if (role.isPresent()) {
			
			if (role.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return roleRepository.save(modif);
	}

	@Override
	public List<Role> findAll() {
		List<Role> rolesTrouves = null;
		// return abonnementRepository.findAbonnementParIdBlock(id);
		List<Role> roles = roleRepository.findAll();

		rolesTrouves = roles.stream()
				
				.collect(Collectors.toList());

		return rolesTrouves;
	}

	@Override
	public Role findById(Long id) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		roleRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Role> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role findByRoleName(RoleName name) {
		// TODO Auto-generated method stub
		return roleRepository.findByName(name).get();
	}
	
}
