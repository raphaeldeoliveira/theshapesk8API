package com.theshapesk8.theshapesk8API.model;

import java.util.List;

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

    public List<ImagemProduct> getImages() {
        return images;
    }

    public void setImages(List<ImagemProduct> images) {
        this.images = images;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}
