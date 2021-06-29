package cm.itspj.java.movie.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cm.itspj.java.movie.model.ImpressionRepository;
import cm.itspj.java.movie.model.MovieRepository;
import cm.itspj.java.movie.model.MovieUser;
import cm.itspj.java.movie.model.MovieUserDetailsImpl;
import cm.itspj.java.movie.model.MovieUserRepository;
import cm.itspj.java.movie.model.Role;
import lombok.RequiredArgsConstructor;
// import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class HomeController {

  private final BCryptPasswordEncoder passwordEncoder;

  private final ImpressionRepository iRep;
  private final MovieRepository mRep;
  private final MovieUserRepository uRep;


  @GetMapping("/")
  public String logoutPage() {
    return "common/logout";
  }

  @GetMapping("/top")
    public String index(@AuthenticationPrincipal MovieUserDetailsImpl userDetails, Model model) {
      System.out.println("user_id " + userDetails.getUserId());
      model.addAttribute("userId", userDetails.getUserId());
    return "common/index";
  }

  @GetMapping("/login")
  public String login() {
    return "common/login";
  }

  @GetMapping("/movie/{id}/list")
  public String movie(@PathVariable int id, Model model) {
    model.addAttribute("movie", mRep.findById(id));
    model.addAttribute("impression", iRep.findByMovieId(id));
    return "common/list";
  }

  @GetMapping("/new/admin")
  public String newUser(@ModelAttribute("user") MovieUser user) {
    return "common/new";
  }

  @PostMapping("/new/admin")
  public String createUser(@Validated @ModelAttribute("user") MovieUser user, BindingResult result) {
    if (result.hasErrors()) {
      return "common/new";
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    user.setRole(user.isMastar() ? Role.ROLE_ADMIN.name() : Role.ROLE_ADMIN.name());

    uRep.save(user);
    return "redirect:/login?new/admin";
  }


}
