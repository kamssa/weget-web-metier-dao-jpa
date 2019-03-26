package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.PartenaireToget;
import ci.weget.web.entites.ecole.PartenaireEcole;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IEspaceMetier;
import ci.weget.web.metier.IPartenaireTogetMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
class PartenaireTogetController {

	@Autowired
	private IPartenaireTogetMetier partenaireTogetMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<PartenaireToget> getPartenaireTogetById(Long id) {
		PartenaireToget partenaireToget = null;
		try {
			partenaireToget = partenaireTogetMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (partenaireToget == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le partenaire n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<PartenaireToget>(0, null, partenaireToget);
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/partenaireToget")
	public String creer(@RequestBody PartenaireToget partenaireToget) throws JsonProcessingException {
		Reponse<PartenaireToget> reponse;

		try {

			PartenaireToget partenaire = partenaireTogetMetier.creer(partenaireToget);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<PartenaireToget>(0, messages, partenaire);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<PartenaireToget>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/partenaireToget")
	public String modfierUnPartenaireToget(@RequestBody PartenaireToget modif) throws JsonProcessingException {
		Reponse<PartenaireToget> reponsePersModif = null;
		Reponse<PartenaireToget> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getPartenaireTogetById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				PartenaireToget b2 = partenaireTogetMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", b2.getId()));
				reponse = new Reponse<PartenaireToget>(0, messages, b2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<PartenaireToget>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le block n'existe pas"));
			reponse = new Reponse<PartenaireToget>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/partenaireToget")
	public String findAllPartenaire() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<PartenaireToget>> reponse;
		try {
			List<PartenaireToget> mats = partenaireTogetMetier.findAll();
			reponse = new Reponse<List<PartenaireToget>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<PartenaireToget>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/partenaireToget/{id}")
	public String chercherPartenaireParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<PartenaireToget> reponse = null;

		reponse = getPartenaireTogetById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	
	/////////////////////////////////////////////////////////////////////////////////////////
	///////////// Supprimer un block a partir de son identifiant
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/partenaireToget/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		PartenaireToget b = null;
		if (!erreur) {
			Reponse<PartenaireToget> responseSup = getPartenaireTogetById(id);
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

				reponse = new Reponse<Boolean>(0, messages, partenaireTogetMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PostMapping("/photoPartenaireToget")
	public String creerPhoto(@RequestParam(name = "image_photo") 
	MultipartFile file,
			@RequestParam(name="id")
	Long id) throws Exception {
		Reponse<PartenaireToget> reponse;

		try {

			PartenaireToget p = partenaireTogetMetier.createImagePartenaireToget(file, id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<PartenaireToget>(0, messages, p);

		} catch (Exception e) {

			reponse = new Reponse<PartenaireToget>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoPartenaireToget/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotosPartenaire(@PathVariable Long version, @PathVariable Long id,@PathVariable String libelle)
			throws FileNotFoundException, IOException {

		byte[] img=null;
		img= partenaireTogetMetier.getImagePartenaireToget(version, id,libelle);
		return img;
	}
}
