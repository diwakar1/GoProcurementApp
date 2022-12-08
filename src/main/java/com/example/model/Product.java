package com.example.model;

import java.math.BigDecimal;
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
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="product")
public class Product {
	
	public Product() {
		
	}
	
	public Product(String sku, String name, String description, BigDecimal unitPrice, String imageUrl, boolean active,
			int unitsInStock, Date dateCreated,ProductCategory category) {
		super();
		this.sku = sku;
		this.name = name;
		this.description = description;
		this.unitPrice = unitPrice;
		this.imageUrl = imageUrl;
		this.active = active;
		this.unitsInStock = unitsInStock;
		this.dateCreated = dateCreated;
		this.category = category;
	}


	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Integer id;
   
    @Column(name = "sku")
    private String sku;
    
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "image_url")
    private String imageUrl;
  
	@Column(name = "active")
    private boolean active;

    @Column(name = "units_in_stock")
    private int unitsInStock;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;
    
    @ManyToOne
    @JoinColumn(name="category_id",nullable=false)
    private ProductCategory category;
    
    @JsonIgnore
	@OneToMany(cascade=CascadeType.ALL,mappedBy="product")
	private  Set<OrderItem>orderItem;
	

}
