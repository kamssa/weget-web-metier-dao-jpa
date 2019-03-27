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

import ci.weget.web.entites.abonnement.CvAbonnement;
import ci.weget.web.entites.abonnement.CvPersonne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.ICvAbonnementMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class CvAbonnementController {
	@Autowired
	ICvAbonnementMetier cvAbonnementMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	private Reponse<CvAbonnement> getCvAbonnementById(Long id) {
		CvAbonnement cvAbonnement = null;
		try {
			cvAbonnement = cvAbonnementMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (cvAbonnement == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le cvAbonnement n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<CvAbonnement>(0, null, cvAbonnement);
	}

	@PostMapping("/cvAbonnement")
	public String creer(@RequestBody CvAbonnement cvAb) throws JsonProcessingException {
		Reponse<CvAbonnement> reponse = null;

		try {
			
			CvAbonnement cv1 = cvAbonnementMetier.creer(cvAb);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cv1.getId()));
			reponse = new Reponse<CvAbonnement>(0, messages, cv1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<CvAbonnement>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	
	
	@PutMapping("/cvAbonnement")
	public String modfier(@RequestBody CvAbonnement modif) throws JsonProcessingException {
		Reponse<CvAbonnement> reponsePersModif = null;
		Reponse<CvAbonnement> reponse = null;
		// on recupere la personne a modifier
				reponsePersModif = getCvAbonnementById(modif.getId());
				if (reponsePersModif.getBody() != null) {
					try {
						CvAbonnement cv = cvAbonnementMetier.modifier(modif);
						List<String> messages = new ArrayList<>();
						messages.add(String.format("%s a modifier avec succes", cv.getId()));
						reponse = new Reponse<CvAbonnement>(0, messages, cv);
					} catch (InvalideTogetException e) {

						reponse = new Reponse<CvAbonnement>(1, Static.getErreursForException(e), null);
					}

				} else {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("Le cv abonnement n'existe pas"));
					reponse = new Reponse<CvAbonnement>(0, messages, null);
				}

				return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les cv abonnemnt de la base 
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/cvAbonnement")
	public String findAll() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<CvAbonnement>> reponse;
		try {
			List<CvAbonnement> cv = cvAbonnementMetier.findAll();
			if (!cv.isEmpty()) {
				reponse = new Reponse<List<CvAbonnement>>(0, null, cv);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de cv abonnement trouve");
				reponse = new Reponse<List<CvAbonnement>>(1, messages, new ArrayList<>());
			}
		} catch (Exception e) {
			reponse = new Reponse<List<CvAbonnement>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	
	@GetMapping("/cvAbonnement/{id}")
	public String findById(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<CvAbonnement> reponse;
		try {
			reponse = getCvAbonnementById(id);
			reponse = new Reponse<CvAbonnement>(0, null, reponse.getBody());
		} catch (Exception e) {
			reponse = new Reponse<CvAbonnement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/cvAbonnement/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, cvAbonnementMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@DeleteMapping("/cvAbonnement")
	public String supprimerAll(@RequestBody List<CvAbonnement> cvAbonnement) throws JsonProcessingException {
		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format("pas de cv abonnement enregistrer "));
			reponse = new Reponse<Boolean>(0, messages, cvAbonnementMetier.supprimer(cvAbonnement));

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
