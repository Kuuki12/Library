package pl.library.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUser;
	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private Date dayOfBirth;
	private String numberIndex;
	
	public User() {
	}

	public User(Long id, String firstName, String lastName, String login, String password, Date dayOfBirth,
			String numberIndex) {
		this.idUser = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.dayOfBirth = dayOfBirth;
		this.numberIndex = numberIndex;
	}
	
	public Long getId() {
		return idUser;
	}
	public void setId(Long id) {
		this.idUser = id;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDayOfBirth() {
		return dayOfBirth;
	}
	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}
	public String getNumberIndex() {
		return numberIndex;
	}
	public void setNumberIndex(String numberIndex) {
		this.numberIndex = numberIndex;
	}	
}
