package ci.weget.web.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Membre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.entites.personne.RoleName;
import ci.weget.web.entites.personne.VerificationToken;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.listener.OnRegistrationCompleteEvent;
import ci.weget.web.metier.IEspaceMetier;
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
public class RegistractionController {

	@Autowired
	private IMembreMetier membreMetier;
	@Autowired
	private IRoleMetier roleMetier;

	@Autowired
	private IEspaceMetier blocksMetier;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;
	@Autowired
	ApplicationEventPublisher eventPublisher;

	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	////////////////////////////////////////////////////////////////
	//////////////// Rechercher une personne a partir de son identifiant///////////

	private Reponse<Personne> getMembreById(final long id) {
		Personne personne = null;
		try {
			personne = membreMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", id));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	private Reponse<Personne> getMembreByLogin(final String login) {
		Personne personne = null;
		try {
			personne = membreMetier.findByLogin(login);

		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		} catch (InvalideTogetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (personne == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La personne n'exste pas", login));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);

	}

	// recuprer le block a partir de son identifiant
	private Reponse<Espace> getBlock(final Long id) {
		// on récupère le block
		Espace block = null;
		try {
			block = blocksMetier.findById(id);
		} catch (Exception e1) {
			return new Reponse<Espace>(1, Static.getErreursForException(e1), null);
		}
		// block existant ?
		if (block == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le block n'exste pas", id));
			return new Reponse<Espace>(2, messages, null);
		}
		// ok
		return new Reponse<Espace>(0, null, block);
	}

	@PostMapping("/login")
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

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@PostMapping("/registration")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String registerUser(@Valid @RequestBody Personne signUpRequest,
			@RequestParam(value = "action") String action, BindingResult result1, HttpServletRequest request,
			Errors errors) throws Exception, FirebaseAuthException {

		Reponse<ResponseEntity<?>> reponse;
		
		UserRecord record = membreMetier.createUser(signUpRequest);
		List<String> messages = new ArrayList<>();
		messages.add(String.format("%s firebase a ete creer avec succee", record.getUid()));
		
		
		if (result1.hasErrors()) {
			throw new RuntimeException("erreur");
		}

		Role userRole = roleMetier.findByRoleName(RoleName.MEMBRE);
		signUpRequest.setRoles(Collections.singleton(userRole));
		signUpRequest.setActived(true);
		Membre registered = (Membre) membreMetier.creer(signUpRequest);
		//////////////////////////////////////////////////
		if (registered == null) {
			result1.rejectValue("email", "message.regError");
		}
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{username}")
				.buildAndExpand(registered.getLogin()).toUri();
		reponse = new Reponse<ResponseEntity<?>>(0, null,
				ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully")));

		try {

			String appUrl = request.getContextPath();
			System.out.println(request.getContextPath());

			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), action));
			reponse = new Reponse<ResponseEntity<?>>(0, null,
					ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully")));
			System.out.println(request.getContextPath());
		} catch (Exception e) {

			reponse = new Reponse<ResponseEntity<?>>(1, Static.getErreursForException(e), null);

		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/regitrationConfirm")
	public String confirmRegistration(@RequestParam(value = "token") String token,
			@RequestParam(value = "login") String login) throws InvalideTogetException, JsonProcessingException {

		Reponse<Personne> reponse = null;

		Membre user = null;
		// on recupre le membre a patir de son token dans la base
		user = membreMetier.getMembre(token);
		if (user.getLogin().equals(login)) {

			if (user.isEnabled() == false) {

				final String result = membreMetier.validateVerificationToken(token);
				if (result.equals("VALID")) {
					// user = membreMetier.getMembre(token);

					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s à été créer avec succes avec statut membres", user.getLogin()));
					reponse = new Reponse<Personne>(0, messages, user);

				} else {
					throw new RuntimeException("votre code a expire" + result);
				}
			} else {
				throw new RuntimeException("vous etes deja active");
			}
		} else {
			throw new RuntimeException("mauvais login");
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/resendRegistrationToken")
	public String resendRegistrationToken(HttpServletRequest request, @RequestParam("login") String login,
			@RequestParam(value = "action") String action) throws JsonProcessingException {

		Reponse<Personne> reponse = getMembreByLogin(login);
		Membre membre = (Membre) reponse.getBody();
		if (membre.isEnabled() == false) {
			VerificationToken verifier = membreMetier.getVericationTokenParMembre(membre.getId());

			Membre user = membreMetier.getMembre(verifier.getToken());

			membreMetier.generateNewVerificationToken(verifier.getToken());
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), action));
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes avec statut membres", user.getLogin()));
			reponse = new Reponse<Personne>(0, messages, user);
			reponse = new Reponse<Personne>(0, messages, membre);

		} else {
			throw new RuntimeException("desole,vous etes deja active ");
			// reponse = new Reponse<Personne>(1, mes., null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/verifierLoginToken")
	public String verifierloginToken(HttpServletRequest request, @RequestParam("login") String login,
			@RequestParam("token") String token) throws JsonProcessingException {
		Reponse<Boolean> reponseBoolean;

		Reponse<Personne> reponse = getMembreByLogin(login);
		Membre membre = (Membre) reponse.getBody();

		VerificationToken verifier = membreMetier.getVericationTokenParMembre(membre.getId());

		LocalDateTime dateActuelle = LocalDateTime.now();
		if (dateActuelle.isBefore(verifier.getExpiryDate()) && verifier.getToken().equals(token)) {
			reponseBoolean = new Reponse<Boolean>(0, null, true);
		} else if (dateActuelle.isAfter(verifier.getExpiryDate())) {
			reponseBoolean = new Reponse<Boolean>(0, null, false);
		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Opreation a echoue"));
			reponseBoolean = new Reponse<Boolean>(0, messages, false);
		}

		return jsonMapper.writeValueAsString(reponseBoolean);
	}

	// change user password
	@GetMapping("/passwordOublier")
	public String changeUserPassword(HttpServletRequest request, @RequestParam("login") String login,
			@RequestParam("action") String action) throws JsonProcessingException {
		Reponse<Personne> reponse = getMembreByLogin(login);
		Membre membre = (Membre) reponse.getBody();

		VerificationToken verifier = membreMetier.getVericationTokenParMembre(membre.getId());

		Membre user = membreMetier.getMembre(verifier.getToken());

		membreMetier.generateNewVerificationToken(verifier.getToken());
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), action));
		List<String> messages = new ArrayList<>();
		messages.add(String.format("%s à été créer avec succes avec statut membres", user.getLogin()));
		reponse = new Reponse<Personne>(0, messages, user);
		reponse = new Reponse<Personne>(0, messages, membre);

		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/newPassword")
	public String registerPassword(@RequestParam("login") String login, @RequestParam("password") String password,
			@RequestParam("repassword") String repassword, @RequestParam("token") String token) throws Exception {
		Reponse<Personne> reponse = null;
		reponse = getMembreByLogin(login);
		Membre membre = (Membre) reponse.getBody();
		VerificationToken verifier = membreMetier.getVericationTokenParMembre(membre.getId());
		if (verifier.getToken().equals(token)) {
			Membre user = membreMetier.getMembre(verifier.getToken());

			LocalDateTime dateActuelle = LocalDateTime.now();

			try {

				Membre p1 = (Membre) membreMetier.modifierPassword(login, password, repassword);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s à été créer avec succes avec statut membres", p1.getLogin()));
				reponse = new Reponse<Personne>(0, messages, p1);

			} catch (Exception e) {
				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);

			}

		} else {
			throw new RuntimeException("mauvais token");
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@PostMapping("/resetPassword/{login}")
	public String modifierMotDePasse(@PathVariable("login") String login, @RequestParam("password") String password,
			@RequestParam("repassword") String repassword) throws JsonProcessingException {

		Reponse<Personne> reponse;

		try {

			Personne p = membreMetier.modifierPassword(login, password, repassword);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Personne>(0, messages, p);

		} catch (Exception e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/updatePassword")
	public String modifierPasswordCopmte(@RequestParam("id") Long id, @RequestParam("password") String password,
			@RequestParam("nouveauPassword") String nouveauPassword,
			@RequestParam("confirmePassword") String confirmePassword) throws JsonProcessingException {
		Reponse<Personne> reponse = null;

		try {
			Personne p2 = membreMetier.changerPasswordCompte(id, password, nouveauPassword, confirmePassword);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s %s a modifier avec succes", p2.getNom(), p2.getPrenom()));
			reponse = new Reponse<Personne>(0, messages, p2);
		} catch (InvalideTogetException e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

}
