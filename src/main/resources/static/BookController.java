package pl.library.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.library.DAO.AuthorDAO;
import pl.library.DAO.BookDAO;
import pl.library.DAO.PublisherDAO;
import pl.library.models.Author;
import pl.library.models.Book;
import pl.library.models.Publisher;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	PublisherDAO publisherDAO;
	
	@Autowired
	AuthorDAO authorDAO;
	
	@RequestMapping("/addBook")
	public ModelAndView addBook(){
		ModelAndView model = new ModelAndView("addBook");
		
		Iterable<Publisher> publishers = publisherDAO.findAll();
		Iterable<Author> authors = authorDAO.findAll();
		
		model.addObject("book", new Book());
		model.addObject("selectedPublisher", new Publisher());
		model.addObject("publishers", publishers);
		model.addObject("selectedAuthor", new Author());
		model.addObject("authors", authors);
		return model;
	}
	
	@RequestMapping("/saveBook")
	public String saveBook(@Validated Book book,
			@Valid Publisher selectedPublisher, 
			@Valid Author selectedAuthor, 
			BindingResult bindingResult){
		
		System.out.println(selectedPublisher.getId());
		System.out.println(selectedAuthor.getId());
		
		Publisher foundPublisher = publisherDAO.findOne(selectedPublisher.getId());
		Author foundAuthor = authorDAO.findOne(selectedAuthor.getId());
		
//		
//		
//		System.out.println(foundPublisher.getName());
//		System.out.println(foundAuthor.getFirstName());
//		bookDAO.save(book);
		
		return "redirect:/";
	}
	
}
