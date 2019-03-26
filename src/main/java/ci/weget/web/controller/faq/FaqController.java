package ci.weget.web.controller.faq;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.weget.web.entites.combo.Pays;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.faq.CategorieFaq;
import ci.weget.web.entites.faq.QuestionnaireFaq;
import ci.weget.web.entites.faq.ReponseFaq;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.metier.IEspaceMetier;
import ci.weget.web.metier.faq.ICategoryFaqMetier;
import ci.weget.web.metier.faq.IQuestionnaireFaqMetier;
import ci.weget.web.metier.faq.IReponseFaqMetier;
import ci.weget.web.modeles.Reponse;
import ci.weget.web.utilitaires.Static;

@RestController
@CrossOrigin
public class FaqController {

	@Autowired
	private IReponseFaqMetier reponseFaqMetier;
	@Autowired
	private IQuestionnaireFaqMetier questionnaireFaqMetier;
	@Autowired
	private ICategoryFaqMetier categoryFaqMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	//////////// chemin ou sera sauvegarder les photos
	//////////// ////////////////////////////////////////
	@Value("${dir.images}")
	private String togetImage;

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////// recuperer un block a partir de son identifiant
	private Reponse<CategorieFaq> getCategorieFaqById(Long id) {
		CategorieFaq categorieFaq = null;
		try {
			categorieFaq = categoryFaqMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (categorieFaq == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<CategorieFaq>(0, null, categorieFaq);
	}
	private Reponse<QuestionnaireFaq> getQuestionnaireFaqById(Long id) {
		QuestionnaireFaq questionnaireFaq = null;
		try {
			questionnaireFaq = questionnaireFaqMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (questionnaireFaq == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<QuestionnaireFaq>(0, null, questionnaireFaq);
	}
	private Reponse<ReponseFaq> getReponseFaqById(Long id) {
		ReponseFaq reponseFaq = null;
		try {
			reponseFaq = reponseFaqMetier.findById(id);
		} catch (RuntimeException e) {
			new Reponse<>(1, Static.getErreursForException(e), null);
		}
		if (reponseFaq == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la matiere n'existe pas", id));
			new Reponse<>(2, messages, null);

		}
		return new Reponse<ReponseFaq>(0, null, reponseFaq);
	}


	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un block dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/categorieFaq")
	public String creer(@RequestBody CategorieFaq[] categorieFaqs) throws JsonProcessingException {
		Reponse<CategorieFaq> reponse = null;

		try {
	           for(CategorieFaq categorieFaq:categorieFaqs) {
	        	   CategorieFaq p = categoryFaqMetier.creer(categorieFaq);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été créer avec succes", p.getId()));
	   			reponse = new Reponse<CategorieFaq>(0, messages, p);
	           }
				
				} catch (InvalideTogetException e) {

				reponse = new Reponse<CategorieFaq>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
	}
	@PostMapping("/questionnaireFaq")
	public String creer(@RequestBody QuestionnaireFaq[] questionnaireFaqs) throws JsonProcessingException {
		Reponse<QuestionnaireFaq> reponse = null;

		try {
	           for(QuestionnaireFaq categorieFaq:questionnaireFaqs) {
	        	   QuestionnaireFaq p = questionnaireFaqMetier.creer(categorieFaq);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été créer avec succes", p.getId()));
	   			reponse = new Reponse<QuestionnaireFaq>(0, messages, p);
	           }
				
				} catch (InvalideTogetException e) {

				reponse = new Reponse<QuestionnaireFaq>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
	}
	@PostMapping("/reponseFaq")
	public String creer(@RequestBody ReponseFaq[] reponseFaqs) throws JsonProcessingException {
		Reponse<ReponseFaq> reponse = null;

		try {
	           for(ReponseFaq categorieFaq:reponseFaqs) {
	        	   ReponseFaq p = reponseFaqMetier.creer(categorieFaq);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été créer avec succes", p.getId()));
	   			reponse = new Reponse<ReponseFaq>(0, messages, p);
	           }
				
				} catch (InvalideTogetException e) {

				reponse = new Reponse<ReponseFaq>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier un block dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/categorieFaq")
	public String modfierUneCategorieFaq(@RequestBody CategorieFaq[] modif) throws JsonProcessingException {
		Reponse<CategorieFaq> reponsePersModif = null;
		Reponse<CategorieFaq> reponse = null;
		try {
	           for(CategorieFaq pay:modif) {
	        	 reponsePersModif=  getCategorieFaqById(pay.getId());
	        	 if (reponsePersModif==null) {
					throw new RuntimeException("pas de pays renvoye");
				}
	        	 CategorieFaq p1= reponsePersModif.getBody();
	        	   System.out.println("les elements a modifier"+p1);
	        	   CategorieFaq p = categoryFaqMetier.modifier(pay);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été modifie avec succes", p.getId()));
	   			reponse = new Reponse<CategorieFaq>(0, messages, p);
	           }
				
				} catch (InvalideTogetException e) {

					reponse = new Reponse<CategorieFaq>(1, Static.getErreursForException(e), null);
				}

	// on recupere la personne a modifier
		
		
		return jsonMapper.writeValueAsString(reponse);

	}
	@PutMapping("/questionnaireFaq")
	public String modfierUnquestionnaireFaq(@RequestBody QuestionnaireFaq[] modif) throws JsonProcessingException {
		Reponse<QuestionnaireFaq> reponsePersModif = null;
		Reponse<QuestionnaireFaq> reponse = null;

		try {
	           for(QuestionnaireFaq pay:modif) {
	        	 reponsePersModif=  getQuestionnaireFaqById(pay.getId());
	        	 if (reponsePersModif==null) {
					throw new RuntimeException("pas de pays renvoye");
				}
	        	 QuestionnaireFaq p1= reponsePersModif.getBody();
	        	   System.out.println("les elements a modifier"+p1);
	        	   QuestionnaireFaq p = questionnaireFaqMetier.modifier(pay);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été modifie avec succes", p.getId()));
	   			reponse = new Reponse<QuestionnaireFaq>(0, messages, p);
	           }
				
				} catch (InvalideTogetException e) {

					reponse = new Reponse<QuestionnaireFaq>(1, Static.getErreursForException(e), null);
				}

	// on recupere la personne a modifier
		
		
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/reponseFaq")
	public String modfierUneReponseFaq(@RequestBody ReponseFaq[] modif) throws JsonProcessingException {
		Reponse<ReponseFaq> reponsePersModif = null;
		Reponse<ReponseFaq> reponse = null;

		try {
	           for(ReponseFaq pay:modif) {
	        	 reponsePersModif=  getReponseFaqById(pay.getId());
	        	 if (reponsePersModif==null) {
					throw new RuntimeException("pas de pays renvoye");
				}
	        	 ReponseFaq p1= reponsePersModif.getBody();
	        	   System.out.println("les elements a modifier"+p1);
	        	   ReponseFaq p = reponseFaqMetier.modifier(pay);
	        	   List<String> messages = new ArrayList<>();
	   			messages.add(String.format("%s  à été modifie avec succes", p.getId()));
	   			reponse = new Reponse<ReponseFaq>(0, messages, p);
	           }
				
				} catch (InvalideTogetException e) {

					reponse = new Reponse<ReponseFaq>(1, Static.getErreursForException(e), null);
				}

	// on recupere la personne a modifier
		
		
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/categorieFaq")
	public String findAllCategorieFaq() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<CategorieFaq>> reponse;
		try {
			List<CategorieFaq> mats = categoryFaqMetier.findAll();
			reponse = new Reponse<List<CategorieFaq>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<CategorieFaq>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/categorieFaq/{id}")
	public String chercherCategorieFaqParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<CategorieFaq> reponse = null;

		reponse = getCategorieFaqById(id);

		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/questionnaireFaq")
	public String findAllQuestionnaireFaq() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<QuestionnaireFaq>> reponse;
		try {
			List<QuestionnaireFaq> mats = questionnaireFaqMetier.findAll();
			reponse = new Reponse<List<QuestionnaireFaq>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<QuestionnaireFaq>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/questionnaireFaq/{id}")
	public String chercherquestionnaireFaqParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<QuestionnaireFaq> reponse = null;

		reponse = getQuestionnaireFaqById(id);

		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/reponseFaq")
	public String findAllReponseFaq() throws JsonProcessingException, InvalideTogetException {
		Reponse<List<ReponseFaq>> reponse;
		try {
			List<ReponseFaq> mats = reponseFaqMetier.findAll();
			reponse = new Reponse<List<ReponseFaq>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<ReponseFaq>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	/////////////////////////////////////////////////////////////////////////////////
	// renvoie un block par son identifiant
	///////////////////////////////////////////////////////////////////////////////// identifiant//////////////////////////////////////////
	@GetMapping("/reponseFaq/{id}")
	public String chercherReponseFaqParId(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<ReponseFaq> reponse = null;

		reponse = getReponseFaqById(id);

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/questionFaqParCat/{id}")
	public String findAllQuestionFaqParCat(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<QuestionnaireFaq>> reponse;
		try {
			List<QuestionnaireFaq> mats = questionnaireFaqMetier.findAllQuestionnaireFaqParCat(id);
			reponse = new Reponse<List<QuestionnaireFaq>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<QuestionnaireFaq>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@GetMapping("/reponseFaqParQues/{id}")
	public String findAllreponseFaqParQues(@PathVariable Long id) throws JsonProcessingException, InvalideTogetException {
		Reponse<List<ReponseFaq>> reponse;
		try {
			List<ReponseFaq> mats = reponseFaqMetier.findAllReponseFaqParQuest(id);
			reponse = new Reponse<List<ReponseFaq>>(0, null, mats);
		} catch (Exception e) {
			reponse = new Reponse<List<ReponseFaq>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
