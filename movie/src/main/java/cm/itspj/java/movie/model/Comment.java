package cm.itspj.java.movie.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;

  /**閲覧者からのコメントnotblank*/
  @NotBlank
  public String comments;

  /**コメントした日時 notnull */
  @DateTimeFormat(iso = ISO.DATE)
  @NotNull(message = "日付を入力してください。")
  public LocalDate date;

  @ManyToOne
  @NotNull
  public Impression impression;
}
