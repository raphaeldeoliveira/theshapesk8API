package com.theshapesk8.theshapesk8API.model;

import java.util.List;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class ProductPayload {
	
	private List<ImagemProduct> images;
    private List<Product> products;
    private ProductDetail productDetail;
    
    public ProductPayload() {}

    public ProductPayload(List<ImagemProduct> images, List<Product> products, ProductDetail productDetail) {
        this.images = images;
        this.products = products;
        this.productDetail = productDetail;
    }

    @OneToMany(mappedBy = "productDetail")
    public List<ImagemProduct> getImages() {
        return images;
    }

    public void setImages(List<ImagemProduct> images) {
        this.images = images;
    }

    @ManyToOne
    @JoinColumn(name = "id_Produto_Detalhes")
    public List<Product> getProduct() {
        return products;
    }

    public void setProduct(List<Product> products) {
        this.products = products;
    }

    @ManyToOne
    @JoinColumn(name = "id_Produto_Detalhes")
    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

}
