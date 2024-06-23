package com.theshapesk8.theshapesk8API.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.theshapesk8.theshapesk8API.model.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

	@Query("SELECT pd FROM ProductDetail pd WHERE " +
	           "LOWER(pd.nome) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(pd.descricao) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(pd.categoria) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(pd.subcategoria) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	           "LOWER(pd.marca) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	    List<ProductDetail> findBySearchTerm(@Param("searchTerm") String searchTerm);
	
}
