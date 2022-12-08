package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {
@Id
@Column(name = "order_quantity")
private int quantity;

 @ManyToOne
 @JoinColumn(name="order_id",nullable=false)
private  Order order;
 
 @ManyToOne
 @JoinColumn(name="product_id",nullable=false)
private  Product product;
 
			

}
