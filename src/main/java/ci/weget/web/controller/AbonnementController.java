package ci.weget.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IAbonnementMetier;
import ci.weget.web.modele.metier.ICreeReAbonne;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class AbonnementController {
	@Autowired
	private IAbonnementMetier abonnementMetier;

	@Autowired
	private ICreeReAbonne creeReAbonne;
    @Autowired
	private ObjectMapper jsonMapper;
	


	private Reponse<Abonnement> getAbonnementById(Long id) {
		Abonnement abonnement = null;
		try {
			abonnement = abonnementMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (abonnement.equals(null)) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("abonnement n'existe pas"));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Abonnement>(0, null, abonnement);
	}

	
	// creer un abonnement
		@PostMapping("/abonnement")
		public String create(@RequestBody Abonnement abonnement) throws JsonProcessingException {
			Reponse<Abonnement> reponse = null;
			// on recupere abonnement a modifier
				
						try {
							Abonnement ab = abonnementMetier.creer(abonnement);
							List<String> messages = new ArrayList<>();
							messages.add(String.format("%s a modifier avec succes", ab.getId()));
							reponse = new Reponse<Abonnement>(0, messages, ab);
						} catch (InvalideTogetException e) {

							reponse = new Reponse<Abonnement>(1, Static.getErreursForException(e), null);
						}

					


			return jsonMapper.writeValueAsString(reponse);
		}


	// update de abonnement
	@PutMapping("/abonnement")
	public String update(@RequestBody Abonnement modif) throws JsonProcessingException {
		Reponse<Abonnement> reponsePersModif=null;
		Reponse<Abonnement> reponse = null;
		// on recupere abonnement a modifier
				reponsePersModif = getAbonnementById(modif.getId());
				if (reponsePersModif.getBody() != null) {
					try {
						Abonnement ab = abonnementMetier.modifier(modif);
						List<String> messages = new ArrayList<>();
						messages.add(String.format("%s a modifier avec succes", ab.getId()));
						reponse = new Reponse<Abonnement>(0, messages, ab);
					} catch (InvalideTogetException e) {

						reponse = new Reponse<Abonnement>(1, Static.getErreursForException(e), null);
					}

				} else {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("Abonnement n'existe pas"));
					reponse = new Reponse<Abonnement>(0, messages, null);
				}


		return jsonMapper.writeValueAsString(reponse);
	}

	// effectuer un reabonnemnt
		@PostMapping("/reabonneGratuit/{idAbonnement}")
		public String reabonneGratuit(@PathVariable Long idAbonnement) throws JsonProcessingException {
			Reponse<Boolean> reponse;

			try {
				creeReAbonne.creerReAbonnement(idAbonnement);
				// List<Panier> paniers= panierMetier.LesPanierDeLaPersonne(personne.getId());
				// panierMetier.supprimer(paniers);
				List<String> messages = new ArrayList<>();
				messages.add(String.format(" à été créer avec succes"));
				reponse = new Reponse<Boolean>(0, messages, null);

			} catch (Exception e) {

				reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
	// recuperer les abonnemnt du mois
	@GetMapping("/getAbonnementByMois")
	public String abonneParMois() throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<Abonnement>> reponse;
		try {
			LocalDate currentTime = LocalDate.now();
			Month mois = currentTime.getMonth();
			List<Abonnement> abs = abonnementMetier.abonnementByMois(currentTime);
			if (!abs.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, abs);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<Abonnement>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Abonnement>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recuperer les abonnements banis
	@GetMapping("/getAbonnementBani")
	public String abonnesBani() throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<Abonnement>> reponse;
		try {

			List<Abonnement> abs = abonnementMetier.abonnementBani();
			if (!abs.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, abs);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés bani enregistrés");
				reponse = new Reponse<List<Abonnement>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Abonnement>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	//////////// get abonnement par id d'une personne
	@GetMapping("/getAbonnementByIdPersonne/{idPersonne}")
	public String getAbonnementByIdPersonne(@PathVariable("idPersonne") long idPersonne)
			throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Abonnement>> reponse;
		try {
			List<Abonnement> abonnements = abonnementMetier.getAbonnementByIdPersonne(idPersonne);
			if (!abonnements.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, abonnements);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<Abonnement>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Abonnement>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

////////////get distinct abonnement
	@GetMapping("/getDistinctAbonnement")
	public String getOccurenceAbonne() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Personne>> reponse;
		try {
			List<Personne> pers = abonnementMetier.uneOcurrenceAbonne();
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Personne>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas de personne enregistrer "));
				reponse = new Reponse<List<Personne>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Personne>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir tous les abonnes speciaux limite a 50 pesonnes

	@GetMapping("/getAbonnementSpecial")
	public String findAllAbonneSpecial() throws JsonProcessingException {
		Reponse<List<Abonnement>> reponse;
		try {
			List<Abonnement> pers = abonnementMetier.abonnementSpecial();
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas d'abonne special enregistre "));
				reponse = new Reponse<List<Abonnement>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

// get all abonnements
	@GetMapping("/abonnement")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Abonnement>> reponse;
		try {
			List<Abonnement> pers = abonnementMetier.findAll();
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<Abonnement>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir tous les abonnements par leur identifiant
	@GetMapping("/abonnement/{id}")
	public String getById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<Abonnement> reponse;
		try {
			Abonnement db = abonnementMetier.findById(id);
			reponse = new Reponse<Abonnement>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recupere les abonnements par login d'une personne
	@GetMapping("/getAbonnementByLogin/{login}")
	public String getAbonnementByLogin(@PathVariable String login) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<List<Abonnement>> reponse = null;

		try {
			List<Abonnement> db = abonnementMetier.getAbonnementByLogin(login);
			if (!db.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, db);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<Abonnement>>(1, messages, new ArrayList<>());
			}

			
		} catch (Exception e) {

			reponse = new Reponse<List<Abonnement>>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// get abonnement par id de espace
	@GetMapping("/getAbonnementByIdEspace/{idEspace}")
	public String getAbonnementByIdEpace(@PathVariable("idEspace") long idEspace) throws JsonProcessingException {
		Reponse<List<Abonnement>> reponse;

		try {
			List<Abonnement> abonnements = abonnementMetier.getAbonnementByIdEspace(idEspace);
			if (!abonnements.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, abonnements);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<Abonnement>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

//////////recupere abonnement par ville
	@GetMapping("/getAbonnementByVille/{ville}")
	public String chercherParVille(@PathVariable String ville) throws JsonProcessingException {
		Reponse<List<Abonnement>> reponse = null;

		try {
			List<Abonnement> db = abonnementMetier.getAbonnementByVille(ville);
			if (!db.isEmpty()) {
				reponse = new Reponse<List<Abonnement>>(0, null, db);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("pas de personne enregistrer "));
				reponse = new Reponse<List<Abonnement>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// mettre a jour le nombre de vue d'un membre
	@PutMapping("/updateVue")
	public String modifierVue(@RequestBody Espace espace, @RequestBody Personne p) throws JsonProcessingException {
		Reponse<Abonnement> reponse;

		try {
			Abonnement db = abonnementMetier.updateVue(p.getId(), espace.getId());
			List<String> messages = new ArrayList<>();
			messages.add(String.format("detail block à été modifier avec succes"));
			reponse = new Reponse<Abonnement>(0, messages, db);

		} catch (Exception e) {

			reponse = new Reponse<Abonnement>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PostMapping("/abonneSpecial")
	public String abonneSpecial(@RequestBody Personne personne) throws JsonProcessingException {
		Reponse<Boolean> reponse;

		try {

			boolean b = abonnementMetier.creerAbonne(personne);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Boolean>(0, messages, b);

		} catch (Exception e) {

			reponse = new Reponse<Boolean>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// recherche avec plusieurs paramettre
	@GetMapping("/getAbonnementByParam")
	public String rechercheAbonnesParParamettres(@RequestParam(value = "libelle") String libelle,
			@RequestParam(value = "specialite") String specialite, @RequestParam(value = "ville") String ville,
			@RequestParam(value = "latitude") double latitude, @RequestParam(value = "longitude") double longitude,
			@RequestParam(value = "proximite") boolean proximite) throws JsonProcessingException {
		Reponse<List<Abonnement>> reponse;
		try {
			List<Abonnement> db = abonnementMetier.getAbonnementByParam(libelle, specialite, ville, latitude, longitude,
					proximite);
			reponse = new Reponse<List<Abonnement>>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<List<Abonnement>>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@PostMapping("/imageAbonnement")
	public String createPhoto(@RequestParam(name = "image_photo") MultipartFile file,@RequestParam(name = "id") Long id) throws Exception {
		Reponse<Abonnement> reponse;
		try {
			Abonnement db = abonnementMetier.createImageAbonnement(file, id);
			reponse = new Reponse<Abonnement>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	//////// recuperer une photo avec pour retour tableau de byte
	//////// /////////////////////////////////

	@GetMapping(value = "/getImageAbonnement/{version}/{id}/{liblle}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPhotos(@PathVariable Long version, @PathVariable Long id,@PathVariable String libelle)
			throws FileNotFoundException, IOException {

		byte[] img=null;
		img= abonnementMetier.getImageAbonnement(version, id, libelle);
		return img;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
