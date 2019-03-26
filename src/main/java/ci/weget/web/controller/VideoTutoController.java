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

import ci.weget.web.entites.VideoTuto;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IVideoTutoMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class VideoTutoController  {
	@Autowired
	private IVideoTutoMetier videoTutoMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<VideoTuto> getVideoTutoById(Long id) {
		VideoTuto videoTuto = null;
		try {
			videoTuto = videoTutoMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (videoTuto == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<VideoTuto>(0, null, videoTuto);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un VideoTuto dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/videoTuto")
	public String creer(@RequestBody VideoTuto videoTuto) throws JsonProcessingException {
		Reponse<VideoTuto> reponse;

		try {

			VideoTuto b1 = videoTutoMetier.creer(videoTuto);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", b1.getLibelle()));
			reponse = new Reponse<VideoTuto>(0, messages, b1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<VideoTuto>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un VideoTuto dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/videoTuto")
	public String modfierUnVideoTuto(@RequestBody VideoTuto modif) throws JsonProcessingException {
		Reponse<VideoTuto> reponsePersModif = null;
		Reponse<VideoTuto> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getVideoTutoById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				VideoTuto b2 = videoTutoMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", b2.getLibelle()));
				reponse = new Reponse<VideoTuto>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<VideoTuto>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le block n'existe pas"));
			reponse = new Reponse<VideoTuto>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/videoTuto")
	public String findAllVideoTuto() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<VideoTuto>> reponse;
		try {
			List<VideoTuto> mats = videoTutoMetier.findAll();
			reponse = new Reponse<List<VideoTuto>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<VideoTuto>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un VideoTuto par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/videoTuto/{id}")
	public String chercherVideoTutoParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<VideoTuto> reponse = null;

		reponse = getVideoTutoById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	
	///////////// Supprimer un VideoTuto a partir de son identifiant
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/videoTuto/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		VideoTuto b = null;
		if (!erreur) {
			Reponse<VideoTuto> responseSup = getVideoTutoById(id);
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
				messages.add(String.format(" %s a ete supprime", b.getLibelle()));

				reponse = new Reponse<Boolean>(0, messages, videoTutoMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
}
