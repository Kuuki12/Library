package pl.library.DAO;

import java.sql.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.library.models.Publisher;

public interface PublisherDAO extends CrudRepository<Publisher, Long>{

	@Modifying
	@Transactional
	@Query("update Publisher p set p.name = ?1 where p.idPublisher = ?2")
	public int edit(String name,
			Long idPublisher);
	
	Publisher findByName(String name);
}
