package io.github.wilembergson.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wilembergson.vendas.domain.entity.ItemPedido;

public interface ItemsPedidos extends JpaRepository<ItemPedido, Integer>{

}
