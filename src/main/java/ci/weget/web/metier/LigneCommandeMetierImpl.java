package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.LigneCommandeRepository;
import ci.weget.web.entites.abonnement.Gallery;
import ci.weget.web.entites.commande.LigneCommande;
import ci.weget.web.entites.commande.Panier;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class LigneCommandeMetierImpl implements ILigneCommandeMetier {
@Autowired
LigneCommandeRepository ligneCommandeRepository;
	@Override
	public LigneCommande creer(LigneCommande entity) throws InvalideTogetException {
		
		return ligneCommandeRepository.save(entity);
	}
	@Override
	public boolean ajoutLigneCommande(Personne personne,Espace espace,double quantite, double montant,boolean abonneSpecial,double nbreJours) {
		
		LigneCommande lc = new LigneCommande();
		
	 if (abonneSpecial==false) {
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$abonneSpecial"+abonneSpecial);

       
      
			
			lc.setPersonne(personne);
			lc.setQuantite(quantite);
			lc.setMontant(montant);
			lc.setAbonneSpecial(false);
			
       // panierRepository.save(p);
	}if(abonneSpecial==true) {
		
		
		lc.setPersonne(personne);
		lc.setQuantite(quantite);
		lc.setMontant(montant);
		lc.setAbonneSpecial(true);
		lc.setNbreJours(nbreJours);
       
	}
	
	ligneCommandeRepository.save(lc);

	return true;
	}
	@Override
	public LigneCommande modifier(LigneCommande modif) throws InvalideTogetException {
		Optional<LigneCommande> ligneCommande = ligneCommandeRepository.findById(modif.getId());

		if (modif != null) {
			
			if (ligneCommande.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return ligneCommandeRepository.save(modif);
	}

	@Override
	public List<LigneCommande> findAll() {
		// TODO Auto-generated method stub
		return ligneCommandeRepository.findAll();
	}

	@Override
	public LigneCommande findById(Long id) {
		// TODO Auto-generated method stub
		return ligneCommandeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		
		 ligneCommandeRepository.deleteById(id);
		 return true;
	}

	@Override
	public boolean supprimer(List<LigneCommande> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public List<LigneCommande> findLigneCommandeParPersonneId(long id) {
		// TODO Auto-generated method stub
		return ligneCommandeRepository.findAllLigneCommandeParPersonneId(id);
	}

	
}
