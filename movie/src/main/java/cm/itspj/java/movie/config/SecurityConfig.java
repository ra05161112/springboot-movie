package cm.itspj.java.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  private final UserDetailsService userDetailsService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // staticファイルはセキュリティ設定対象外としておく
    web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
    .antMatchers("/").permitAll()
    .antMatchers("/viewer/**", "/movie/**", "new/admin").permitAll()
    .antMatchers("/login","/logout", "/new/admin").permitAll().antMatchers("/h2-console/**").permitAll()
    .anyRequest().authenticated().and()

    .formLogin().
    loginPage("/login").defaultSuccessUrl("/top").and()
    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    .logoutSuccessUrl("/logout");

    http.csrf().disable();
    http.headers().frameOptions().disable();
}
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  // UserDetailsServiceを使用してDBからユーザーを参照する
  auth.userDetailsService(userDetailsService)
      // パスワードエンコーダ指定
      .passwordEncoder(passwordEncoder());
}

}