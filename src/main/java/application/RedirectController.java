package application;

import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String redirect(Authentication authentication) {
        if (authentication != null) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            System.out.println("User Roles: " + roles);
            
            System.out.println("Redirecting to /login");
            return "redirect:/home";
        } else {
            // Handle the case where authentication is null
            System.out.println("Authentication is null. Redirecting to /error or home.");
            return "redirect:/error";  // Or redirect to any other appropriate page
        }
    }
}
