package pl.library.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.library.DAO.RoleDAO;
import pl.library.DAO.UserDAO;
import pl.library.controllers.BookController;
import pl.library.details.Role;
import pl.library.models.user_roles;
import pl.library.models.users;


@Controller
public class LoginController {
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	JavaMailSender javaMailSender;
		
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView error403(){
		
		ModelAndView model = new ModelAndView("/errors/403");
		
		return model;
	}
	
	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public ModelAndView signIn(@RequestParam(value = "error",required = false) String error,
			@RequestParam(value = "logout",required = false) String logout) {
		ModelAndView model = new ModelAndView("/signIn");		
		
		if (error != null) model.addObject("error", "Blad logowania");
				
		if (logout != null) model.addObject("error", "Wylogowano pomyslnie");
		
		return model;
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public ModelAndView signUp(@RequestParam(required = false) String error) {
		ModelAndView model = new ModelAndView("/signUp");
		
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ROLE_USER"));
		roles.add(new Role("ROLE_EMPLOYEE"));
		roles.add(new Role("ROLE_ADMIN"));		
		
		model.addObject("user", new users());
		model.addObject("selectedRole", new Role());
		model.addObject("roles", roles);
		if(error != null) model.addObject("error", "exist");
		
		return model;
	}
	
	@RequestMapping(value = "/saveSignUp", method = RequestMethod.POST)
	public String saveSignUp(@RequestParam(required = false) Long id, 
			@Validated users user, BindingResult bindingResult, 
			@Validated Role role, BindingResult resultRole) {
		
		users userExist = userDAO.findByUsername(user.getUsername());
		
		if(id != null){
			userDAO.updatePersonalInformation(user.getFirstName(), user.getLastName(), id);
			return "redirect:/";
		}else{
			if(role.getNameRole() != null && userExist == null){
				System.out.println("wybrano role "+role.getNameRole());
				userDAO.save(user);
				user_roles roleUser = new user_roles();
				roleUser.setRole(role.getNameRole());
				roleUser.setUser(user);
				roleDAO.save(roleUser);
			}else if(userExist == null){
				System.out.println("null");
				userDAO.save(user);
				user_roles roleUser = new user_roles();
				roleUser.setRole("ROLE_USER");
				roleUser.setUser(user);
				roleDAO.save(roleUser);
			}else{
				return "redirect:/signUp?error=exist";
			}
			sendEmail(user.getUsername(), "Rejestracja", "Pomyślnie zarejestrowałeś się w bibliotece szkolnej (tresc testowa)");
			return "redirect:/signIn";
		}
	}
	
	public String sendEmail(String to, String subject, String message){
		
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(to);
			System.out.println(to);
			mail.setFrom("openclose435@gmail.com");
			mail.setSubject(subject);
			mail.setText(message);
			javaMailSender.send(mail);
		} catch (MailException e) {
		}
		
		return "redirect:/";
	}
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();     

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
	
	@RequestMapping(value = "/addNewUser", method = RequestMethod.GET)
	public ModelAndView addNewUser(){
		
		ModelAndView model = new ModelAndView("/addNewUser");
		
		Iterable<user_roles> roles = roleDAO.findAll();
		
		model.addObject("roles", roles);
		
		return model;
	}	
}
