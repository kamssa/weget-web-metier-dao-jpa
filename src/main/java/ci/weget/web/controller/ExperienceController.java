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

import ci.weget.web.entites.abonnement.Experiences;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IExperiencesMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class ExperienceController {
	@Autowired
	IExperiencesMetier experiencesMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	private Reponse<Experiences> getExperiencesById(Long id) {
		Experiences experiences = null;
		try {
			experiences = experiencesMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (experiences == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Experiences>(0, null, experiences);
	}
	
	@PostMapping("/experience")
	public String creer(@RequestBody Experiences experiences) throws JsonProcessingException {
		Reponse<Experiences> reponse = null;

		try {
			
			Experiences exp = experiencesMetier.creer(experiences);

		
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", exp.getId()));
			reponse = new Reponse<Experiences>(0, messages, exp);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Experiences>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/experience")
	public String modfierUneExperiences(@RequestBody Experiences modif) throws JsonProcessingException {
		Reponse<Experiences> reponsePersModif = null;
		Reponse<Experiences> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getExperiencesById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Experiences b2 = experiencesMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", b2.getId()));
				reponse = new Reponse<Experiences>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Experiences>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("L'experience n'existe pas"));
			reponse = new Reponse<Experiences>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/experience")
	public String findAll() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Experiences>> reponse;
		try {
			List<Experiences> experiences = experiencesMetier.findAll();
			if (!experiences.isEmpty()) {
				reponse = new Reponse<List<Experiences>>(0, null, experiences);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'experiences enregistrées");
				reponse = new Reponse<List<Experiences>>(1, messages, new ArrayList<>());
			}
		} catch (Exception e) {
			reponse = new Reponse<List<Experiences>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/getExperienceByIdCv/{id}")
	 public String experienceByIdCv(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<List<Experiences>> reponse;
			try {
				List<Experiences> mats = experiencesMetier.findAllExperienceParCv(id);
				reponse = new Reponse<List<Experiences>>(0, null, mats);
			} catch (Exception e) {
				reponse = new Reponse<List<Experiences>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/experience/{id}")
	public String chercherExperienceParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Experiences> reponse = null;

		reponse = getExperiencesById(id);

		return jsonMapper.writeValueAsString(reponse);

	}
	@DeleteMapping("/experience/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		Experiences b = null;
		if (!erreur) {
			Reponse<Experiences> responseSup = getExperiencesById(id);
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

				reponse = new Reponse<Boolean>(0, messages, experiencesMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
}
