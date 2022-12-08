package com.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.model.Product;
import com.example.model.User;
import com.example.respositorys.ProductRepository;
import com.example.respositorys.UserRepository;
import com.example.security.services.UserDetailsServiceImpl;
import com.example.services.ProductService;


@RunWith(SpringRunner.class)
@SpringBootTest
class GreatOutlookShoppingAppApplicationTests {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	UserRepository ur;

	@Autowired 
	ProductRepository productRepository;
	
	@Autowired
	ProductService ps;
	
	
	private MockMvc mockMvc;
	
	 UserDetailsServiceImpl test1 = new  UserDetailsServiceImpl();
	 
	 
	 @Before
	 public void setup() {
		 mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
	 }
	 
	 
	
	@Test
	void contextLoads() {
		

	}
	
	

	

	
//	@Test
//	public void testUser() throws Exception{
//		mockMvc.perform(get("/api/test/findById?id=1")).andExpect(status().isOk())
//		  //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		  .andExpect(content().contentType("application/json"))
//		   .andExpect(jsonPath("$.id").value(1))
//		   .andExpect(jsonPath("$.sku").value("BOOK-TECH-1000"))
//		   .andExpect(jsonPath("$.name").value("Crash Course in Python"))
//		   .andExpect(jsonPath("$.description").value("Learn Python at your own pace. The author explains how the technology works in easy-to-understand language. This book includes working examples that you can apply to your own projects. Purchase the book and get started today!\""))
//		   .andExpect(jsonPath("$.unitPrice").value(14.99))
//		   .andExpect(jsonPath("$.imgUrl").value("assets/images/products/books/book-luv2code-1000.png"))
//		   .andExpect(jsonPath("$.active").value(true))
//		   .andExpect(jsonPath("$.unitsInStock").value(100))
//		   .andExpect(jsonPath("$.dateCreated").value("2021-06-06T03:13:28.000+00:00"))
//		   .andExpect(jsonPath("$.lastUpdated").value(null))
//		   .andExpect(jsonPath("$.category.id").value(1))
//		   .andExpect(jsonPath("$.category.categoryname").value("Books"));
//		
//	}
//	
	
	
	@Test
    public void testfindByCategoryId() throws Exception{ 
		
		Pageable pageable = PageRequest.of(0, 20);

        Page<Product> ps = productRepository.findByCategoryId(0, pageable);   
        if(ps.hasContent()) {
            assertEquals("Crash Course in Python ",ps.get().map(st->st.getName()));           
        }
    }   
	
	
	
	@Test
    public void testfindByIdI() throws Exception{ 

        Optional<Product> ps = productRepository.findById(100);   
        if(ps.isPresent()) {
            //assertEquals("Crash Course in Python",ps.get().getName());           
            assertEquals("Luggage Tag - Countryside",ps.get().getName());           

        }
    }   
	
	
	@Test
    public void testfindByIdII() throws Exception{ 

        Optional<Product> ps = productRepository.findById(1);   
        if(ps.isPresent()) {
            assertEquals("BOOK-TECH-1000",ps.get().getSku());           

        }
    }   
	
	

	@Test   
	public void testUsernameExists() throws Exception{ 

      assertEquals(ur.existsByUsername("sudha"),true);
    }   
	
	
	@Test
	public void saveuserTest() {
		User user = new User("sudha","sharma","2345","sudha@em","sudha","password","baltimore","dc","maryland","23334");
		User user1 = ur.save(user);
		assertEquals(user1.getFirstName(),"sudha");
	}
	
//	@Test
//	public void testUserName() {
//		UserRepository ps = mock(UserRepository.class);
//		Optional<User>p = ps.findByUsername("sudha");
//		Optional<User>p1 = ps.findByUsername("sudha");
//
//		
//		Mockito.when(ps.findByUsername(Mockito.anyString())).thenReturn(p,p1);
//		// If you are using argument matchers, all
//
//
//}
//	
	
	
	

}
