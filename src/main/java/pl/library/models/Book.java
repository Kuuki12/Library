package pl.library.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBook;
	@Column(unique = true)
	private String title;
	private String url;
	@Column(unique = true)
	private String isbn;
	private Integer yearRealase;
	private Integer currentlyNumberBook;
	private Integer maxNumberBook;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_author")
	private Author author;
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "id_publisher")
	private Publisher publisher;
	
	public Book() {}
	
	public Book(Long idBook, String title, String url, String isbn, Integer yearRealase,
			Integer currentlyNumberBook, Integer maxNumberBook, Author author, Publisher publisher) {
		this.idBook = idBook;
		this.title = title;
		this.url = url;
		this.isbn = isbn;
		this.yearRealase = yearRealase;
		this.currentlyNumberBook = currentlyNumberBook;
		this.maxNumberBook = maxNumberBook;
		this.author = author;
		this.publisher = publisher;
	}

	public Long getIdBook() {
		return idBook;
	}
	public void setIdBook(Long idBook) {
		this.idBook = idBook;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getYearRealase() {
		return yearRealase;
	}
	public void setYearRealase(Integer yearRealase) {
		this.yearRealase = yearRealase;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Integer getCurrentlyNumberBook() {
		return currentlyNumberBook;
	}

	public void setCurrentlyNumberBook(Integer currentlyNumberBook) {
		this.currentlyNumberBook = currentlyNumberBook;
	}

	public Integer getMaxNumberBook() {
		return maxNumberBook;
	}

	public void setMaxNumberBook(Integer maxNumberBook) {
		this.maxNumberBook = maxNumberBook;
	}		
	
}
