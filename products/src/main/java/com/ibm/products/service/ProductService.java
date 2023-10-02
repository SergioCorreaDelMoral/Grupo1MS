package com.ibm.products.service;

import com.ibm.products.model.ProductPatchModel;
import com.ibm.products.model.ProductPostModel;
import com.ibm.products.model.ProductResponseModel;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public JSONObject findAll();

    public ProductResponseModel saveProduct(ProductPostModel model);

    public ProductResponseModel findProductById(Long productId);

    void deleteProductbyId(Long productId);

    ProductResponseModel updateProductSelective(ProductPatchModel model);

}
