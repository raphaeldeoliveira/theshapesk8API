package com.theshapesk8.theshapesk8API.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Produto")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tamanho", nullable = false, length = 5)
	private String tamanho;
	
	@Column(name = "quantidade", nullable = false)
	private int quantidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_Produto_Detalhes", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private ProductDetail productDetail;

	public Product() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTamanho() {
        return tamanho;
    }
    
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public ProductDetail getProductDetail() {
        return productDetail;
    }
    
    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", tamanho='" + tamanho + '\'' +
                ", quantidade=" + quantidade +
                ", productDetail=" + productDetail +
                '}';
    }

}
