package pl.library.details;

public class Customer {
	private String firstname;
	private String lastname;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	  
}
