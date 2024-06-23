package com.theshapesk8.theshapesk8API.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theshapesk8.theshapesk8API.model.ImagemProduct;
import com.theshapesk8.theshapesk8API.model.Product;
import com.theshapesk8.theshapesk8API.model.ProductDetail;
import com.theshapesk8.theshapesk8API.model.ProductPayload;
import com.theshapesk8.theshapesk8API.service.ImagemProductServices;
import com.theshapesk8.theshapesk8API.service.ProductDetailServices;
import com.theshapesk8.theshapesk8API.service.ProductServices;

@RestController
@RequestMapping("/mainProductTeste")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class MainProduct {

	@Autowired
	private ImagemProductServices imageProductService;
	
	@Autowired
	private ProductDetailServices productDetailService;
	
	@Autowired
	private ProductServices productService;
	
	// ENDPOINT PRA PEGAR OS PRODUTOS QUE ESTÃO EM ESTOQUE
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ProductPayload> findAll() {
	    List<ProductPayload> productsPayload = new ArrayList<>();
	    List<ProductDetail> productDetails = productDetailService.findAll();

	    for (ProductDetail productDetail : productDetails) {
	        List<ImagemProduct> images = imageProductService.findByProductDetailId(productDetail.getId());
	        List<Product> products = productService.findByProductDetailId(productDetail.getId());
	        if (!products.isEmpty()) {
	            boolean hasNonZeroQuantity = products.stream().anyMatch(product -> product.getQuantidade() > 0);
	            if (hasNonZeroQuantity) {
	                productsPayload.add(new ProductPayload(images, products, productDetail));
	            }
	        }
	    }

	    return productsPayload;
	}

	// ENDPOINT QUE PEGA UM PRODUTO ESPEPECIFICO PELO PRODUCTDETAIL
	@GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ProductPayload findById(@PathVariable(value = "id") Long id) throws Exception {
		List<ImagemProduct> images = imageProductService.findByProductDetailId(id);
		ProductDetail productDetail = productDetailService.findById(id);
		List<Product> products = productService.findByProductDetailId(id);
		return new ProductPayload(images, products, productDetail);
	}
	
	// ENDPOINT PRA ADICIONAR UM PRODUTO	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductPayload create(@RequestBody ProductPayload productPayload) {
	    ProductDetail createdProductDetail = productDetailService.create(productPayload.getProductDetail());
	    //Long productDetailId = createdProductDetail.getId();

	    for (ImagemProduct imageProduct : productPayload.getImages()) {
	        imageProduct.setProductDetail(createdProductDetail);
	        imageProductService.create(imageProduct);
	    }

	    for (Product product : productPayload.getProducts()) {
	        product.setProductDetail(createdProductDetail);
	        productService.create(product);
	    }

	    return new ProductPayload(productPayload.getImages(), productPayload.getProducts(), createdProductDetail);
	}
	
	// ENDPOINT PRA ATUALIZAR UM PRODUTO
	@PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ProductPayload update(@RequestBody ProductPayload productPayload) {
		
		ProductDetail productDetail = productPayload.getProductDetail();
	    List<Product> products = productPayload.getProducts();
	    List<ImagemProduct> images = productPayload.getImages();
		
	    for (ImagemProduct image : images) {
	    	image.setProductDetail(productDetail);
	        imageProductService.update(image);
	    }
		
	    for (Product product : products) {
	    	product.setProductDetail(productDetail);
	        productService.update(product);
	    }
		
	    productDetailService.update(productDetail);
	    
	    return productPayload;
	}

	// ENDPOINT PRA DELETAR UM PRODUTO	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
		
		List<ImagemProduct> imagens = imageProductService.findByProductDetailId(id);
		for (ImagemProduct image : imagens) {
			imageProductService.delete(image.getId());
		}
		List<Product> products = productService.findByProductDetailId(id);
		for (Product product : products) {
			productService.delete(product.getId());
		}
		productDetailService.delete(id);
		return ResponseEntity.noContent().build();
	}

	
}
