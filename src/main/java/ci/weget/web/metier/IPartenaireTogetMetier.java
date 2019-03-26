package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.Partenaire;

public interface IPartenaireMetier extends Imetier<Partenaire, Long>{
public List<Partenaire>	getPartenaireByIdSousBlock(Long id);
}
