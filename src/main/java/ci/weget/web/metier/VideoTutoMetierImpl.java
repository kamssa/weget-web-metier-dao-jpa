package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.VideoTutoRepository;
import ci.weget.web.entites.VideoTuto;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class VideoTutoMetierImpl implements IVideoTutoMetier{
@Autowired
VideoTutoRepository videoTutoRepository;
	@Override
	public VideoTuto creer(VideoTuto entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return videoTutoRepository.save(entity);
	}

	@Override
	public VideoTuto modifier(VideoTuto entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return videoTutoRepository.save(entity);
	}

	@Override
	public List<VideoTuto> findAll() {
		// TODO Auto-generated method stub
		return videoTutoRepository.findAll();
	}

	@Override
	public VideoTuto findById(Long id) {
		// TODO Auto-generated method stub
		return videoTutoRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		videoTutoRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<VideoTuto> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
