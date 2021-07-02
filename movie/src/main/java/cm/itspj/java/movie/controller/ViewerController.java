package cm.itspj.java.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.itspj.java.movie.model.Comment;
import cm.itspj.java.movie.model.CommentRepository;
import cm.itspj.java.movie.model.Impression;
import cm.itspj.java.movie.model.ImpressionRepository;
import cm.itspj.java.movie.model.MovieRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/viewer")
public class ViewerController {

  private final MovieRepository mRep;
  private final ImpressionRepository iRep;
  private final CommentRepository cRep;

  /**閲覧者トップページ */
  @GetMapping("/home")
  public String home(Model model) {
    model.addAttribute("implist", iRep.findTop5ByOrderByDateDesc());
    model.addAttribute("movielist", mRep.findAll());
    return "viewer/home";
  }

  /**投稿一覧 */
  @GetMapping("/all")
  public String all(Model model){
    model.addAttribute("implist", iRep.findAll());
    return "viewer/all";
  }

  /**タイトル順並び替え */
  @GetMapping("/all/movie")
  public String movieby(Model model){
    model.addAttribute("implist", iRep.findAllByOrderByMovieTitle());
    return "viewer/movie";
  }

  /**投稿日時順 */
  @GetMapping("/all/date")
  public String dateby(Model model){
    model.addAttribute("implist", iRep.findAllByOrderByDate());
    return "viewer/date";
  }

  /**評価高い順 */
  @GetMapping("/all/evalu")
  public String avluby(Model model){
    model.addAttribute("implist", iRep.findAllByOrderByEvaluationDesc());
    return "viewer/evalu";
  }

  /**投稿詳細表示 */
  @GetMapping("/{id}/show")
  public String show(Model model, @PathVariable int id) {
    Impression impression = iRep.getById(id);
    model.addAttribute("impression", impression);
   model.addAttribute("movie", iRep.findMovieById(id));
   model.addAttribute("comment", iRep.findCommentsById(id));
    return "viewer/show";
  }

  /**コメント投稿ページ */
  @GetMapping("/{impid}/comment")
  public String com(@ModelAttribute Comment comment , Model model, @PathVariable int impid) {
  model.addAttribute("impId", impid);
    return "viewer/comment";
  }

  /**コメント投稿コマンド */
  @PostMapping("/{impid}/comment")
  public String commentation(@Validated @ModelAttribute Comment comments, BindingResult result, @PathVariable int impid, Model model) {
    if(result.hasErrors()) {
      model.addAttribute("impid", impid);
      return "viewer/comment";
    }
    cRep.save(comments);
    return "redirect:/viewer/home";
  }

 
}
