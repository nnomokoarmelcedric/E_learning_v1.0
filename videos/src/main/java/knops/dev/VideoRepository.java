package knops.dev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<VideoProjection> findAllBy();

    Optional<Video> findByName(String name);
      Optional<VideoProjection> findProjectionById(long id);
 Optional<Video> findById(long id);


}
