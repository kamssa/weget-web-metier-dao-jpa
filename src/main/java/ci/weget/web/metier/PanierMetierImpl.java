package ci.weget.web.metier;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.PanierRepository;
import ci.weget.web.entites.commande.Panier;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class PanierMetierImpl implements IPanierMetier {

	private Map<Long, Panier> items = new HashMap<>();

	@Autowired
	private PanierRepository panierRepository;

	@Override
	public boolean ajoutLignePanier(Tarif tarif,Espace espace, Personne personne, double quantite, double montant,
			boolean abonneSpecial, double nbreJours) {
		Panier p = new Panier();
		List<Panier> paniers = panierRepository.findPaniersByPersonne(personne.getId());
		for (Panier pa : paniers) {
			// on a le block du panier de la personne
			if (pa.getEspace().getId()==espace.getId()) {
				throw new RuntimeException("Cet espace existe deja dans votre panier");
			}
		}
		if (abonneSpecial == false) {

			p.setTarif(tarif);
			p.setPersonne(personne);
			p.setQuantite(quantite);
			p.setMontant(montant);
			p.setAbonneSpecial(false);

		} else if (abonneSpecial == true) {

			p.setTarif(tarif);
			p.setPersonne(personne);
			p.setQuantite(quantite);
			p.setMontant(montant);
			p.setAbonneSpecial(true);
			p.setNbreJours(nbreJours);
		}

		panierRepository.save(p);

		return true;
	}

	@Override
	public Panier creer(Panier panier) throws InvalideTogetException {
		return panierRepository.save(panier);

	}

	@Override
	public boolean modifLignePanier(Long id, Tarif tarif, Personne personne, double quantite, double montant,
			boolean abonneSpecial, double nbreJours) {

		Panier p = panierRepository.findById(id).get();

		if (abonneSpecial == false) {

			p.setTarif(tarif);
			p.setPersonne(personne);
			p.setQuantite(quantite);
			p.setMontant(montant);
			p.setAbonneSpecial(false);
		}
		if (abonneSpecial == true) {

			p.setTarif(tarif);
			p.setPersonne(personne);
			p.setQuantite(quantite);
			p.setMontant(montant);
			p.setAbonneSpecial(true);
			p.setNbreJours(nbreJours);

		}

		panierRepository.save(p);

		return true;
	}

	@Override
	public List<Panier> findAll() {

		return panierRepository.findAll();
	}

	@Override
	public Panier findById(Long id) {

		return panierRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {

		panierRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Panier> entites) {

		panierRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		return false;
	}

	public Collection<Panier> getItems() {
		return items.values();

	}

	public double getTotal() {
		double total = 0d;
		for (Panier lc : items.values()) {
			total += lc.getTarif().getPrix() * lc.getQuantite();

		}
		return total;

	}

	public int getSize() {
		return items.size();
	}

	@Override
	public List<Panier> getPanierByIdPersonne(long idPersonne) {

		return panierRepository.findPaniersByPersonne(idPersonne);
	}

	@Override
	public Panier modifier(Panier entity) throws InvalideTogetException {

		return null;
	}

}
