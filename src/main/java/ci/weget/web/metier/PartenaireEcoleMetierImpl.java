package ci.weget.web.metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ci.weget.web.dao.PartenaireEcoleRepository;
import ci.weget.web.entites.ecole.PartenaireEcole;
import ci.weget.web.exception.InvalideTogetException;
import ci.weget.web.modeles.PathImage;

@Service
public class PartenaireEcoleMetierImpl implements IPartenaireEcoleMetier {
	@Autowired
	private PartenaireEcoleRepository partenaireEcoleRepository;
////////////chemin ou sera sauvegarder les photos
//////////// ////////////////////////////////////////
@Value("${dir.images}")
private String togetImage;
	@Override
	public PartenaireEcole creer(PartenaireEcole entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return partenaireEcoleRepository.save(entity);
	}

	@Override
	public PartenaireEcole modifier(PartenaireEcole entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return partenaireEcoleRepository.save(entity);
	}

	@Override
	public List<PartenaireEcole> findAll() {
		// TODO Auto-generated method stub
		return partenaireEcoleRepository.findAll();
	}

	@Override
	public PartenaireEcole findById(Long id) {
		// TODO Auto-generated method stub
		return partenaireEcoleRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<PartenaireEcole> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PartenaireEcole> getPartenaireByIdEcole(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartenaireEcole createImagePartenaireEcole(MultipartFile file, Long id) throws IllegalStateException, IOException {
		// recuperer le libelle à partir du nom de la photo
				PartenaireEcole p = null;
				String libelle = file.getOriginalFilename();
				String libelleSEspace = libelle.trim();
				String libelleS = libelleSEspace.replaceAll("\\s", "");
				p = partenaireEcoleRepository.findById(id).get();
				System.out.println(p);

				String path = PathImage.getLienRetourPartenaireEcole() + p.getVersion() + "/" + id+"/"+libelleS;
				System.out.println(path);

				String dossier = togetImage + "/" + PathImage.getLienImagePartenaireEcole() + "/"+id+"/";
				File rep = new File(dossier);

				if (!file.isEmpty()) {
					if (!rep.exists() && !rep.isDirectory()) {
						rep.mkdir();
					}
				}
				try {
					// enregistrer le chemin dans la photo
					p.setPathLogo(path);
					System.out.println(path);
					file.transferTo(new File(dossier + libelleS));
		            partenaireEcoleRepository.save(p);
				} catch (Exception e) {

					throw new RuntimeException("Operation reussi");
				}
				return p;
	}

	@Override
	public byte[] getImagePartenaireEcole(Long version, Long id,String libelle) throws FileNotFoundException, IOException {
		String dossier = togetImage + "/" + PathImage.getLienImagePartenaireEcole() + "/"+id+"/";
		File f = new File(dossier + libelle);
		byte[] img = IOUtils.toByteArray(new FileInputStream(f));

		return img;
	}

}
