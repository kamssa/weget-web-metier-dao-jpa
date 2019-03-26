package ci.weget.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import ci.weget.web.entites.abonnement.Document;
import ci.weget.web.entites.competences.DocCompetence;
import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.ecole.FlashInfo;
import ci.weget.web.entites.ecole.Formation;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IDocumentMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class DocumentController {
	@Autowired
	private IDocumentMetier documentMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	private Reponse<Document> getDocumentByTitre(final String titre) {
		Document document = null;
		try {
			document = documentMetier.chercherDocumentParLibelle(titre);

		} catch (RuntimeException e) {
			new Reponse<Document>(1, Static.getErreursForException(e), null);
		}
		if (document == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le document n'exste pas", titre));
			return new Reponse<Document>(2, messages, null);
		}
		return new Reponse<Document>(0, null, document);

	}

	private Reponse<Document> getDocumentById(final Long id) {
		Document document = null;
		try {
			document = documentMetier.findById(id);

		} catch (RuntimeException e) {
			new Reponse<Document>(1, Static.getErreursForException(e), null);
		}
		if (document == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le document n'exste pas", id));
			return new Reponse<Document>(2, messages, null);
		}
		return new Reponse<Document>(0, null, document);

	}

	@PostMapping("/document")
	public String creer(@RequestBody Document doc) throws JsonProcessingException {
		Reponse<Document> reponse;

		try {

			Document d = documentMetier.creer(doc);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Document>(0, messages, d);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Document>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/document")
	public String modifier(@RequestBody Document doc) throws JsonProcessingException {
		Reponse<Document> reponse;

		try {

			Document d = documentMetier.modifier(doc);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Document>(0, messages, d);

		} catch (InvalideTogetException e) {

			reponse = new Reponse<Document>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/*@GetMapping("/documentParIdDetailAbonnement/{id}")
	public String findDocumentIdSousBlock(@PathVariable Long id)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Document>> reponse;
		try {
			List<Document> docs = documentMetier.findDocumentParIdDetailAbonnement(id);
			reponse = new Reponse<List<Document>>(0, null, docs);
			if (reponse.getBody().isEmpty()) {
				throw new RuntimeException("pas de docs  trouve");
			}
		} catch (Exception e) {
			reponse = new Reponse<List<Document>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
*/
	//////////////////////////////////////////////////////////////////////////////////////
	// enregistrer les documents d'un sous block dans la base
	////////////////////////////////////////////////////////////////////////////////////// base/////////////////////////////////////

	/*@PostMapping("/detailAbonnementDocument")
	public String uploadDocuments(@RequestParam(name = "image_photo") MultipartFile file, @RequestParam Long id)
			throws Exception {
		Reponse<Document> reponse = null;
		Reponse<Document> reponseParLibelle;
		reponseParLibelle = getDocumentById(id);
		String libelle = file.getOriginalFilename();
		Document doc = reponseParLibelle.getBody();
		String titre= doc.getTitre();
		Ecole sb = doc.getDetailAbonnement();
		Long idSb = sb.getId();
		String path = "http://wegetback:8080/getDetailAbonnementDocument/"+sb.getVersion()+"/" + idSb + "/" + titre+"/"+libelle;
		if (reponseParLibelle.getStatus() == 0) {
			String dossier = togetImage + "/" + "detailAbonnementDocuments" + "/" + idSb + "/" + titre + "/";
			File rep = new File(dossier);
			if (!file.isEmpty()) {
				if (!rep.exists() && !rep.isDirectory()) {
					rep.mkdir();
				}
			}
			try {
				// enregistrer le chemin dans la photo
				doc.setPathDocument(path);;
				
				file.transferTo(new File(dossier + libelle));
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s (photo ajouter avec succes)", doc.getTitre()));
				reponse = new Reponse<Document>(0, messages, documentMetier.modifier(doc));

			} catch (Exception e) {

				reponse = new Reponse<Document>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("ce document n'existe pas"));
			reponse = new Reponse<Document>(reponse.getStatus(), reponse.getMessages(), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// Multiple file upload

	
	 * @PostMapping("/sousblockDocument") public String
	 * uploadFileMulti(@RequestParam("image_photo") MultipartFile[]
	 * files, @RequestParam Long id) throws Exception { Reponse<Document> reponse =
	 * null; Reponse<Document> reponseParId; reponseParId = getDocumentById(id);
	 * //appartir de ce libelle on recupere la gallery SousBlock sb = Document doc=
	 * reponseParId.getBody(); String uploadedFileName = Arrays.stream(files) .map(x
	 * -> x.getOriginalFilename()) .filter(x -> !StringUtils.isEmpty(x))
	 * .collect(Collectors.joining(" , "));
	 * 
	 * if (StringUtils.isEmpty(uploadedFileName)) { List<String> messages = new
	 * ArrayList<>(); messages.add(String.format(" Non effectue")); reponse = new
	 * Reponse<Document>(0, messages, null); }
	 * 
	 * try { String ok = saveUploadedFiles(Arrays.asList(files)); List<String>
	 * messages = new ArrayList<>();
	 * 
	 * messages.add(String.format(" ok"));
	 * 
	 * reponse = new Reponse<Document>(0, messages, documentMetier.modifier(doc));
	 * 
	 * } catch (IOException e) { reponse = new
	 * Reponse<Document>(1,Static.getErreursForException(e), null); }
	 * 
	 * return jsonMapper.writeValueAsString(reponse); }
	 

	// Save single file
	
	 * private String saveUploadedFiles(List<MultipartFile> files) throws Exception
	 * { if (Files.notExists(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture"
	 * + "/"))) { init(); }
	 * 
	 * // String randomPath = "";
	 * 
	 * for (MultipartFile file : files) { if (file.isEmpty()) { throw new
	 * Exception("impossible de charger un fichier vide " +
	 * file.getOriginalFilename()); } String ext = file.getOriginalFilename();
	 * String[] extAutorise = { "mp4", "avi", "ogg", "ogv", "jpg", "jpeg", "png",
	 * "gif" }; String fileNameTarget = ""; if (ArrayUtils.contains(extAutorise,
	 * ext)) { fileNameTarget = file.getOriginalFilename(); fileNameTarget =
	 * fileNameTarget.replaceAll(" ", "_"); byte[] bytes = file.getBytes();
	 * 
	 * String fileName = file.getOriginalFilename(); Path path =
	 * Paths.get(togetImage + "/" + "sousBlockDocuments" + "/" + fileName);
	 * Files.write(path, bytes); }
	 * 
	 * }
	 * 
	 * return null; }
	 

	private void init() {
		try {
			Files.createDirectory(Paths.get(togetImage + "/" + "sousBlockPhotoCouverture" + "/"));
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}

	public static String generateRandomPath() {
		String path = UUID.randomUUID().toString().replace("-", "");
		return path;
	}

	// recuperer les images de la gallery
	@GetMapping(value = "/getDetailAbonnementDocument/{version}/{id}/{titre}/{libelle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> getPhotosDocument(@PathVariable String version, @PathVariable Long id,
			@PathVariable String titre, @PathVariable String libelle, HttpServletRequest request)
			throws FileNotFoundException, IOException {

		System.out.println(version);
		String dossier = togetImage + "/" + "detailAbonnementDocuments" + "/" + id + "/" + titre + "/" + libelle;
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

	}*/

}
