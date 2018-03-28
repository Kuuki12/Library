package pl.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.library.DAO.AuthorDAO;
import pl.library.DAO.PublisherDAO;
import pl.library.models.Author;
import pl.library.models.Book;
import pl.library.models.Publisher;

@Controller
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	AuthorDAO authorDAO;
	
	@Autowired
	PublisherDAO publisherDAO;
	
	@RequestMapping("/add")
	public ModelAndView addAuthor(){
		ModelAndView model = new ModelAndView("/author/add");
		
		model.addObject("author", new Author());
		return model;
	}
	
	@RequestMapping("/save")
	public String save(@RequestParam(required = false) Long id, @Validated Author autor, 
			BindingResult bindingResult){
		
		if(id != null){
			authorDAO.edit(autor.getFirstName(), 
					autor.getLastName(), 
					autor.getDateOfBirth(), 
					autor.getPlaceOfBirth(), 
					id);
		}else{
			authorDAO.save(autor);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView model = new ModelAndView("/author/list");
		
		Iterable<Author> authors = authorDAO.findAll();
				
		model.addObject("authors", authors);
		
		return model;
	}
	
	@RequestMapping("/modification")
	public ModelAndView listModification(){
		ModelAndView model = new ModelAndView("/author/listModification");
		
		Iterable<Author> authors = authorDAO.findAll();
				
		model.addObject("authors", authors);
		
		return model;
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam Long id){
		
		authorDAO.delete(id);
		
		return "redirect:/";
	}
	
	@RequestMapping("/select")
	public ModelAndView select(@RequestParam Long id){
		ModelAndView model = new ModelAndView("/author/select");
		
		Author author = authorDAO.findOne(id);
		
		model.addObject("author", author);
		
		return model;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Long id){
		ModelAndView model = new ModelAndView("/author/edit");
		
		Author author = authorDAO.findOne(id);
		
		model.addObject("author", author);
		
		return model;
	}
	
}
