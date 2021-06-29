package cm.itspj.java.movie.model;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionRepository extends JpaRepository<Impression, Integer> {
  Collection<Impression> findByUserId(int id);
  // Collection<Impression> findByTitle(String title);
  Collection<Impression> findByMovieId(int id);
  // Collection<Impression> 
  Collection<MovieIdOnly> findMovieById(int id);
  Collection<CommentIdOnly> findCommentById(int id);
}
