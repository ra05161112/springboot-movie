package cm.itspj.java.movie.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cm.itspj.java.movie.model.MovieUser;
import cm.itspj.java.movie.model.MovieUserDetailsImpl;
import cm.itspj.java.movie.model.MovieUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
  
  private final MovieUserRepository uRep;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // DB検索
    MovieUser user = uRep.findByUsername(username);
    if (user == null) {
      // 見つからない場合は例外をスロー
      throw new UsernameNotFoundException(username + " not found.");
    }
    // UserDetailsオブジェクトを作成
  

    return new MovieUserDetailsImpl(user);
  }
 
}

