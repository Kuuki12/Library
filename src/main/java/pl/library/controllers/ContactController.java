package pl.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {
	
	@RequestMapping("/contact")
	public ModelAndView contact(){
		ModelAndView model = new ModelAndView("contact");
		return model;
	}
}
