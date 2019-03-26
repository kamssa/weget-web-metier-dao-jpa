package ci.weget.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.weget.web.dao.PersonnesRepository;
import ci.weget.web.entites.personne.Personne;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	PersonnesRepository personnesRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String loginOrTelephone) throws UsernameNotFoundException {
		
		Personne user = personnesRepository.findByLoginOrTelephone(loginOrTelephone, loginOrTelephone)
				.orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouve : " + loginOrTelephone));

		return Personne.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		
		 
		Personne user = personnesRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return Personne.create(user);
	}
}