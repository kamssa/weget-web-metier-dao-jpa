package ci.weget.web.controller.combo;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.combo.Pays;
import ci.weget.web.entites.combo.Quartier;
import ci.weget.web.entites.combo.Ville;
import ci.weget.web.entites.commande.Commande;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IEspaceMetier;
import ci.weget.web.metier.combo.IComboPaysMetier;
import ci.weget.web.metier.combo.IComboQuartierMetier;
import ci.weget.web.metier.combo.IComboVilleMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
@Transactional
public class ComboController {
	@Autowired
	private IComboPaysMetier comboPaysMetier;
	@Autowired
	private IComboVilleMetier comboVilleMetier;
	@Autowired
	private IComboQuartierMetier comboQuartierMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un pays a partir de son identifiant
	private Reponse<Pays> getPaysParId(Long id) {
		Pays pays = null;
		try {
			pays = comboPaysMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (pays == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le pays n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Pays>(0, null, pays);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////// recuperer une ville a partir de son identifiant
	private Reponse<Ville> getVilleParId(Long id) {
		Ville ville = null;
		try {
			ville = comboVilleMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (ville == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la ville n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Ville>(0, null, ville);
	}
	private Reponse<Quartier> getQuartierParId(Long id) {
		Quartier quartier = null;
		try {
			quartier = comboQuartierMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (quartier == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("le quartier n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<Quartier>(0, null, quartier);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un pays dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/pays")
	public String creer(@RequestBody Pays[] pays) throws JsonProcessingException {
		Reponse<Pays> reponse = null;
		//Reponse<List<Pays>> reponseList = null;

		try {
           for(Pays pay:pays) {
        	   Pays p = comboPaysMetier.creer(pay);
        	   List<String> messages = new ArrayList<>();
   			messages.add(String.format("%s  à été créer avec succes", p.getNom_fr_fr()));
   			reponse = new Reponse<Pays>(0, messages, p);
           }
			
			} catch (InvalideTogetException e) {

			reponse = new Reponse<Pays>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/pays")
	public String modfierUnPays(@RequestBody Pays[] modif) throws JsonProcessingException {
		Reponse<Pays> reponsePersModif = null;
		Reponse<Pays> reponse = null;
		
		try {
	           for(Pays pay:modif) {
	        	 reponsePersModif=  getPaysParId(pay.getId());
	        	 if (reponsePersModif==null) {
					throw new RuntimeException("pas de pays renvoye");
				}
	        	   Pays p1= reponsePersModif.getBody();
	        	   System.out.println("les elements a modifier"+p1);
	        	   Pays p = comboPaysMetier.modifier(pay);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été modifie avec succes", p.getNom_fr_fr()));
	   			reponse = new Reponse<Pays>(0, messages, p);
	           }
				
				} catch (InvalideTogetException e) {

					reponse = new Reponse<Pays>(1, Static.getErreursForException(e), null);
				}

	// on recupere la personne a modifier
		
		
		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer  le pays par id
		@GetMapping("/pays/{id}")
		public String findPaysParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
			Reponse<Pays> reponse;
			try {
				reponse = getPaysParId(id);
				Pays p=reponse.getBody();
				reponse = new Reponse<Pays>(0, null, p);
			} catch (Exception e) {
				reponse = new Reponse<Pays>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	// recuperer touts les pays
	@GetMapping("/pays")
	public String findAllPays() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Pays>> reponse;
		try {
			List<Pays> p = comboPaysMetier.findAll();
			reponse = new Reponse<List<Pays>>(0, null, p);
		} catch (Exception e) {
			reponse = new Reponse<List<Pays>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// ajouter touts les ville
	@PostMapping("/ville")
	public String ajouterville(@RequestBody Ville[] villes) throws JsonProcessingException, InvalideTogetException {
		Reponse<Ville> reponse = null;
		//Reponse<List<Pays>> reponseList = null;

		try {
           for(Ville ville:villes) {
        	   
        	   Ville v = comboVilleMetier.creer(ville);
        	   List<String> messages = new ArrayList<>();
   			messages.add(String.format("%s  à été créer avec succes", v.getNom_fr_fr()));
   			reponse = new Reponse<Ville>(0, messages, v);
           }
			
			} catch (InvalideTogetException e) {

			reponse = new Reponse<Ville>(1, Static.getErreursForException(e), null);
		}
		return  jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/ville")
	public String modfierUneVille(@RequestBody Ville[] modif) throws JsonProcessingException {
		Reponse<Ville> reponsePersModif = null;
		Reponse<Ville> reponse = null;
		
		try {
	           for(Ville ville:modif) {
	        	 reponsePersModif=  getVilleParId(ville.getId());
	        	 if (reponsePersModif==null) {
					throw new RuntimeException("pas de pays renvoye");
				}
	        	 Ville v= reponsePersModif.getBody();
	        	   System.out.println("les elements a modifier"+v);
	        	   Ville v1 = comboVilleMetier.modifier(ville);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été modifie avec succes", v1.getNom_fr_fr()));
	   			reponse = new Reponse<Ville>(0, messages, v1);
	           }
				
				} catch (InvalideTogetException e) {

					reponse = new Reponse<Ville>(1, Static.getErreursForException(e), null);
				}

	// on recupere la personne a modifier
		
		
		return jsonMapper.writeValueAsString(reponse);
	}
	// recuperer  le pays par id
			@GetMapping("/ville/{id}")
			public String findVilleParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
				Reponse<Ville> reponse;
				try {
					reponse = getVilleParId(id);
					Ville v=reponse.getBody();
					reponse = new Reponse<Ville>(0, null, v);
				} catch (Exception e) {
					reponse = new Reponse<Ville>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);

			}

	// recuperer touts les pays
		@GetMapping("/ville")
		public String findAllVille() throws JsonProcessingException, InvalideTogetException {
			Reponse<List<Ville>> reponse;
			try {
				List<Ville> v = comboVilleMetier.findAll();
				reponse = new Reponse<List<Ville>>(0, null, v);
			} catch (Exception e) {
				reponse = new Reponse<List<Ville>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	@GetMapping("/villeParPays/{id}")
	public String getPersonneBlock(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<Ville>> reponse;
		try {
			List<Ville> villes = comboVilleMetier.findAllVilleParPays(id);
			reponse = new Reponse<List<Ville>>(0, null, villes);
		} catch (Exception e) {
			reponse = new Reponse<List<Ville>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// ajouter touts les ville
		@PostMapping("/quartier")
		public String ajouterQuartier(@RequestBody Quartier[] quartiers) throws JsonProcessingException, InvalideTogetException {
			Reponse<Quartier> reponse = null;
			//Reponse<List<Pays>> reponseList = null;

			try {
	           for(Quartier quartier:quartiers) {
	        	   
	        	   Quartier q = comboQuartierMetier.creer(quartier);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été créer avec succes", q.getNom_fr_fr()));
	   			reponse = new Reponse<Quartier>(0, messages, q);
	           }
				
				} catch (InvalideTogetException e) {

				reponse = new Reponse<Quartier>(1, Static.getErreursForException(e), null);
			}
			return  jsonMapper.writeValueAsString(reponse);
		}

		@PutMapping("/quartier")
		public String modfierUnQuartier(@RequestBody Quartier[] modif) throws JsonProcessingException {
			Reponse<Quartier> reponsePersModif = null;
			Reponse<Quartier> reponse = null;
			
			try {
		           for(Quartier quartier:modif) {
		        	 reponsePersModif=  getQuartierParId(quartier.getId());
		        	 if (reponsePersModif==null) {
						throw new RuntimeException("pas de pays renvoye");
					}
		        	 Quartier q= reponsePersModif.getBody();
		        	   System.out.println("les elements a modifier"+q);
		        	   Quartier q1 = comboQuartierMetier.modifier(quartier);
		        	   List<String> messages = new ArrayList<>();
		   			messages.add(String.format("%s  à été modifie avec succes", q1.getNom_fr_fr()));
		   			reponse = new Reponse<Quartier>(0, messages, q1);
		           }
					
					} catch (InvalideTogetException e) {

						reponse = new Reponse<Quartier>(1, Static.getErreursForException(e), null);
					}

		// on recupere la personne a modifier
			
			
			return jsonMapper.writeValueAsString(reponse);
		}
		// recuperer  le pays par id
				@GetMapping("/quartier/{id}")
				public String findQuartierParId(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
					Reponse<Quartier> reponse;
					try {
						reponse = getQuartierParId(id);
						Quartier q=reponse.getBody();
						reponse = new Reponse<Quartier>(0, null, q);
					} catch (Exception e) {
						reponse = new Reponse<Quartier>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}

		// recuperer touts les pays
			@GetMapping("/quartier")
			public String findAllQuartier() throws JsonProcessingException, InvalideTogetException {
				Reponse<List<Quartier>> reponse;
				try {
					List<Quartier> q = comboQuartierMetier.findAll();
					reponse = new Reponse<List<Quartier>>(0, null, q);
				} catch (Exception e) {
					reponse = new Reponse<List<Quartier>>(1, Static.getErreursForException(e), new ArrayList<>());
				}
				return jsonMapper.writeValueAsString(reponse);

			}

			@GetMapping("/quartierParVille/{id}")
			public String getQuartierParVille(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
				Reponse<List<Quartier>> reponse;
				try {
					List<Quartier> quartiers = comboQuartierMetier.qartierParVille(id);
					reponse = new Reponse<List<Quartier>>(0, null, quartiers);
				} catch (Exception e) {
					reponse = new Reponse<List<Quartier>>(1, Static.getErreursForException(e), new ArrayList<>());
				}
				return jsonMapper.writeValueAsString(reponse);

			}
			@DeleteMapping("/pays/{id}")
			public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

				Reponse<Boolean> reponse = null;
				boolean erreur = false;
				Pays p = null;
				if (!erreur) {
					Reponse<Pays> responseSup = getPaysParId(id);
					p = responseSup.getBody();
					if (responseSup.getStatus() != 0) {
						reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
						erreur = true;

					}
				}
				if (!erreur) {
					// suppression
					try {

						List<String> messages = new ArrayList<>();
						messages.add(String.format(" %s a ete supprime", p.getId()));

						reponse = new Reponse<Boolean>(0, messages, comboPaysMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
					}
				}
				return jsonMapper.writeValueAsString(reponse);
			}
			@DeleteMapping("/ville/{id}")
			public String supprimerVille(@PathVariable("id") Long id) throws JsonProcessingException {

				Reponse<Boolean> reponse = null;
				boolean erreur = false;
				Ville v = null;
				if (!erreur) {
					Reponse<Ville> responseSup = getVilleParId(id);
					v = responseSup.getBody();
					if (responseSup.getStatus() != 0) {
						reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
						erreur = true;

					}
				}
				if (!erreur) {
					// suppression
					try {

						List<String> messages = new ArrayList<>();
						messages.add(String.format(" %s a ete supprime", v.getId()));

						reponse = new Reponse<Boolean>(0, messages, comboVilleMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
					}
				}
				return jsonMapper.writeValueAsString(reponse);
			}
			@DeleteMapping("/quartier/{id}")
			public String supprimerQuartier(@PathVariable("id") Long id) throws JsonProcessingException {

				Reponse<Boolean> reponse = null;
				boolean erreur = false;
                 Quartier q = null;
				if (!erreur) {
					Reponse<Quartier> responseSup = getQuartierParId(id);
					q = responseSup.getBody();
					if (responseSup.getStatus() != 0) {
						reponse = new Reponse<>(responseSup.getStatus(), responseSup.getMessages(), null);
						erreur = true;

					}
				}
				if (!erreur) {
					// suppression
					try {

						List<String> messages = new ArrayList<>();
						messages.add(String.format(" %s a ete supprime", q.getId()));

						reponse = new Reponse<Boolean>(0, messages, comboQuartierMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
					}
				}
				return jsonMapper.writeValueAsString(reponse);
			}
}
