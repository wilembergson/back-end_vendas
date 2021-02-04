package io.github.wilembergson.vendas.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.wilembergson.vendas.domain.entity.Cliente;

public interface Clientes extends JpaRepository<Cliente, Integer>{
	
	@Query(value = " select c from Cliente c where c.nome like :nome ")
	List<Cliente> encontrarPorNome(String nome);
	
	@Query(" delete from Cliente c where c.nome =:nome ")
	void deleteByNome(String nome);
	
	boolean existsByNome(String nome);
	
	@Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
	Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
