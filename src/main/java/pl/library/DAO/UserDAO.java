package pl.library.DAO;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.library.models.users;


public interface UserDAO extends CrudRepository<users, Long>{

	users findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query("update users as u set u.firstName = ?1, u.lastName = ?2 where u.idUser = ?3")
	public int updatePersonalInformation(String firstName, String lastName, Long idUser);
	
	@Modifying
	@Transactional
	@Query("update users as u set u.enabled = ?1 where u.idUser = ?2")
	public int banOrUnban(boolean enabled, Long id);
	
	@Modifying
	@Transactional
	@Query("update users as u set u.password = ?1 where u.idUser = ?2")
	public int changePassword(String password, Long id);
}
