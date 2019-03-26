package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.commande.LigneCommande;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ILigneCommandeMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class LigneCommendeController {
	@Autowired
	private ILigneCommandeMetier ligneCommandeMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	@PostMapping("/ligneCommande")
	public String creer(@RequestBody LigneCommande entite) throws JsonProcessingException {
		Reponse<LigneCommande> reponse;
		try {
			LigneCommande l1 = ligneCommandeMetier.creer(entite);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s à été créer avec succes", l1.getId()));
			reponse = new Reponse<LigneCommande>(0, messages, l1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<LigneCommande>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/ligneCommande")
	public String modifier(@RequestBody LigneCommande modif) throws JsonProcessingException {
		Reponse<LigneCommande> reponsePersModif = null;
		Reponse<LigneCommande> reponse = null;

		// on recupere la personne a modifier
		LigneCommande l = ligneCommandeMetier.findById(modif.getId());
		
			try {
				LigneCommande p2 = ligneCommandeMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  a modifier avec succes", p2.getId()));
				reponse = new Reponse<LigneCommande>(0, messages, p2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<LigneCommande>(1, Static.getErreursForException(e), null);
			}

		

		return jsonMapper.writeValueAsString(reponse);
	}

	
	// obtenir la liste des membres
	/*@GetMapping("/ligneCommande")
	public String findAllTypePersonne(@PathVariable("type") String type) throws JsonProcessingException {
		Reponse<List<Personne>> reponse;

		try {
			List<Personne> personneTous = membreMetier.personneALL(type);
			if (!personneTous.isEmpty()) {
				reponse = new Reponse<List<Personne>>(0, null, personneTous);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de personnes enregistrées");
				reponse = new Reponse<List<Personne>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recherche les membres par login
	@GetMapping("/membresLogin/{login}")
	public String chercherMembresParLogin(@PathVariable String login) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Personne> reponse = null;
		reponse = getMembreByLogin(login);
		return jsonMapper.writeValueAsString(reponse);

	}
*/
}
