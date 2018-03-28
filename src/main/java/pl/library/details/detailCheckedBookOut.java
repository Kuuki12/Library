package pl.library.details;

import java.sql.Date;

public class detailCheckedBookOut {

	private Long idBook;
	private String title;
	private Date date;
	private boolean returnBook;
	private String email;
	private String firstName;
	private String lastName;
	private double moneyToPay;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public double getMoneyToPay() {
		return moneyToPay;
	}
	public void setMoneyToPay(double moneyToPay) {
		this.moneyToPay = moneyToPay;
	}
	
	
}
