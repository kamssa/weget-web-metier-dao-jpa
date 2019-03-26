package ci.weget.web.modele.metier;

import java.text.ParseException;

import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface ICreeReAbonne {
	public void creerReAbonnement(Long idAbonnement) throws ParseException, InvalideTogetException;
}
