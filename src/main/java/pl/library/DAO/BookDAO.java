package pl.library.DAO;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.library.models.Author;
import pl.library.models.Book;
import pl.library.models.Publisher;

public interface BookDAO extends CrudRepository<Book, Long>{

	@Modifying
	@Transactional
	@Query("update Book b set b.title = ?1, b.url = ?2, b.isbn = ?3, b.yearRealase = ?4, b.currentlyNumberBook = ?5, b.maxNumberBook = ?6, b.author = ?7, b.publisher = ?8 where b.idBook = ?9")
	public int edit(String title, String url, String isbn, Integer yearRealase, Integer currentlyNumberBook, Integer maxNumberBook, Author author, Publisher publisher, Long idBook);
	
	@Modifying
	@Transactional
	@Query("update Book b set b.currentlyNumberBook = ?1 where b.id = ?2")
	public int changeNumberBook(Integer currentlyNumberBook, Long id);
	
}
