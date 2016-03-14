package com.wom.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tbllogin")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoginUser implements Serializable {

	public LoginUser(){}
	public LoginUser(String usercode, String password, String emailaddress){
		this.userCode = usercode;
		this.password = password;
		this.emailAddress = emailaddress;
		this.active = "YES";
	}
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="idgen", strategy="increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "UserCode")
	private String userCode;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "EmailAddress")
	private String emailAddress;

	@Column(name = "Active")
	private String active;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	/**
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "loginUser")
	@Cascade(CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	
	public Customer membership;

	public Customer getMembership() {
		return membership;
	}

	public void setMembership(Customer membership) {
		this.membership = membership;
	}
	**/
	
}
