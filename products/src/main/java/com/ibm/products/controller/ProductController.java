package com.ibm.products.controller;

import com.ibm.products.model.ProductPatchModel;
import com.ibm.products.model.ProductPostModel;
import com.ibm.products.model.ProductResponseModel;
import com.ibm.products.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    Environment env;

    //@ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint que regresa todos products")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllProducts(){
        JSONObject response = service.findAll();
        response.put("port", env.getProperty("local.server.port"));
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @Operation(summary = "Endpoint that creates a product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseModel saveProduct(@Valid @RequestBody ProductPostModel model){
        return service.saveProduct(model);
    }

    @Operation(summary = "Endpoint that gets a Product by Id")
    @GetMapping("/{productId}")
    public ProductResponseModel findProduct(@PathVariable("productId") Long productId) {
        return service.findProductById(productId);
    }

    @Operation(summary = "Endpoint that deletes a Product defined by id")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") Long productId){
        service.deleteProductbyId(productId);
    }

    @PatchMapping("/{productId}")
    public ProductResponseModel updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductPatchModel model) {
        model.setId(productId);
        return service.updateProductSelective(model);
    }

}
