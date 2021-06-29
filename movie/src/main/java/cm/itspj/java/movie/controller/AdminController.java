package cm.itspj.java.movie.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.itspj.java.movie.model.Impression;
// import cm.itspj.java.movie.model.Impression;
import cm.itspj.java.movie.model.ImpressionRepository;
import cm.itspj.java.movie.model.Movie;
import cm.itspj.java.movie.model.MovieRepository;
import cm.itspj.java.movie.model.MovieUser;
import cm.itspj.java.movie.model.MovieUserRepository;
// import cm.itspj.java.movie.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
  
  private final ImpressionRepository iRep;
  private final MovieRepository mRep;
  private final MovieUserRepository uRep;

  @GetMapping("/{id}/home")
  public String home(@PathVariable int id, Model model) {
    // List<Impression> impList = iRep.findByUserId(id);
    model.addAttribute("implist", iRep.findByUserId(id));
    List<Movie> movieList = mRep.findAll();
    model.addAttribute("movielist", movieList);
    return "admin/home";
  }

  @GetMapping("/{id}/show")
  public String show(Model model, @PathVariable int id) {
    Impression impression = iRep.getById(id);
    model.addAttribute("impression", impression);
   model.addAttribute("movie", iRep.findMovieById(id));
    return "admin/show";
  }

  @GetMapping("/{id}/new")
  public String newForm(@ModelAttribute Impression impression, @ModelAttribute Movie movie, @PathVariable int id, Model model) {
    model.addAttribute("movie", mRep.findById(id));
    return "admin/new";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable int id, Model model) {
    model.addAttribute("impression", iRep.findById(id));
    return "admin/edit";
  }

  @PatchMapping("/{id}/edit")
  public String update(@Validated @ModelAttribute Impression impression, BindingResult result, @PathVariable int id){
    if (result.hasErrors()) {
      return "admin/edit";
    }
    impression.setId(id);
    iRep.save(impression);
    return "redirect:/admin/"+id+"/show";
  }


  @PostMapping("/{id}/new")
  public String newImpress(@PathVariable int id, @Validated @ModelAttribute Impression impression, BindingResult result, @ModelAttribute MovieUser user) {
    if (result.hasErrors()) {
      return "admin/new";
    }
    iRep.save(impression);
    return "redirect:/admin/"+id+"/home";
  }

  
  @GetMapping("/damy")
  public String user(@AuthenticationPrincipal MovieUser movieUser, Model model) {
    model.addAttribute("user", movieUser);
    return "admin/damy";
  }

}
