package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.ecole.Chiffre;

public interface IChiffreMetier extends Imetier<Chiffre, Long>{
public List<Chiffre> getChifreByIdSousBlock(Long id);
}
