package ci.weget.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import ci.weget.web.entites.ecole.Formation;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IFormationMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class FormationController {
	@Autowired
	private IFormationMetier formationMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<Formation> getFormationById(Long id) {
		Formation foramtion = null;
		try {
			foramtion = formationMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (foramtion == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la formation n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Formation>(0, null, foramtion);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une formation dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/formations")
	public String creer(@RequestBody Formation formation) throws JsonProcessingException {
		Reponse<Formation> reponse;

		try {

			Formation f1 = formationMetier.creer(formation);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", f1.getId()));
			reponse = new Reponse<Formation>(0, messages, f1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Formation>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/formations")
	public String modfierUnBlock(@RequestBody Formation modif) throws JsonProcessingException {
		Reponse<Formation> reponsePersModif = null;
		Reponse<Formation> reponse = null;

		// on recupere la personne a modifier
		reponsePersModif = getFormationById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Formation f2 = formationMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", f2.getId()));
				reponse = new Reponse<Formation>(0, messages, f2);
			} catch (InvalideTogetException e) {

				reponse = new Reponse<Formation>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La formation n'existe pas"));
			reponse = new Reponse<Formation>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer tous les blocks de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/formations")
	public String findAllBlocks() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Formation>> reponse;
		try {
			List<Formation> mats = formationMetier.findAll();
			reponse = new Reponse<List<Formation>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/formationDetailAbonnement/{id}")
	public String formationParSousBlocks(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Formation>> reponse;
		try {
			List<Formation> formations = formationMetier.getFormationByEcole(id);
			reponse = new Reponse<List<Formation>>(0, null, formations);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/formations/{id}")
	public String chercherFormationParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Formation> reponse = null;

		reponse = getFormationById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/formations/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, formationMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	@PostMapping("/imageFormation")
	public String creerFormationPhoto(@RequestParam(name = "image_photo") MultipartFile file, @RequestParam Long id)
			throws Exception {
		Reponse<Formation> reponse;

		try {

			Formation f = formationMetier.createImageFormation(file, id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Formation>(0, messages, f);

		} catch (Exception e) {

			reponse = new Reponse<Formation>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PostMapping("/formulaireFormation")
	public String creerFormationFormulaire(@RequestParam(name = "image_photo") MultipartFile file,
			@RequestParam Long id) throws Exception {
		
		Reponse<Formation> reponse;

		try {

			Formation f = formationMetier.createFormulaireFormation(file, id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Formation>(0, messages, f);

		} catch (Exception e) {

			reponse = new Reponse<Formation>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PostMapping("/catalogueFormation")
	public String creerFormationCatalogue(@RequestParam(name = "image_photo") MultipartFile file, @RequestParam Long id)
			throws Exception {
		
		Reponse<Formation> reponse;

		try {

			Formation f = formationMetier.createCatalogueFormation(file, id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Formation>(0, messages, f);

		} catch (Exception e) {

			reponse = new Reponse<Formation>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo de couverture avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoFormation/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> getPhotosFormation(@PathVariable Long version, @PathVariable Long id, HttpServletRequest request) throws FileNotFoundException, IOException {
		ResponseEntity<Resource> img=null;
		img= formationMetier.getImageFormation(version, id,request);
		return img;
	

	}

	@GetMapping(value = "/getFormulaireFormation/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> getFormulaireFormation(@PathVariable Long version, @PathVariable Long id,
			 HttpServletRequest request) throws FileNotFoundException, IOException {

		ResponseEntity<Resource> img=null;
		img= formationMetier.getImageFormulaireFormation(version, id,request);
		return img;
	}

	@GetMapping(value = "/getCatalogueFormation/{version}/{id}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> getCatalogueFormation(@PathVariable Long version,
			@PathVariable Long id,HttpServletRequest request) throws FileNotFoundException, IOException {

		ResponseEntity<Resource> img=null;
		img= formationMetier.getImageFormation(version, id,request);
		return img;
	}
}
