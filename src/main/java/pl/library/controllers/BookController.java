package pl.library.controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.websocket.Session;

//import javax.validation.Valid;

//import org.assertj.core.util.DateUtil;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.library.DAO.AuthorDAO;
import pl.library.DAO.BookDAO;
import pl.library.DAO.CheckBookOutDAO;
import pl.library.DAO.PublisherDAO;
import pl.library.DAO.UserDAO;
import pl.library.details.Category;
import pl.library.details.SearchBook;
import pl.library.details.detailCheckedBookOut;
import pl.library.models.Author;
import pl.library.models.Book;
import pl.library.models.CheckBookOut;
import pl.library.models.Publisher;
import pl.library.models.User;
import pl.library.models.users;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	PublisherDAO publisherDAO;
	
	@Autowired
	AuthorDAO authorDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CheckBookOutDAO checkBookOutDAO;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView model = new ModelAndView("/book/add");
		
		Iterable<Publisher> publishers = publisherDAO.findAll();
		Iterable<Author> authors = authorDAO.findAll();
		
		model.addObject("book", new Book());
		model.addObject("selectedPublisher", new Publisher());
		model.addObject("publishers", publishers);
		model.addObject("selectedAuthor", new Author());
		model.addObject("authors", authors);
		return model;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestParam(required = false) Long id, @ModelAttribute("selectedPublisher") @Validated Publisher selectedPublisher,
			BindingResult bindingResultPublisher,
			@ModelAttribute("selectedAuthor") @Validated Author selectedAuthor, 
			BindingResult bindingResultAuthor,
			@Validated Book book,
			BindingResult bindingBook){
		
		if(id != null){
			bookDAO.edit(book.getTitle(), book.getUrl(), book.getIsbn(), book.getYearRealase(), book.getCurrentlyNumberBook(), book.getMaxNumberBook(), selectedAuthor, selectedPublisher, id);
		}else{
			book.setAuthor(selectedAuthor);
			book.setPublisher(selectedPublisher);
			bookDAO.save(book);
		}
		
		System.out.println(selectedAuthor.getIdAuthor());
		System.out.println(selectedPublisher.getIdPublisher());
		
		return "redirect:/";
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Long id){
		ModelAndView model = new ModelAndView("/book/edit");
		
		Book book = bookDAO.findOne(id);
		Iterable<Publisher> publishers = publisherDAO.findAll();
		Iterable<Author> authors = authorDAO.findAll();
		
		model.addObject("book", book);
		model.addObject("selectedPublisher", new Publisher());
		model.addObject("publishers", publishers);
		model.addObject("selectedAuthor", new Author());
		model.addObject("authors", authors);
		
		return model;
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam Long id){
		
		Book book = bookDAO.findOne(id);
		
		if(book.getIdBook() != null){
			bookDAO.delete(book);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/lookingForBook", method = RequestMethod.POST)
	public ModelAndView lookingForBook(@Validated Book book, BindingResult resultBook, 
			@Validated Author author, BindingResult resultAuthor, 
			@Validated Publisher publisher, BindingResult resultPublished){
		
		ModelAndView model = new ModelAndView("/book/list");

		Iterable<Book> books = bookDAO.findAll();
		List<Book> foundBook = new ArrayList<>();
		for (Book book2 : books) {
			foundBook.add(book2);
			
		}
		
		if(book.getTitle().length() == 0 && 
				book.getIsbn().length() == 0 && 
				book.getYearRealase() == null && 
				author.getFirstName().length() == 0 &&
				author.getLastName().length() == 0 &&
				publisher.getName().length() == 0){
			System.out.println("Nic nie podałeś");
		}
		
		if(book.getTitle().length() != 0){
			for (Book book2 : books) {
				if(book2.getTitle().compareTo(book.getTitle()) == 0){
					
				}else{
					foundBook.remove(book2);
				}
			}
		}
		if(book.getIsbn().length() != 0){
			for (Book book2 : books) {
				if(book2.getIsbn().compareTo(book.getIsbn()) == 0){
					
				}else{
					foundBook.remove(book2);
				}
			}
		}
		if(book.getYearRealase() != null){
			for (Book book2 : books) {
				if(book2.getYearRealase().compareTo(book.getYearRealase()) == 0){
					
				}else{
					foundBook.remove(book2);
				}
			}
		}
		if(author.getFirstName().length() != 0){			
			for (Book book2 : books) {
				Author idAuthor = authorDAO.findByFirstName(author.getFirstName());
				if(author.getFirstName().compareTo(idAuthor.getFirstName()) == 0){
					
				}else{
					foundBook.remove(book2);
				}
			}
		}
		if(author.getLastName().length() != 0){			
			for (Book book2 : books) {
				Author idAuthor = authorDAO.findByLastName(author.getLastName());
				if(author.getLastName().compareTo(idAuthor.getLastName()) == 0){
					
				}else{
					foundBook.remove(book2);
				}
			}
		}
		if(publisher.getName().length() != 0){			
			for (Book book2 : books) {
				Publisher idPublisher = publisherDAO.findByName(publisher.getName());
				if(publisher.getName().compareTo(idPublisher.getName()) == 0){
					
				}else{
					foundBook.remove(book2);
				}
			}
		}
		
		
		model.addObject("books", foundBook);
		
		return model;
	}
	
	@RequestMapping("/list")
	public ModelAndView list( ){
		ModelAndView model = new ModelAndView("/book/list");
		
		Iterable<Book> books = bookDAO.findAll();
		
		model.addObject("books", books);
		return model;
	}
	
	@RequestMapping("/modification")
	public ModelAndView listModification(){
		ModelAndView model = new ModelAndView("/book/listModification");
		
		Iterable<Book> books = bookDAO.findAll();
		
		model.addObject("books", books);
		
		return model;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(){
		
		ModelAndView model = new ModelAndView("/search/book");
		
		model.addObject("book", new Book());
		model.addObject("publisher", new Publisher());
		model.addObject("author", new Author());
		
		return model;
	}
	
	@RequestMapping(value = "/result", method = RequestMethod.POST) //dodac test
	public String result(@Validated Category category, BindingResult bindingCategory,
			@Validated SearchBook query, BindingResult bindingName){
		
		Iterable<Book> books = bookDAO.findAll();		
		List<Book> foundBook = new ArrayList<>();
		
		for (Book book : books) {
			foundBook.add(book);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/select")
	public ModelAndView select(@RequestParam Long id, @RequestParam(required=false) Date date, @RequestParam(required=false) String error){
		ModelAndView model = new ModelAndView("/book/select");
		CheckBookOut checkedBookOut = null;
		Book book = bookDAO.findOne(id);
		
		if(SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser"){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 	
			
			String username = auth.getName();
			users user = userDAO.findByUsername(username);			
			checkedBookOut = checkBookOutDAO.returnBook(user.getIdUser(), book.getIdBook());
		}
		
		if(error != null){
			model.addObject("error", error);
		}

		model.addObject("book", book);
		
		return model;
	}
	
	@RequestMapping("/checkBookOut")//nie dziala jeszcze
	public ModelAndView checkBookOut(@RequestParam Long idBook){
		
		ModelAndView model = new ModelAndView("/book/checkBookOut");
				
		return model;
	}
	
	@RequestMapping("/saveCheckBookOut")
	public String saveCheckBookOut(@RequestParam Long idBook){
		
		Book book = bookDAO.findOne(idBook);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 		
		String username = auth.getName();
		users user = userDAO.findByUsername(username);
		
		CheckBookOut IsCheckedOut = checkBookOutDAO.returnBook(user.getIdUser(), idBook);
		Long howManyBooks = checkBookOutDAO.howManyCheckedBookOut(user.getIdUser());
		if(IsCheckedOut == null && howManyBooks < 5){
			CheckBookOut checkOut = new CheckBookOut();
			java.util.Date date = new GregorianCalendar().getTime();
			checkOut.setBook(book);
			checkOut.setUser(user);
			checkOut.setReturnBook(false);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 30);
			date = calendar.getTime();
			
			Date sqlDate = new Date(date.getTime());
			
			checkOut.setDate(sqlDate);
			checkBookOutDAO.save(checkOut);
			System.out.println("Data wypożyczenia: "+sqlDate.toString());
			
			bookDAO.changeNumberBook(book.getCurrentlyNumberBook()-1, book.getIdBook());
			
			sendEmail(user.getUsername(), "Wypożyczyłeś książke: "+book.getTitle(), "Książka "+book.getTitle()+" została pomyślnie wypożyczona dnia: "+sqlDate.toString()+" czas na oddanie książki to 30 dni");
			
			return "redirect:/";
		}else{
			System.out.println("Książka jest już wypożyczona");
			if(IsCheckedOut != null){
				String error = new String("wypozyczone");
				return "redirect:/book/select?id="+idBook+"&error="+error;
			}
			
			if(howManyBooks >= 5){
				String error = new String("limit");
				return "redirect:/book/select?id="+idBook+"&error="+error;
			}
			
			return "redirect:/";
		}
		
	}
	
	@RequestMapping("/checkedBookOut")
	public ModelAndView checkedBookOut(){
		
		ModelAndView model = new ModelAndView("/book/checkedBookOut");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 		
		String username = auth.getName();
		users user = userDAO.findByUsername(username);
		
		List<CheckBookOut> checks = checkBookOutDAO.checkedBooksOut(user.getIdUser());
		
		for (CheckBookOut checkBookOut : checks) {
			System.out.println("ID checked: "+checkBookOut.getIdCheckBookOut());
		}
		
		List<detailCheckedBookOut> books = new ArrayList<>();
		
		for (CheckBookOut checkedBook : checks) {
			
			Book book = bookDAO.findOne(checkedBook.getBook().getIdBook());
			
			detailCheckedBookOut detailBook = new detailCheckedBookOut();
			detailBook.setIdBook(book.getIdBook());
			detailBook.setTitle(book.getTitle());
			detailBook.setDate(checkedBook.getDate());
			detailBook.setReturnBook(checkedBook.isReturnBook());
			detailBook.setEmail(user.getUsername());
			detailBook.setFirstName(user.getFirstName());
			detailBook.setLastName(user.getLastName());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(checkedBook.getDate());
			java.util.Date date = calendar.getTime();
			Date oldDate = new Date(date.getTime());

			Calendar calendarServer = Calendar.getInstance();
			calendarServer.setTime(checkedBook.getDate());
			java.util.Date dateServer = calendarServer.getTime();
			Date newDateServer = new Date(dateServer.getTime());			
			
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(checkedBook.getDate());
			Long day = 0L;
			double money = 0;
			for(int i = 0 ; i < 1000; i ++){
				
				if(newDateServer.compareTo(oldDate) < 0){

					calendar3.add(Calendar.DATE, 1);
					java.util.Date date3 = calendar3.getTime();
					Date date4 = new Date(date3.getTime());
					newDateServer.setTime(date4.getTime());
					day++;

				}

			}
			if(day != 0L){
				money = 5 * day;
			}
			
			detailBook.setMoneyToPay(money);
			books.add(detailBook);
		}
		
		model.addObject("books", books);
		
		return model;
	}

	@RequestMapping("/return")
	public String returnBook(@RequestParam Long idBook){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 		
		String username = auth.getName();
		users user = userDAO.findByUsername(username);
		
		Book book = bookDAO.findOne(idBook);
		
		CheckBookOut checkBookOut = checkBookOutDAO.returnBook(user.getIdUser(), idBook);
		
		if(checkBookOut != null){
			boolean oddaj = true;
			checkBookOutDAO.backBook(oddaj, checkBookOut.getIdCheckBookOut());
			sendEmail(user.getUsername(), "Status oddania książki : "+book.getTitle(), "Książka "+book.getTitle()+" oczekuje na akceptacje przez pracownika biblioteki");
			CheckBookOut c = checkBookOutDAO.findOne(checkBookOut.getIdCheckBookOut());			
		}
		
		return "redirect:/book/checkedBookOut";
	}
	
	@RequestMapping("/renew")
	public String renew(@RequestParam Long idBook){
		CheckBookOut checkBookOut = checkBookOutDAO.findOne(idBook);
		Book book = bookDAO.findOne(idBook);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 		
		String username = auth.getName();
		users user = userDAO.findByUsername(username);
		
		CheckBookOut checkBookOut1 = checkBookOutDAO.returnBook(user.getIdUser(), idBook);
		if(checkBookOut1.isReturnBook() == true){
			sendEmail(user.getUsername(), "Przedłużenie terminu wypożyczonej książki: "+book.getTitle(), "Termin książki "+book.getTitle()+" nie może zostać przedłużony bo oczekuje na oddanie.");
		}else{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(checkBookOut1.getDate());
			calendar.add(Calendar.DATE, 30);
			java.util.Date date = calendar.getTime();
			Date sqlDate = new Date(date.getTime());
			sendEmail(user.getUsername(), "Przedłużenie terminu wypożyczonej książki: "+book.getTitle(), "Termin książki "+book.getTitle()+" została pomyślnie przedłużony o kolejne 30 dni. Na oddanie książki masz czas do "+sqlDate.toString());
			checkBookOutDAO.renewRentalBook(sqlDate, checkBookOut1.getIdCheckBookOut());
		}		
		
		return "redirect:/";
	}
	
	@RequestMapping("/listBackBook")
	public ModelAndView listBackBook(){
		
		ModelAndView model = new ModelAndView("/book/listBackBook");
		
		List<CheckBookOut> checks = checkBookOutDAO.bookToBack();

		List<detailCheckedBookOut> books = new ArrayList<>();
		
		for (CheckBookOut checkedBook : checks) {
			
			Book book = bookDAO.findOne(checkedBook.getBook().getIdBook());
			users user = userDAO.findOne(checkedBook.getUser().getIdUser());
			
			detailCheckedBookOut detailBook = new detailCheckedBookOut();
			detailBook.setIdBook(checkedBook.getIdCheckBookOut());
			detailBook.setTitle(book.getTitle());
			detailBook.setDate(checkedBook.getDate());
			detailBook.setReturnBook(checkedBook.isReturnBook());
			detailBook.setEmail(user.getUsername());
			detailBook.setFirstName(user.getFirstName());
			detailBook.setLastName(user.getLastName());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(checkedBook.getDate());
			java.util.Date date = calendar.getTime();
			Date oldDate = new Date(date.getTime());

			Calendar calendarServer = Calendar.getInstance();
			calendarServer.setTime(checkedBook.getDate());
			java.util.Date dateServer = calendarServer.getTime();
			Date newDateServer = new Date(dateServer.getTime());
			
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(checkedBook.getDate());
			Long day = 0L;
			double money = 0;
			for(int i = 0 ; i < 1000; i ++){
				
				if(newDateServer.compareTo(oldDate) < 0){					
					calendar3.add(Calendar.DATE, 1);
					java.util.Date date3 = calendar3.getTime();
					Date date4 = new Date(date3.getTime());
					newDateServer.setTime(date4.getTime());
					day++;
				}

			}
			if(day != 0L){
				money = 5 * day;
			}
			
			detailBook.setMoneyToPay(money);
			books.add(detailBook);
		}
		
		model.addObject("books", books);
		
		return model;
	}
	
	@RequestMapping("/takeBackBook")
	public String takeBackBook(@RequestParam boolean accept, @RequestParam Long idBook){
		
		CheckBookOut checkBookOut = checkBookOutDAO.findOne(idBook);
		users user = userDAO.findOne(checkBookOut.getUser().getIdUser());
		Book book = bookDAO.findOne(checkBookOut.getBook().getIdBook());
		
		if(accept == true){
						
			bookDAO.changeNumberBook(book.getCurrentlyNumberBook()+1, checkBookOut.getBook().getIdBook());
			checkBookOutDAO.delete(checkBookOut);
			sendEmail(user.getUsername(), "Pomyślne oddanie książki "+book.getTitle(), "Książka "+book.getTitle()+" została zaakceptowana przez pracownika biblioteki");
		}else if(accept == false){
			checkBookOutDAO.backBook(false, checkBookOut.getIdCheckBookOut());
			sendEmail(user.getUsername(), "Książka "+book.getTitle()+"nie została oddana", "Książka "+book.getTitle()+" została z pewnych powodów odrzucona skontaktuj się z pracownikiem biblioteki");
		}
		return "redirect:/book/listBackBook";
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
		}
		
	}

	public ModelAndView rejectGiveBackBook(){
		ModelAndView model = new ModelAndView();
		
		return model;
	}
	
}