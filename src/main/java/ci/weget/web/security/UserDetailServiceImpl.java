package ci.weget.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ci.weget.web.entites.personne.Personne;
import ci.weget.web.metier.IAdminMetier;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
    
	/*@Autowired
	private IAdminMetier adminMetier;
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Personne user = adminMetier.findPersonnesByLogin(login);
		if (user==null) {
			throw new UsernameNotFoundException(login);
		}
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		List<AppRoles> roles = adminMetier.getRoles(user.getId());
		roles.forEach(r->{
			authorities.add(new SimpleGrantedAuthority(r.getNom()));
		});
		return new User(user.getLogin(),user.getPassword(),authorities);
	}
*/
}
