package pl.library.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CheckBookOut {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCheckBookOut;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_User")
	private users user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_book")
	private Book book;
	
	private Date date;
	
	@Column(columnDefinition="tinyint(1) default 0")
	private boolean returnBook;

	public Long getIdCheckBookOut() {
		return idCheckBookOut;
	}

	public void setIdCheckBookOut(Long idCheckBookOut) {
		this.idCheckBookOut = idCheckBookOut;
	}

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isReturnBook() {
		return returnBook;
	}

	public void setReturnBook(boolean returnBook) {
		this.returnBook = returnBook;
	}	
}
