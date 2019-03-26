package ci.weget.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

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

import ci.weget.web.dao.CursusScolaireRepository;
import ci.weget.web.dao.CvPersonneRepository;
import ci.weget.web.dao.ExperienceRepository;
import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.abonnement.CursusColaire;
import ci.weget.web.entites.abonnement.CvPersonne;
import ci.weget.web.entites.abonnement.Experiences;
import ci.weget.web.entites.commande.Commande;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.exception.ResourceNotFoundException;
import ci.weget.web.metier.IAbonnementMetier;
import ci.weget.web.metier.ICursusMetier;
import ci.weget.web.metier.ICvPersonneMetier;
import ci.weget.web.metier.IExperiencesMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;
import java.util.stream.Collectors;
@RestController
@CrossOrigin
public class CvPersonneController {

	@Autowired
	ICvPersonneMetier cvPersonneMetier;
	@Autowired
	ICursusMetier cursusMetier;
	@Autowired
	CursusScolaireRepository cursusScolaireRepository;
	@Autowired
	ExperienceRepository experienceRepository;
	@Autowired
	IExperiencesMetier experiencesMetier;
	@Autowired
	IAbonnementMetier abonnementMetier;
	@Autowired
	CvPersonneRepository cvPersonneRepository;
	@Autowired
	private ObjectMapper jsonMapper;

	private Reponse<CvPersonne> getCvPersonneById(Long id) {
		CvPersonne cvPersonne = null;
		try {
			cvPersonne = cvPersonneMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (cvPersonne == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le cv n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<CvPersonne>(0, null, cvPersonne);
	}

	
	@PostMapping("/cvPersonne")
	public String creer(@RequestBody CvPersonne cv) throws JsonProcessingException {
		Reponse<CvPersonne> reponse = null;

		try {
			
			CvPersonne cv1 = cvPersonneMetier.creer(cv);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cv1.getId()));
			reponse = new Reponse<CvPersonne>(0, messages, cv1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<CvPersonne>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	
	
	@PutMapping("/cvPersonne")
	public String modfier(@RequestBody CvPersonne modif) throws JsonProcessingException {
		Reponse<CvPersonne> reponsePersModif = null;
		Reponse<CvPersonne> reponse = null;
		// on recupere la personne a modifier
				reponsePersModif = getCvPersonneById(modif.getId());
				if (reponsePersModif.getBody() != null) {
					try {
						CvPersonne cv = cvPersonneMetier.modifier(modif);
						List<String> messages = new ArrayList<>();
						messages.add(String.format("%s a modifier avec succes", cv.getId()));
						reponse = new Reponse<CvPersonne>(0, messages, cv);
					} catch (InvalideTogetException e) {

						reponse = new Reponse<CvPersonne>(1, Static.getErreursForException(e), null);
					}

				} else {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("Le cv n'existe pas"));
					reponse = new Reponse<CvPersonne>(0, messages, null);
				}

				return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les cv de la base 
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/cvPersonne")
	public String findAll() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<CvPersonne>> reponse;
		try {
			List<CvPersonne> cv = cvPersonneMetier.findAll();
			if (!cv.isEmpty()) {
				reponse = new Reponse<List<CvPersonne>>(0, null, cv);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de cv trouve");
				reponse = new Reponse<List<CvPersonne>>(1, messages, new ArrayList<>());
			}
		} catch (Exception e) {
			reponse = new Reponse<List<CvPersonne>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/getCvPersonneByIdAbonnemnt/{idAbonnement}")
	public String getCvPersonneByIdAbonnemnt(@PathVariable("idAbonnement") long id)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<CvPersonne> reponse;
		try {
			CvPersonne cv = cvPersonneMetier.getCvPersonneByIdAbonnemnt(id);
			reponse = new Reponse<CvPersonne>(0, null, cv);
		} catch (Exception e) {
			reponse = new Reponse<CvPersonne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/cvPersonne/{id}")
	public String findCvPersonneId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<CvPersonne> reponse;
		try {
			reponse = getCvPersonneById(id);
			reponse = new Reponse<CvPersonne>(0, null, reponse.getBody());
		} catch (Exception e) {
			reponse = new Reponse<CvPersonne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/cvPersonne/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, cvPersonneMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@DeleteMapping("/cvPersonne")
	public String supprimerAll(@RequestBody List<CvPersonne> cvPersonnes) throws JsonProcessingException {
		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format("pas de cv enregistrer "));
			reponse = new Reponse<Boolean>(0, messages, cvPersonneMetier.supprimer(cvPersonnes));

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
