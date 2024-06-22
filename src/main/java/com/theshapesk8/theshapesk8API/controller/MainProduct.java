package com.theshapesk8.theshapesk8API.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mainProductTeste")
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
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ProductPayload create(@RequestBody ProductPayload productPayload) {
		//return service.create(address);
		
		for (ImagemProduct imageProduct : productPayload.getImages()) {
			imageProductService.create(imageProduct);
		}

		productDetailService.create(productPayload.getProductDetail());
		
		for (Product product : productPayload.getProduct()) {
			productService.create(product);
		}
		
		return new ProductPayload(productPayload.getImages(), productPayload.getProduct(), productPayload.getProductDetail());
	}
	
	/*@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductPayload create(@ModelAttribute ProductPayload productPayload) {
        // Implementação do método create aqui
        // Você pode acessar os dados enviados via multipart/form-data através do objeto productPayload
        
        for (ImagemProduct imageProduct : productPayload.getImages()) {
            imageProductService.create(imageProduct);
        }

        productDetailService.create(productPayload.getProductDetail());

        for (Product product : productPayload.getProduct()) {
            productService.create(product);
        }

        // Retorne o objeto ProductPayload atualizado ou conforme necessário
        return new ProductPayload(productPayload.getImages(), productPayload.getProduct(), productPayload.getProductDetail());
    }*/
	
}
