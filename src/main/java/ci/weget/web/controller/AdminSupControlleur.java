package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.UserRecord;

import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAdminSupMetier;
import ci.weget.web.metier.IRoleMetier;
import ci.weget.web.modeles.JwtAuthenticationResponse;
import ci.weget.web.modeles.LoginRequest;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.modeles.ReponsePaiement;
import ci.weget.web.security.JwtTokenProvider;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class AdminSupControlleur {
	@Autowired
	private IAdminSupMetier adminSupMetier;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private IRoleMetier roleMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;

	

	
	@PostMapping("/auth")
	public String authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse=null;
		ReponsePaiement<ResponseEntity<?>, String> response=null;
		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getLoginOrTelephone(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		
       
        	reponse = new Reponse<ResponseEntity<?>>(0, null, ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
    		response = new ReponsePaiement<ResponseEntity<?>, String>(0, null,
    				ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
			return jsonMapper.writeValueAsString(response);

	}

	@PostMapping("/admin")
	public String creerSup(@RequestBody Personne entite) throws Exception {
		Reponse<Personne> reponse;
		UserRecord record = adminSupMetier.createUser(entite);
		try {
			
			Role userRole = roleMetier.findByRoleName(RoleName.ADMINSUP);
			entite.setRoles(Collections.singleton(userRole));
			Personne p1 = adminSupMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", p1.getNomComplet()));
			reponse = new Reponse<Personne>(0, messages, p1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	
	
}
