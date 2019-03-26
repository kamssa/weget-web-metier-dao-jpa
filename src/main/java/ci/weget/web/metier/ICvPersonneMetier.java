package ci.weget.web.metier;

import ci.weget.web.entites.abonnement.CvPersonne;

public interface ICvPersonneMetier extends Imetier<CvPersonne, Long>{
CvPersonne	getCvPersonneByIdAbonnemnt(long id);
}
