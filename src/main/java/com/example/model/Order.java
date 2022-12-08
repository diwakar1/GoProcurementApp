package com.example.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
	
	public Order() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	Integer id;
	
	
	@Column(name = "order_date")
    @CreationTimestamp
    private Date orderdate;

	
	@ManyToOne()
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="order")
	private Set<OrderItem>orderItem;
	
	
	

}
