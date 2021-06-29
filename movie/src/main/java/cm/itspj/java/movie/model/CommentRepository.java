package cm.itspj.java.movie.model;

// import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
  // Collection<Comment> findByImpressionId(int id);
}
