package pl.library.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class user_roles {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUserRole;
	private String role;	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", referencedColumnName="username")
	private users user;	
	
	public user_roles() {}
	
	public user_roles(Long idUserRole, String role, users user) {
		this.idUserRole = idUserRole;
		this.role = role;
		this.user = user;
	}
	public Long getIdUserRole() {
		return idUserRole;
	}
	public void setIdUserRole(Long idUserRole) {
		this.idUserRole = idUserRole;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public users getUser() {
		return user;
	}
	public void setUser(users user) {
		this.user = user;
	}	
}
