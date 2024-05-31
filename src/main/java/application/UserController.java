package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.Dto.UserDto;
import application.model.User;
import application.service.EmailService;
import application.service.UserService;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/index")
    public String login(Model model) {
        model.addAttribute("user", new UserDto());
        return "index";
    }

    @PostMapping("/index")
    public String loginSubmit(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "index";
        }

        User user = userService.findByUsername(userDto.getUsername());
        if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            model.addAttribute("errorMessage", "Invalid username or password.");
            model.addAttribute("user", userDto);
            return "index";
        }

        return "redirect:/home";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        User existingUser = userService.findByUsername(userDto.getUsername());
        if (existingUser != null) {
            model.addAttribute("usernameError", "Username already exists.");
            model.addAttribute("user", userDto);
            return "register";
        }

        try {
            userService.save(userDto);
            String emailSubject = "Welcome to Our Saranya Movies";
            String emailBody = "Thank you for registering " + userDto.getUsername() + "!";
            emailService.sendEmail(userDto.getEmail(), emailSubject, emailBody);
            System.out.println("Welcome email sent successfully to: " + userDto.getEmail());
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Welcome email sent.");
            System.out.println("User registered successfully!");
            return "redirect:/index";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to send welcome email: " + e.getMessage());
            System.out.println("Email ID not found: " + userDto.getEmail());
            model.addAttribute("emailError", "Email ID not found. Please provide a valid email ID.");
            return "register";
        }
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }
}
