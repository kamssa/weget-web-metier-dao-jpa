package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.abonnement.Experiences;

public interface IExperiencesMetier extends Imetier<Experiences, Long>{
public List<Experiences> findAllExperienceParCv(Long id);
}
