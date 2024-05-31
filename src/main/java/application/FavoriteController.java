package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import application.model.User;
import application.service.MovieService;
import application.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String showFavoritePage(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "favorite";
    }

}
