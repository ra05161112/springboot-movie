package cm.itspj.java.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.itspj.java.movie.model.Comment;
import cm.itspj.java.movie.model.CommentRepository;
import cm.itspj.java.movie.model.Impression;
import cm.itspj.java.movie.model.ImpressionRepository;
// import cm.itspj.java.movie.model.Movie;
// import cm.itspj.java.movie.model.Movie;
import cm.itspj.java.movie.model.MovieRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/viewer")
public class ViewerController {

  private final MovieRepository mRep;
  private final ImpressionRepository iRep;
  private final CommentRepository cRep;

  @GetMapping("/home")
  public String home(Model model) {
    model.addAttribute("implist", iRep.findAll());
    model.addAttribute("movielist", mRep.findAll());
    return "viewer/home";
  }

  @GetMapping("/{id}/show")
  public String show(Model model, @PathVariable int id) {
    // model.addAttribute("impression", iRep.findById(id));
    // model.addAttribute("movie", mRep.findByImpressions(mId));
    Impression impression = iRep.getById(id);
    model.addAttribute("impression", impression);
   model.addAttribute("movie", iRep.findMovieById(id));
   model.addAttribute("comment", iRep.findCommentById(id));
    return "viewer/show";
  }

  @GetMapping("/{impid}/comment")
  public String com(@ModelAttribute Impression impression, @ModelAttribute Comment comment , Model model, @PathVariable int impid) {
  model.addAttribute("impression", iRep.findById(impid));
  model.addAttribute("impId", impid);
    return "viewer/comment";
  }

  @PostMapping("/{impid}/comment")
  public String commentation(@Validated @ModelAttribute Comment comment, BindingResult result, @ModelAttribute Impression impression) {
    if(result.hasErrors()) {
      return "viewer/home";
    }
    cRep.save(comment);
    return "viewer/home";
  }

 
}
