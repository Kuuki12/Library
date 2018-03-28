package pl.library.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Publisher {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPublisher;
	private String name;
	
	public Publisher() {}
	public Publisher(Long idPublisher, String name) {
		this.idPublisher = idPublisher;
		this.name = name;
	}
	public Long getIdPublisher() {
		return idPublisher;
	}
	public void setIdPublisher(Long idPublisher) {
		this.idPublisher = idPublisher;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
