package pl.library.models;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idAuthor;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String placeOfBirth;	
	
	public Author() {
	}
	public Author(Long idAuthor, String firstName, String lastName, Date dateOfBirth, String placeOfBirth) {
		this.idAuthor = idAuthor;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
	}
	public Long getIdAuthor() {
		return idAuthor;
	}
	public void setIdAuthor(Long idAuthor) {
		this.idAuthor = idAuthor;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	
	
}
