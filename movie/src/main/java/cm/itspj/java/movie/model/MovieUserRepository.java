package cm.itspj.java.movie.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieUserRepository extends JpaRepository<MovieUser, Integer> {
  MovieUser findByUsername(String username);

  // MovieUser getOne(String username);

}