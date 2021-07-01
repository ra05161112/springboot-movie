package cm.itspj.java.movie.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
// import javax.persistence.OneToMany;
// import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Impression {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;

  /**評価（5段階）notnull */
  @NotNull
  @Max(5)
  public int evaluation;

  /*adminuserの感想null ok*/
  @Column(name = "impress", nullable = true)
  public String impress;

  @ManyToOne
  public Movie movie;

  /**見た日 notnull*/
  @DateTimeFormat(iso = ISO.DATE)
  @NotNull
  public LocalDate date;

  /**公開か非公開か boolean */
  @NotNull
  public boolean release;

  @ManyToOne
  public MovieUser user;

  @OneToMany
  public List<Comment> comments;
}