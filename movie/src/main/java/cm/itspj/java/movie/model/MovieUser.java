package cm.itspj.java.movie.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class MovieUser {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;

  @NotBlank
  @Size(max = 100)
  public String username;

  /**password   6~255文字 */
  @Size(min = 6, max = 255, message = "パスワードは6～255文字で設定してください。")
  public String password;

  /**権限　RELO_ADMIN  RELO_USER なくてもよくない？*/
  public String role;

  /**管理者フラグ */
  public boolean mastar;

  @OneToMany
  public List<Impression> impressions;
}

