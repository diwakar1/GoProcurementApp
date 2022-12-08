package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.model.Product;
import com.example.respositorys.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public Page<Product>findByCategoryId(int id, Pageable pageable){
		Page<Product>product = productRepository.findByCategoryId(id, pageable);
		return product;
	}
	
	//Page<Product> findByCategoryId(@RequestParam("id") int id, Pageable pageable);
	//Page<Product> findByName(@PathVariable String name, Pageable pageable);
	
	public Page <Product>findByName(String name,Pageable pageable){
	 	Page<Product>product = productRepository.findByName(name, pageable);
	 	return product;
	}
	
	
	public void insertProduct(Product product) {
		 productRepository.save(product);
		
	}
}
