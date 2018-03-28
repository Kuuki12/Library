package pl.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.library.DAO.PublisherDAO;
import pl.library.models.Author;
import pl.library.models.Publisher;

@Controller
@RequestMapping("/publisher")
public class PublisherController {
	
	@Autowired
	PublisherDAO publisherDAO;
	
	@RequestMapping("/add")
	public ModelAndView addPublisher(){
		ModelAndView model = new ModelAndView("/publisher/add");
		
		model.addObject("publisher", new Publisher());
		return model;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam(required = false) Long id, @Validated Publisher publisher, BindingResult bindingResult){
		
		if(id != null){
			publisherDAO.edit(publisher.getName(), id);
		}else{
			publisherDAO.save(publisher);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/list")
	public ModelAndView list(){
		
		ModelAndView model = new ModelAndView("/publisher/list");
		
		Iterable<Publisher> publishers = publisherDAO.findAll();
		
		model.addObject("publishers", publishers);
		return model;
	}
	
	@RequestMapping("/modification")
	public ModelAndView listModification(){
		
		ModelAndView model = new ModelAndView("/publisher/listModification");
		
		Iterable<Publisher> publishers = publisherDAO.findAll();
		
		model.addObject("publishers", publishers);
		return model;
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam Long id){
		
		publisherDAO.delete(id);
		
		return "redirect:/";
	}
	
	@RequestMapping("/select")
	public ModelAndView select(@RequestParam Long id){
		ModelAndView model = new ModelAndView("/publisher/select");
		
		Publisher publisher = publisherDAO.findOne(id);
		
		model.addObject("publisher", publisher);
		
		return model;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Long id){
		ModelAndView model = new ModelAndView("/publisher/edit");
		
		Publisher publisher = publisherDAO.findOne(id);
		
		model.addObject("publisher", publisher);
		
		return model;
	}
	
}
