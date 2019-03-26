package ci.weget.web.metier;

import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;

public interface IRoleMetier extends Imetier<Role, Long> {
	public Role findByRoleName(RoleName name);
}
