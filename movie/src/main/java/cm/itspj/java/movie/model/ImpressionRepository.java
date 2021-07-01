package cm.itspj.java.movie.model;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionRepository extends JpaRepository<Impression, Integer> {
  
  Collection<Impression> findByUserId(int id);
  Collection<Impression> findByMovieId(int id);
  Collection<MovieIdOnly> findMovieById(int id);
  Collection<CommentIdOnly> findCommentsById(int id);
  
  /**impressionを日付で並び替えた最新から件 */
  Collection<Impression> findTop5ByOrderByDateDesc();
  /**impressionの日付古い順並び替え */
  Collection<Impression> findAllByOrderByDate();
  /**impressionの評価高い順並び替え */
  Collection<Impression> findAllByOrderByEvaluationDesc();
  /**映画のタイトル順並び替え */
  Collection<Impression> findAllByOrderByMovieTitle();
  
}
