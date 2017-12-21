package formController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import form.SignupForm;

public class SignupFormController {

	@RequestMapping
	@Controller
	public class FormController{

		@GetMapping
		public String input(Model model) {
			return "form/signup";
		}

		@PostMapping
		public String result(@ModelAttribute SignupForm form, Model model) {
			return "redirect:/signup";
		}
	}
}
