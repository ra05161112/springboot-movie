package cm.itspj.java.movie.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.itspj.java.movie.model.CommentRepository;
import cm.itspj.java.movie.model.Impression;
import cm.itspj.java.movie.model.ImpressionRepository;
import cm.itspj.java.movie.model.Movie;
import cm.itspj.java.movie.model.MovieRepository;
import cm.itspj.java.movie.model.MovieUser;
import cm.itspj.java.movie.model.MovieUserDetailsImpl;
import cm.itspj.java.movie.model.MovieUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
  
  private final ImpressionRepository iRep;
  private final MovieRepository mRep;
  private final MovieUserRepository uRep;
  private final CommentRepository cRep;

  /**adminユーザーのトップページ */
  @GetMapping("/{id}/home")
  public String home(@PathVariable int id, Model model) {
    model.addAttribute("implist", iRep.findByUserId(id));
    List<Movie> movieList = mRep.findAll();
    model.addAttribute("movielist", movieList);
    return "admin/home";
  }

  /**自分の投稿の詳細表示 */
  @GetMapping("/{id}/show")
  public String show(Model model, @PathVariable int id, @AuthenticationPrincipal MovieUserDetailsImpl userDetails) {
    Impression impression = iRep.getById(id);
    model.addAttribute("impression", impression);
   model.addAttribute("movie", iRep.findMovieById(id));
   model.addAttribute("comment", cRep.findByImpressionId(id));
   model.addAttribute("userId", userDetails.getUserId());
    return "admin/show";
  }


  /**編集ページ */
  @GetMapping("/{userid}/{impid}/edit")
  public String edit(@PathVariable int impid,@PathVariable int userid ,Model model, @AuthenticationPrincipal MovieUserDetailsImpl userDetails) {
    model.addAttribute("impress", iRep.findById(impid));
    model.addAttribute("user", uRep.findById(userid));
    model.addAttribute("userId", userDetails.getUserId());
    model.addAttribute("impId", impid);
    return "admin/edit";
  }

  /**編集コマンド */
  @PatchMapping("/{userid}/{impid}/edit")
  public String update(@Validated @ModelAttribute Impression impression, BindingResult result, @PathVariable int impid, @PathVariable int userid){
    if(result.hasErrors()) {
      return "adamin/edit";
    }
    impression.setId(impid);
    iRep.save(impression);
    return "redirect:/admin/"+userid+"/home";
  }
  
  /**新規投稿ページ */
  @GetMapping("/{userid}/new")
  public String newForm(@ModelAttribute Impression impression, @ModelAttribute Movie movie, @PathVariable int userid, Model model, @AuthenticationPrincipal MovieUserDetailsImpl userDetails) {
    model.addAttribute("movie", mRep.findById(userid));
    model.addAttribute("userId", userDetails.getUserId());

    return "admin/new";
  }

  /**新規投稿コマンド */
  @PostMapping("/{userid}/new")
  public String newImpress(@PathVariable int userid, @Validated @ModelAttribute Impression impression, BindingResult result, @ModelAttribute MovieUser user) {
    if (result.hasErrors()) {
      return "admin/new";
    }
    iRep.save(impression);
    return "redirect:/admin/"+userid+"/home";
  }

  /**閲覧者からのコメント削除コマンド */
  @DeleteMapping("/{impid}/{id}/show")
  public String destroy(@PathVariable int impid, @PathVariable int id, Model model) {
    cRep.deleteById(id);
    return "redirect:/admin/"+impid+"/show";
  }

}
