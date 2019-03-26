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

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IEspaceMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class EspaceController {

	@Autowired
	private IEspaceMetier espaceMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	private Reponse<Espace> getEspaceById(Long id) {
		Espace espace = null;
		try {
			espace = espaceMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (espace.equals(null)) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("l'espace n'existe pas"));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Espace>(0, null, espace);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/espace")
	public String create(@RequestBody Espace espace) throws JsonProcessingException {
		Reponse<Espace> reponse;

		try {

			Espace b1 = espaceMetier.creer(espace);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", b1.getLibelle()));
			reponse = new Reponse<Espace>(0, messages, b1);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Espace>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/espace")
	public String update(@RequestBody Espace modif) throws JsonProcessingException {
		
		Reponse<Espace> reponse = null;
		Reponse<Espace> reponsePersModif = null;
		// on recupere abonnement a modifier
				reponsePersModif = getEspaceById(modif.getId());
				if (reponsePersModif.getBody() != null) {
					try {
						Espace espace = espaceMetier.modifier(modif);
						List<String> messages = new ArrayList<>();
						messages.add(String.format("%s a modifier avec succes", espace.getId()));
						reponse = new Reponse<Espace>(0, messages, espace);
					} catch (InvalideTogetException e) {

						reponse = new Reponse<Espace>(1, Static.getErreursForException(e), null);
					}

				} else {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("l'espace n'existe pas"));
					reponse = new Reponse<Espace>(0, messages, null);
				}



		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/espace")
	public String findAll() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Espace>> reponse;
		try {
			List<Espace> mats = espaceMetier.findAll();
			reponse = new Reponse<List<Espace>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<Espace>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un espace par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/espace/{id}")
	public String getById(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Espace> reponse = null;
		
		try {
			Espace espace = espaceMetier.findById(id);
			reponse = new Reponse<Espace>(0, null, espace);
		} catch (Exception e) {
			reponse = new Reponse<Espace>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////// recherche a partir de mot cle
	///////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////
	@GetMapping("/getEspaceByMc")
	public String getEspaceByMc(@RequestParam String mc) throws JsonProcessingException {
		Reponse<List<Espace>> reponse = null;

		try {
			List<Espace> blocks = espaceMetier.getEspaceByMc(mc);
			if (!blocks.isEmpty()) {
				reponse = new Reponse<List<Espace>>(0, null, blocks);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas de block enregistrer "));
				reponse = new Reponse<List<Espace>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////////////
	///////////// Supprimer un block a partir de son identifiant
	///////////////////////////////////////////////////////////////////////////////////////// //////////////////////////
	@DeleteMapping("/espace/{id}")
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		
		Espace b = null;
		
			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s a ete supprime", b.getLibelle()));

				reponse = new Reponse<Boolean>(0, messages, espaceMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		
		return jsonMapper.writeValueAsString(reponse);
	}
	////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////
	////// ajouter une photo a la base a partir du libelle d'un block
	///////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////

	@PostMapping("/photoEspace")
	public String createPhoto(@RequestParam(name = "image_photo") MultipartFile file) throws Exception {
		Reponse<Espace> reponse = null;
		Reponse<Espace> reponseParLibelle;
		// recuperer le libelle à partir du nom de la photo
		String libelle = file.getOriginalFilename();
		Espace b  = espaceMetier.findByLibelle(libelle);
		
		System.out.println(b);

		String path = "http://wegetback:8080/getPhotoEspace/" + b.getVersion() + "/" + b.getId();
		System.out.println(path);
		
			String dossier = togetImage+ "/" + "espace" + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				b.setPathPhoto(path);
				System.out.println(path);
				file.transferTo(new File(dossier + b.getId()));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", b.getLibelle()));
				reponse = new Reponse<Espace>(0, messages, espaceMetier.modifier(b));

			} catch (Exception e) {

				reponse = new Reponse<Espace>(1, Static.getErreursForException(e), null);
			}

		
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getPhotoEspace/{version}/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable Long version, @PathVariable Long id)
			throws FileNotFoundException, IOException {

		
		String dossier = togetImage+ "/" + "espace" + "/"+id;
        File f = new File(dossier);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

}
