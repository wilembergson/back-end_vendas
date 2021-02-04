package io.github.wilembergson.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wilembergson.vendas.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer>{

	
	
}
