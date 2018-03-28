package pl.library.DAO;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.library.models.CheckBookOut;
import pl.library.models.users;

public interface CheckBookOutDAO extends CrudRepository<CheckBookOut, Long> {

	@Query("select cbo from CheckBookOut cbo where cbo.user.idUser = ?1")
	List<CheckBookOut> checkedBooksOut(Long idUser);
	
	@Query("select cbo from CheckBookOut cbo where cbo.user.idUser = ?1 and cbo.book.idBook = ?2")
	CheckBookOut returnBook(Long idUser, Long idBook);
	
	@Modifying
	@Transactional
	@Query("update CheckBookOut cbo set cbo.date = ?1 where cbo.idCheckBookOut = ?2")
	public int renewRentalBook(Date date, Long idCheckBookOut);
	
	@Modifying
	@Transactional
	@Query("update CheckBookOut cbo set cbo.returnBook = ?1 where cbo.idCheckBookOut = ?2")
	public int backBook(boolean returnBook, Long idCheckBookOut);
	
	@Query("select cbo from CheckBookOut cbo where cbo.returnBook = true")
	List<CheckBookOut> bookToBack();
	
	@Query("select cbo from CheckBookOut cbo where cbo.user.idUser = ?1")
	public CheckBookOut checkedBookOut(Long idUser);
	
	@Query("SELECT COUNT(*) FROM CheckBookOut cbo WHERE cbo.user.idUser = ?1")
	Long howManyCheckedBookOut(Long idUser);
}
