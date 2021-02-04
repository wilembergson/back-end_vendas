package io.github.wilembergson.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wilembergson.vendas.domain.entity.Cliente;
import io.github.wilembergson.vendas.domain.entity.ItemPedido;
import io.github.wilembergson.vendas.domain.entity.Pedido;
import io.github.wilembergson.vendas.domain.entity.Produto;
import io.github.wilembergson.vendas.domain.entity.enums.StatusPedido;
import io.github.wilembergson.vendas.domain.repository.Clientes;
import io.github.wilembergson.vendas.domain.repository.ItemsPedidos;
import io.github.wilembergson.vendas.domain.repository.Pedidos;
import io.github.wilembergson.vendas.domain.repository.Produtos;
import io.github.wilembergson.vendas.exception.PedidoNaoEncontradoException;
import io.github.wilembergson.vendas.exception.RegraNegocioException;
import io.github.wilembergson.vendas.rest.dto.ItemPedidoDTO;
import io.github.wilembergson.vendas.rest.dto.PedidoDTO;
import io.github.wilembergson.vendas.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	private Pedidos repositoryPedidos;
	private Clientes clienteRepository;
	private Produtos produtoRepository;
	private ItemsPedidos itemsPedidoRepository;

	public PedidoServiceImpl(Pedidos repositoryPedidos, Clientes clienteRepository, Produtos produtoRepository, ItemsPedidos itemsPedidoRepository) {
		this.repositoryPedidos = repositoryPedidos;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
		this.itemsPedidoRepository = itemsPedidoRepository;
	}

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clienteRepository.findById(idCliente)
											.orElseThrow(() -> new RegraNegocioException("Codigo do cliente invalido."));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
		repositoryPedidos.save(pedido);
		itemsPedidoRepository.saveAll(itemsPedido);
		pedido.setItens(itemsPedido);
		
		return pedido;
	}
	
	private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
		if(items.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
		}
		
		return items.stream().map(dto -> {
			Integer idProduto = dto.getProduto();
			Produto produto = produtoRepository.findById(idProduto).orElseThrow(() -> new RegraNegocioException("Código do produto inválido: " + idProduto));
			
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(dto.getQuantidade());
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			
			return itemPedido;
			
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return repositoryPedidos.FindByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizarStatus(Integer id, StatusPedido statusPedido) {
		repositoryPedidos.findById(id).map(pedido -> {
			pedido.setStatus(statusPedido);
			return repositoryPedidos.save(pedido);
		}).orElseThrow(() -> new PedidoNaoEncontradoException());
	}
	
}
