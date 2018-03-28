package pl.library.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pl.library.DAO.AuthorDAO;
import pl.library.DAO.BookDAO;
import pl.library.DAO.PublisherDAO;
import pl.library.DAO.RoleDAO;
import pl.library.DAO.UserDAO;
import pl.library.details.Customer;
import pl.library.models.Author;
import pl.library.models.Book;
import pl.library.models.ChangePassword;
import pl.library.models.Publisher;
import pl.library.models.user_roles;
import pl.library.models.users;

@Controller
public class HomeController {
	
	@Autowired
	AuthorDAO authorDAO;
	
	@Autowired
	PublisherDAO publisherDAO;
	
	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	@RequestMapping("/")
	public ModelAndView home(){
		
		ModelAndView model = new ModelAndView("home");
		
		return model;
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
	
	@RequestMapping("/testBooks")
	public String test(){
		
		Author author1 = new Author(1L, "imie1", "nazwisko1", new Date(2000-10-2), "miejsce1");
		Author author2 = new Author(2L, "imie2", "nazwisko2", new Date(2000-10-3), "miejsce2");
		
		authorDAO.save(author1);
		authorDAO.save(author2);
		
		Publisher publisher1 = new Publisher(1L, "Wydawnictwo1");
		Publisher publisher2 = new Publisher(2L, "Wydawnictwo2");
		
		publisherDAO.save(publisher1);
		publisherDAO.save(publisher2);
		
		Book book1 = new Book(1L, "Tytul1", "URL1", "ISBN1", 2010, 2, 2, author1, publisher1);
		Book book2 = new Book(2L, "Tytul2", "URL2", "ISBN2", 2010, 10, 10, author2, publisher2);
		
		bookDAO.save(book1);
		bookDAO.save(book2);
		
		return "redirect:/";
	}
	
	@RequestMapping("/testUsers")
	public String testUsers(){
		
		
		
//		users user1 = new users(1L, "user1", "user1", true, "user1", "user1");
//		users user2 = new users(2L, "user2", "user2", true, "user2", "user2");
		users admin = new users(3L, "jedruszczak.jaroslaw@gmail.com", "admin", true, "admin", "admin");
//		users emp = new users(4L, "emp", "emp", true, "emp", "emp");
		
//		userDAO.save(user1);
//		userDAO.save(user2);
		userDAO.save(admin);
//		userDAO.save(emp);
		
		user_roles role1 = new user_roles();
		user_roles role2 = new user_roles();
		user_roles role3 = new user_roles();
		user_roles role4 = new user_roles();
		
//		role1.setRole("ROLE_USER");
//		role1.setUser(user1);
//		roleDAO.save(role1);
//		
//		role2.setRole("ROLE_USER");
//		role2.setUser(user2);
//		roleDAO.save(role2);
		
		role3.setRole("ROLE_ADMIN");
		role3.setUser(admin);
		roleDAO.save(role3);
		
//		role4.setRole("ROLE_EMPLOYEE");
//		role4.setUser(emp);
//		roleDAO.save(role4);
		
		
		
		
		return "redirect:/";
	}
	
	
	@RequestMapping("/listBook")
	public ModelAndView listBook(){
		ModelAndView model = new ModelAndView("/listAjax");
		
		Iterable<Book> books = bookDAO.findAll();
		
		model.addObject("books", books);
		model.addObject("bookSearch", new Book());
		return model;
	}
	
	@RequestMapping("/selectBook")
	public String selectBook(@RequestParam Long id){
		
		System.out.println("selected: "+id);
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/searchBook", method = RequestMethod.POST, headers = {"Content-type = application/json"})
	@ResponseBody
	public String searchBook(@ModelAttribute Customer customer){
		
		System.out.println("search");
		
		return null;
	}
		

}
