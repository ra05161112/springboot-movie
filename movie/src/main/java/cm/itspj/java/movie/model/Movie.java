package cm.itspj.java.movie.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movie {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;

  @NotBlank
  public String title;

  /**監督*/
  @NotBlank
  public String director;

  @OneToMany
  public List<Impression> impressions;
}
