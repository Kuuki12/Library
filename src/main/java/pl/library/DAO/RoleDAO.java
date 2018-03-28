package pl.library.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.library.models.user_roles;


public interface RoleDAO extends CrudRepository<user_roles, Long>{

	@Query("select r from user_roles r where r.role = ?1")
	user_roles findRole(String nameRole);
	
}
