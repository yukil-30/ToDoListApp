package _1.finalproj.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("message", "Welcome to the Home Page!");
        return "home";  // Resolves to /src/main/resources/templates/home.html
    }

}
