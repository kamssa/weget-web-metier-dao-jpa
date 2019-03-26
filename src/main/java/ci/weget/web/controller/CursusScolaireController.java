package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ci.weget.web.entites.abonnement.CursusColaire;
import ci.weget.web.entites.abonnement.Experiences;
import ci.weget.web.entites.commande.Commande;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ICursusMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class CursusScolaireController {
	
	@Autowired
	ICursusMetier cursusMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	private Reponse<CursusColaire> getCursusColaireById(Long id) {
		CursusColaire cursusColaire = null;
		try {
			cursusColaire = cursusMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (cursusColaire == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<CursusColaire>(0, null, cursusColaire);
	}
	
	@PostMapping("/cursus")
	public String creer(@RequestBody CursusColaire cursusColaire) throws JsonProcessingException {
		Reponse<CursusColaire> reponse = null;

		try {
			
			CursusColaire cursus = cursusMetier.creer(cursusColaire);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cursus.getId()));
			reponse = new Reponse<CursusColaire>(0, messages, cursus);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<CursusColaire>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/cursus")
	public String modfierUnCursus(@RequestBody CursusColaire modif) throws JsonProcessingException {
		Reponse<CursusColaire> reponsePersModif = null;
		Reponse<CursusColaire> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getCursusColaireById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				CursusColaire b2 = cursusMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", b2.getId()));
				reponse = new Reponse<CursusColaire>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<CursusColaire>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le block n'existe pas"));
			reponse = new Reponse<CursusColaire>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/cursus")
	public String findAll() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<CursusColaire>> reponse;
		try {
			List<CursusColaire> cs = cursusMetier.findAll();
			if (!cs.isEmpty()) {
				reponse = new Reponse<List<CursusColaire>>(0, null, cs);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de Cursus scolaire trouve");
				reponse = new Reponse<List<CursusColaire>>(1, messages, new ArrayList<>());
			}
		} catch (Exception e) {
			reponse = new Reponse<List<CursusColaire>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/cursusParCv/{id}")
	 public String cursusColaireparIdCv(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<List<CursusColaire>> reponse;
			try {
				List<CursusColaire> cs = cursusMetier.findAllCursusColaireParCv(id);
				if (!cs.isEmpty()) {
					reponse = new Reponse<List<CursusColaire>>(0, null, cs);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de cursus scolaire trouve");
					reponse = new Reponse<List<CursusColaire>>(1, messages, new ArrayList<>());
				}
			} catch (Exception e) {
				reponse = new Reponse<List<CursusColaire>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/cursus/{id}")
	public String chercherCursusColaireParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<CursusColaire> reponse = null;

		reponse = getCursusColaireById(id);

		return jsonMapper.writeValueAsString(reponse);

	}
	@DeleteMapping("/cursus/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		CursusColaire b = null;
		if (!erreur) {
			Reponse<CursusColaire> responseSup = getCursusColaireById(id);
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

				reponse = new Reponse<Boolean>(0, messages, cursusMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
}
