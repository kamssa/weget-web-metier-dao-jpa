package ci.weget.web.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.personne.Administrateur;
import ci.weget.web.entites.personne.Membre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.listener.OnRegistrationCompleteEvent;
import ci.weget.web.metier.IAdminMetier;
import ci.weget.web.metier.IMembreMetier;
import ci.weget.web.metier.IRoleMetier;
import ci.weget.web.modeles.ApiResponse;
import ci.weget.web.modeles.JwtAuthenticationResponse;
import ci.weget.web.modeles.LoginRequest;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.modeles.ReponsePaiement;
import ci.weget.web.security.JwtTokenProvider;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class AdminController {

	@Autowired
	private IAdminMetier adminMetier;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private IRoleMetier roleMetier;

	@Autowired
	private ObjectMapper jsonMapper;


    // athentifer un administrateur
	  @PostMapping("/signin")
	  public String authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse;
		ReponsePaiement<ResponseEntity<?>, String> response;
		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getLoginOrTelephone(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		//String login = tokenProvider.getUserPrincipal().getLogin();
		//String login = tokenProvider.getPersonne().getLogin();

		reponse = new Reponse<ResponseEntity<?>>(0, null, ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
		response = new ReponsePaiement<ResponseEntity<?>, String>(0, null,
				ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
		return jsonMapper.writeValueAsString(response);

	}

	
	@PostMapping("/signup")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String registerUser(@Valid @RequestBody Personne signUpRequest,
			@RequestParam(value = "action") String action, BindingResult result1, HttpServletRequest request,
			Errors errors) throws Exception, FirebaseAuthException {

		Reponse<ResponseEntity<?>> reponse = null;
		
		UserRecord record = adminMetier.createUser(signUpRequest);
		List<String> messages = new ArrayList<>();
		messages.add(String.format("%s firebase a ete creer avec succee", record.getUid()));
		Role userRole = roleMetier.findByRoleName(RoleName.MEMBRE);
		signUpRequest.setRoles(Collections.singleton(userRole));
		
		Membre registered = (Membre) adminMetier.creer(signUpRequest);
		
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/admin")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Personne>> reponse;
		List<Personne> personnes= adminMetier.findAll();
		if (!personnes.isEmpty()) {
			reponse = new Reponse<List<Personne>>(0, null, personnes);
		} else {
			List<String> messages = new ArrayList<>();
			messages.add("Pas d'abonnés enregistrées");
			reponse = new Reponse<List<Personne>>(1, messages, new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	
	
	
}
