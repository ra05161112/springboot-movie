package cm.itspj.java.movie.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MovieUserDetailsImpl extends User {

  private final MovieUser user;

  public MovieUserDetailsImpl(MovieUser user) {
    super(user.getUsername(), user.getPassword(), getAuthorities(user));
    this.user = user;
  }
  
  private static Collection<? extends GrantedAuthority> getAuthorities(MovieUser user) {
    Set<GrantedAuthority> authSet = new HashSet<>();
    authSet.add(new SimpleGrantedAuthority(user.getRole()));
    return authSet;
  }

  public Integer getUserId() {
    return user.getId();
  }
}

