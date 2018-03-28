package pl.library.DAO;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.library.models.Author;

public interface AuthorDAO extends CrudRepository<Author, Long>{

	@Query("select a from Author a where a.firstName = ?1 and a.lastName = ?2 and a.dateOfBirth = ?3 and a.placeOfBirth = ?4")
	List<Author> findId(String firstName, String lastName, Date dateOfBirth, String placeOfBirth);
	
	@Modifying
	@Transactional
	@Query("update Author a set a.firstName = ?1, a.lastName = ?2, a.dateOfBirth = ?3, a.placeOfBirth = ?4 where a.idAuthor = ?5")
	public int edit(String firstName,
			String lastName,
			Date dateOfBirth,
			String placeOfBirth,
			Long id);
	
	Author findByIdAuthor(Long idAuthor);
	
	Author findByFirstName(String firstName);
	
	Author findByLastName(String lastName);
}
