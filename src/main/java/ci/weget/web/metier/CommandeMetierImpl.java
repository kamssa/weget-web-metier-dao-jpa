package ci.weget.web.metier;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.CommandeRepository;
import ci.weget.web.entites.commande.Commande;
import ci.weget.web.entites.ecole.Chiffre;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modele.metier.ICreeAbonne;

@Service
public class CommandeMetierImpl implements ICommandeMetier {
	@Autowired
	CommandeRepository commandeRepository;
	@Autowired
	ICreeAbonne creerAbonne;
	

	@Override
	public Commande creerCommande(Personne pe, double montant,String code) throws InvalideTogetException {
        Commande commande= new Commande();
        
		
        commande.setPersonne(pe);
        commande.setMontant(montant);
        commande.setNumero(code);
		
		
	   return commandeRepository.save(commande);
	}

	@Override
	public Commande modifier(Commande modif) throws InvalideTogetException {

		Optional<Commande> commande = commandeRepository.findById(modif.getId());

		if (commande.isPresent()) {
			
			if (commande.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return commandeRepository.save(modif);
	}

	@Override
	public List<Commande> findAll() {

		return commandeRepository.findAll();
	}

	@Override
	public Commande findById(Long id) {
        Commande commande=null;
		commande = commandeRepository.findById(id).get();
		 return commande;
	}

	@Override
	public boolean supprimer(Long id) {
		commandeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Commande> entites) {
		commandeRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Commande creer(Commande entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Commande getByIdPersonne(Long id) {
		
		return commandeRepository.findByIdPersonne(id);
	}
	@Override
	public boolean creerAbonne(Personne personne) throws InvalideTogetException {
		try {
			Commande c= commandeRepository.findByIdPersonne(personne.getId());
			if(c.isPaye()==true) {
				creerAbonne.creerUnAbonne(personne);
			}else {
				throw new RuntimeException("paiement non effectue");
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

}
