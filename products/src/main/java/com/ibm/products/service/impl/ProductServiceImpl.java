package com.ibm.products.service.impl;

import com.ibm.products.domain.Product;
import com.ibm.products.model.ProductPatchModel;
import com.ibm.products.model.ProductPostModel;
import com.ibm.products.model.ProductResponseModel;
import com.ibm.products.repository.ProductRepository;
import com.ibm.products.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected ModelMapper modelMapper;

    @Override
    public JSONObject findAll() {
        JSONObject response = new JSONObject();
        response.put("data", productRepository.findAll());
        response.put("code", HttpStatus.OK);

        return response;
    }

    @Override
    public ProductResponseModel saveProduct(ProductPostModel model) {
        log.debug("Saving product [{}]", model);
        Product productEntity = modelMapper.map(model, Product.class);
        productEntity.setCreateAt(LocalDateTime.now());
        productRepository.saveAndFlush(productEntity);
        return modelMapper.map(productEntity, ProductResponseModel.class);
    }

    @Override
    public ProductResponseModel findProductById(Long productId) {
        log.debug("Finding a product by id: {}", productId);
        Product productEntity = productRepository.findById(productId).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cannot find product by id [%s]".formatted(productId)
                ));
        return modelMapper.map(productEntity, ProductResponseModel.class);


    }

    @Override
    public void deleteProductbyId(Long productId) {
        log.debug("Deleting product by id {}", productId);
        productRepository.findById(productId).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cannot find product by id [%s]".formatted(productId)
                ));
        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponseModel updateProductSelective(ProductPatchModel model) {
        log.debug("Updating product {}", model);
        Long productId = model.getId();
        Product productEntity = productRepository.findById(productId).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cannot find product by id [%s]".formatted(productId)
                ));
        if(model.getName() != null){
            productEntity.setName(model.getName());
        }
        if(model.getPrice() != null){
            productEntity.setPrice(model.getPrice());
        }
        return modelMapper.map(
                productRepository.saveAndFlush(productEntity), ProductResponseModel.class
        );
    }


}
