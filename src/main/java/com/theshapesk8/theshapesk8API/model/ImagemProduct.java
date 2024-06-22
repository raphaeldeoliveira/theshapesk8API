package com.theshapesk8.theshapesk8API.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Imagem_Produto")
public class ImagemProduct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "imagem", nullable = false, columnDefinition = "TEXT")
	private String imagem;
	
	@ManyToOne
    @JoinColumn(name = "id_Produto_Detalhes", referencedColumnName = "id", nullable = false)
	@JsonIgnore
    private ProductDetail productDetail;

    public ImagemProduct() {
        
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getImagem() {
        return imagem;
    }
    
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    
    public ProductDetail getProductDetail() {
        return productDetail;
    }
    
    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public String toString() {
        return "ImagemProduct{" +
                "id=" + id +
                ", imagem='" + imagem + '\'' +
                ", productDetail=" +
                '}';
    }

}
