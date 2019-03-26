package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import ci.weget.web.entites.abonnement.Experiences;
import ci.weget.web.entites.competences.DocCompetence;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IDocCompetenceMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DocCompetenceController {

	private final Path fileStorageLocation = null;
	@Autowired
	private IDocCompetenceMetier docCompetenceMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	private Reponse<DocCompetence> getDocumentByTitre(final String titre) {
		DocCompetence document = null;
		try {
			document = docCompetenceMetier.chercherDocParTitre(titre);

		} catch (RuntimeException e) {
			new Reponse<DocCompetence>(1, Static.getErreursForException(e), null);
		}
		if (document == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le document n'exste pas", titre));
			return new Reponse<DocCompetence>(2, messages, null);
		}
		return new Reponse<DocCompetence>(0, null, document);

	}

	private Reponse<DocCompetence> getDocCompetenceById(Long id) {
		DocCompetence doc = null;
		try {
			doc = docCompetenceMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (doc == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le doc n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<DocCompetence>(0, null, doc);
	}

	@PostMapping("/documentCompetence")
	public String creer(@RequestBody DocCompetence doc) throws JsonProcessingException {
		Reponse<DocCompetence> reponse;

		try {

			DocCompetence d = docCompetenceMetier.creer(doc);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<DocCompetence>(0, messages, d);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<DocCompetence>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/documentCompetence")
	public String modifier(@RequestBody DocCompetence doc) throws JsonProcessingException {
		Reponse<DocCompetence> reponse;

		try {

			DocCompetence d = docCompetenceMetier.modifier(doc);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<DocCompetence>(0, messages, d);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<DocCompetence>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/documentCompetence/{id}")
	public String docCompetenceParIdAbonne(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<DocCompetence>> reponse = null;

		try {
			List<DocCompetence> doc = docCompetenceMetier.findAllDocumentsParIdAbonne(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("recuperation effectue"));
			reponse = new Reponse<List<DocCompetence>>(0, messages, doc);
		} catch (Exception e) {

			reponse = new Reponse<List<DocCompetence>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@DeleteMapping("/documentCompetence/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;
		boolean erreur = false;
		DocCompetence b = null;
		if (!erreur) {
			Reponse<DocCompetence> responseSup = getDocCompetenceById(id);
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

				reponse = new Reponse<Boolean>(0, messages, docCompetenceMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer les photos d'une gallery dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////
	@PostMapping("/photoDocumentCompetence")
	public String creerPhoto(@RequestParam(name = "image_doc") MultipartFile file, @RequestParam Long id)
			throws Exception {
		Reponse<DocCompetence> reponse = null;
		Reponse<DocCompetence> reponseParId;
		List<DocCompetence>docs=docCompetenceMetier.findAllDocumentsParIdAbonne(id);
		// recuperer le libelle à partir du nom de la photo
		String titre = file.getOriginalFilename();
		reponseParId = getDocCompetenceById(id);
		DocCompetence d = reponseParId.getBody();
		System.out.println(d);

		String path = "http://wegetback:8080/getDocCompetence/" + id + "/" + titre;
		System.out.println(path);
		if (reponseParId.getStatus() == 0) {
			String dossier = togetImage + "/" + "DocCompetence" + "/" + id + "/";
			File rep = new File(dossier);

			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				d.setPathDocument(path);
				System.out.println(path);
				file.transferTo(new File(dossier + titre));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", d.getTitre()));
				reponse = new Reponse<DocCompetence>(0, messages, docCompetenceMetier.modifier(d));

			} catch (Exception e) {

				reponse = new Reponse<DocCompetence>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("ce document n'existe pas"));
			reponse = new Reponse<DocCompetence>(reponseParId.getStatus(), reponseParId.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer les images de la gallery
	//@PreAuthorize("hasRole('ROLE_MEMBRE')")
	//@RolesAllowed("ROLE_MEMBRE")
	@GetMapping(value = "/getDocCompetence/{id}/{titre}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> getDocsCompetence(@PathVariable Long id,
			@PathVariable String titre, HttpServletRequest request) throws FileNotFoundException, IOException {
		// ResponseEntity<Resource> reponse;
		String dossier = togetImage + "/" + "DocCompetence" + "/" + id + "/" + titre;
		Path filePath = Paths.get(dossier);
		Resource resource = new UrlResource(filePath.toUri());
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			throw new RuntimeException("Desole,nous ne pouvons determiner le type de fichier");
		}
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

		// String dossier = togetImage + "/" + "DocCompetence"+ "/"+id+"/";
		// File fichier = new File(dossier + titre);
		// byte[] img = IOUtils.toByteArray(new FileInputStream(fichier));

		// return img;

	}
}
