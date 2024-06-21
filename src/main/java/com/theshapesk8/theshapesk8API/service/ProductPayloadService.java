package com.theshapesk8.theshapesk8API.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theshapesk8.theshapesk8API.model.ImagemProduct;
import com.theshapesk8.theshapesk8API.model.Product;
import com.theshapesk8.theshapesk8API.model.ProductDetail;
import com.theshapesk8.theshapesk8API.model.ProductPayload;

@Service
public class ProductPayloadService {
	
	/*@Autowired
	private ImagemProductServices service;
	
	public ProductPayload findProductPayloadById(Long id) {
        List<ImagemProduct> images = service.findAllById(id);
        Product product = findProductById(id);
        ProductDetail productDetail = findProductDetailByProductId(id);

        return new ProductPayload(images, product, productDetail);
    }*/

}
