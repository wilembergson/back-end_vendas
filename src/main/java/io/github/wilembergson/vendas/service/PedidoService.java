package io.github.wilembergson.vendas.service;

import java.util.Optional;

import io.github.wilembergson.vendas.domain.entity.Pedido;
import io.github.wilembergson.vendas.domain.entity.enums.StatusPedido;
import io.github.wilembergson.vendas.rest.dto.PedidoDTO;

public interface PedidoService {

	Pedido salvar(PedidoDTO dto);
	
	Optional <Pedido> obterPedidoCompleto(Integer id);
	
	void atualizarStatus(Integer id, StatusPedido statusPedido);
}
