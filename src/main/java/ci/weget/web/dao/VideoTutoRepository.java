package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.VideoTuto;

@Repository
public interface VideoTutoRepository extends JpaRepository<VideoTuto, Long> {

}
