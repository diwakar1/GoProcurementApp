package com.example.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;
import com.example.security.services.UserDetailsImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.Product;
import com.example.model.User;
import com.example.respositorys.ProductCategoryRepository;
import com.example.respositorys.ProductRepository;
import com.example.respositorys.UserRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.services.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
   private Logger logger = Logger.getLogger(TestController.class);

	@Autowired
	ProductRepository productRepository;
	
	@Autowired 
	ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	ProductService productService;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	//@PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
	@PreAuthorize("hasAuthority('USER')")

	public String userAccess() {
		return "User Content.";
	}
	
	
	
	
	
	@GetMapping("/findByCategoryId")
	public List<Product> findById(@RequestParam("categoryId") int categoryId) {
		Pageable pageable = PageRequest.of(0, 20);
		Page<Product> oprod = productService.findByCategoryId(categoryId, pageable);
		  if(oprod.hasContent()) {
		        logger.info("The product is received by find by category ID");

	            return oprod.getContent();

	        } else {
		        logger.error("This is an error to find product with find By by category Id");

	            return new ArrayList<Product>();
	        }
		  
	}
	
	@GetMapping("/findByName/{name}")
	public List<Product>findByName(@PathVariable String name){
		Pageable pageable = PageRequest.of(0, 20);
		Page<Product> oprod = productService.findByName(name, pageable);
		 if(oprod.hasContent()) {
			 logger.info("The content is received with find By Name");
	            return oprod.getContent();
	        } else {
		        logger.error("This is an error in find by name ");

	            return new ArrayList<Product>();
	            
	        }

	}
	

	
	
	@GetMapping("/findById")
	//@PreAuthorize("hasAuthority('USER')")
	public Product findByProductId(@RequestParam("id") int id) {
		Product prod = null;
		Optional<Product> oprod = productRepository.findById(id);
		if (oprod.isPresent()) {
			prod = oprod.get();
		} else {
			prod = new Product();
		}
		
		return prod;
	}
	
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('ADMIN')")
	public String insertProduct(@RequestBody Product product) {
		productService.insertProduct(product);
		return "products inserted successfully";

	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable int id) {
        productRepository.deleteById(id); 
        return "products deleted successfully";
    }
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Product product, @PathVariable int id) {
        try {
           // Product existProduct = productRepository.findById(id).get();
            product.setId(id);            
            productRepository.save(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	

	@GetMapping("/mod")
	@PreAuthorize("hasAuthority('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@GetMapping("/getUsers")
	//@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<User>getuser( @RequestParam("id") int id) {
		User user= null;
		Optional<User> optionalUser = userRepository.findById(id);
	   if(optionalUser.isPresent()) {
		   user = optionalUser.get();
		   logger.info(user.getId());
	   }else {
		   user= new User();
	   }
	   return ResponseEntity.ok(user);

	}
}
