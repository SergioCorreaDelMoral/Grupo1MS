package com.ibm.products.repository;

import com.ibm.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //SELECT * FROM PRODUCT WHERE NAME = "NINTENDO"

//    public List<Product> findByName(@Param("name")String name);
//
//    @Query("SELECT * FROM product WHERE name =:nameofProduct")
//    public List<Product> getProductsByName(@Param("name")String nameOfProduct);
//
//    @Query(value = "SELECT * FROM PRODUCT WHERE name =:nameofProduct", nativeQuery = true)
//    public List<Product> getProductsByNameNative(@Param("name")String nameOfProduct);
}
