package pl.library.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.library.DAO.UserDAO;
import pl.library.models.ChangePassword;
import pl.library.models.User;
import pl.library.models.users;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@RequestMapping("/change")
	public ModelAndView changePersonalInformation(){
		ModelAndView model = new ModelAndView("/account/change/personalInformation");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		
		users user = userDAO.findByUsername(auth.getName());
		
		System.out.println("imie "+user.getFirstName());
		System.out.println("Nazwisko "+user.getLastName());
		
		users user2 = new users();
		user2.setLastName("nazwiskooo");
		user2.setFirstName("imieee");
		
		model.addObject("user", user);
		return model;
	}
	
	@RequestMapping("/ban")
	public String ban(@RequestParam Long id){
		
		users user = userDAO.findOne(id);
		
		if(user.getIdUser() != null){
			userDAO.banOrUnban(false, id);
			sendEmail(user.getUsername(), "Twoje konto zostało zablokowane", "Twoje konto zostało zablokowane zgłoś się do administratora w celu wyjaśnienia sytuacji");
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/unban")
	public String unban(@RequestParam Long id){
		users user = userDAO.findOne(id);

		if(user.getIdUser() != null){
			userDAO.banOrUnban(true, id);
			sendEmail(user.getUsername(), "Twoje konto zostało odblokowane", "Twoje konto zostało pomyślnie odblokowane");
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam Long id){
		
		users user = userDAO.findOne(id);
		
		if(user.getIdUser() != null){
			userDAO.delete(id);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView model = new ModelAndView("/account/list");
		
		Iterable<users> users = userDAO.findAll();
		
		model.addObject("users", users);
		
		return model;
	}
	
	@RequestMapping("/select")
	public ModelAndView select(@RequestParam Long id){
		ModelAndView model = new ModelAndView("/account/select");
		
		users user = userDAO.findOne(id);
		
		model.addObject("user", user);
		
		return model;
	}
	
	@RequestMapping("/change/password")
	public ModelAndView password(@RequestParam(required = false) String pass){
		ModelAndView model = new ModelAndView("/account/change/password");
		
		if(pass != null){
			model.addObject("error", "nieprawidlowe stare haslo");
		}
		System.out.println(pass);
		
		model.addObject("pass", new ChangePassword());
		
		return model;
	}
	
	@RequestMapping("/savePassword")
	public String savePassword(@Validated ChangePassword pass, BindingResult bindingOldUser){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 		
		String username = auth.getName();
		
		users user = userDAO.findByUsername(username);
		
		System.out.println("id "+ user.getIdUser());
		System.out.println("old pass "+ user.getPassword());
		System.out.println("new pass "+ pass.getNewPassword());
		
		if(user.getIdUser() != null && user.getPassword().compareTo(pass.getOldPassword()) == 0){
//			user.setPassword(pass.getNewPassword());
			userDAO.changePassword(pass.getNewPassword(), user.getIdUser());
			System.out.println("zmienionio haslo");
			return "redirect:/";
		}else{
			System.out.println("nie zmieniono hasla");
			return "redirect:/user/change/password?pass=pass";
		}
		
//		System.out.println("stare haslo: "+pass.getOldPassword());
//		System.out.println("nowe haslo: "+pass.getNewPassword());
//		
//		return "redirect:/";
	}
	
	@RequestMapping("/setting")
	public ModelAndView setting(){
		ModelAndView model = new ModelAndView("/account/setting");
		
		return model;
	}
	
	public void sendEmail(String to, String subject, String message){
		
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(to);
			System.out.println(to);
			mail.setFrom("openclose435@gmail.com");
			mail.setSubject(subject);
			mail.setText(message);
			System.out.println("WLACZ POCZTE");
			javaMailSender.send(mail);
		} catch (MailException e) {
			System.out.println(""+e.getMessage());
			// TODO: handle exception
		}
		
	}
	
}
