package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.ecole.PartenaireEcole;
import ci.weget.web.entites.personne.Role;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IPartenaireTogetMetier;
import ci.weget.web.metier.IRoleMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class RoleController {
	@Autowired
	private IRoleMetier roleMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<Role> getRoleById(Long id) {
		Role role = null;
		try {
			role = roleMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (role == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le role n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Role>(0, null, role);
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un role dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/role")
	public String creer(@RequestBody Role role) throws JsonProcessingException {
		Reponse<Role> reponse;

		try {

			Role b1 = roleMetier.creer(role);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Role>(0, messages, b1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Role>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/role")
	public String modfierUnPartenaire(@RequestBody Role modif) throws JsonProcessingException {
		Reponse<Role> reponsePersModif = null;
		Reponse<Role> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getRoleById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Role b2 = roleMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", b2.getId()));
				reponse = new Reponse<Role>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Role>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le block n'existe pas"));
			reponse = new Reponse<Role>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/role")
	public String findAllPartenaire() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Role>> reponse;
		try {
			List<Role> mats = roleMetier.findAll();
			reponse = new Reponse<List<Role>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<Role>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/role/{id}")
	public String chercherPartenaireParId(@PathVariable("id") Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Role> reponse = null;

		reponse = getRoleById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	
	/////////////////////////////////////////////////////////////////////////////////////////
	///////////// Supprimer un block a partir de son identifiant
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/role/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Role b = null;
		if (!erreur) {
			Reponse<Role> responseSup = getRoleById(id);
			b = responseSup.getBody();
			if (responseSup.getStatus() != 0) {
				reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
				erreur = true;

			}
		}
		if (!erreur) {
			// suppression
			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s a ete supprime", b.getId()));

				reponse = new Reponse<Boolean>(0, messages, roleMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
}
