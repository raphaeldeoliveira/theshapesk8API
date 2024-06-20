package com.theshapesk8.theshapesk8API.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Produto_Detalhes")
public class ProductDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 255)
	private String nome;
	
	@Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
	private String descricao;
	
	@Column(name = "valor", nullable = false, precision = 10, scale = 2)
	private float valor;
	
	@Column(name = "categoria", nullable = false, length = 255)
	private String categoria;
	
	@Column(name = "subCategoria", nullable = false, length = 255)
	private String subCategoria;
	
	@Column(name = "marca", nullable = false, length = 255)
	private String marca;
	
	public ProductDetail() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public float getValor() {
		return valor;
	}
	
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getSubCategoria() {
		return subCategoria;
	}
	
	public void setSubCategoria(String subCategoria) {
		this.subCategoria = subCategoria;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}

}
