package com.example.respositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.model.Product;
import com.example.model.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	//Page<ProductCategory> findByCategoryName(@PathVariable String name, Pageable pageable);

//    @Query(value=" from product p  inner join product_category pc "
//    		+ "on p.category_id = pc.id and pc.category_name=:name")
//	Page<ProductCategory> findByCategoryName(@Param("name")String name, Pageable pageable);

    
  
    //@Query(value = "from Employee e where e.salary > :sal and e.deptId=:did")
  	//public List<Employee> findBySalary(@Param("sal") double salary, @Param("did") int deptId);
  	
  	//@Query(value = "from Employee e where e.salary > :sal")
  	//public List<Employee> findBySalaryGeaterthan(@Param("sal") double salary);


}
