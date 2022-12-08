package com.example.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="users")
@Setter
@Getter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="user_id")
	@Column(name="id")
	private Integer id;
	@Column(name="user_firstname")
	private String firstName;
	@Column(name="user_lastname")
	private String lastName;
	@Column(name="user_phonenumber")
	private String phoneNumber;
	@Column(name="user_email")
	private String email;
	@Column(name="user_name")
	private String username;
	@Column(name="user_password")
	//@JsonIgnore
	private String password;
	@Column(name="user_addressline1")
	private String addressline1;
	@Column(name="user_addressline2")
	private String addressline2;
	@Column(name="user_state")
	private String state;
	@Column(name="user_zipcode")
	private String zipcode;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
	private Set<Order>order;
	
	
	public User() {
		}
	

	public User( String firstName, String lastName, String phoneNumber, String email, String username,
			String password, String addressline1, String addressline2, String state, String zipcode) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		this.addressline1 = addressline1;
		this.addressline2 = addressline2;
		this.state = state;
		this.zipcode = zipcode;
		//this.roles=roles;
		
	}
	
	
	
		
}
