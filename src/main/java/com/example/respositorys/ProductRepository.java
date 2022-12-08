package com.example.respositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
	Page<Product> findByCategoryId(@RequestParam("id") int id, Pageable pageable);
	Page<Product> findByName(@PathVariable String name, Pageable pageable);

}

