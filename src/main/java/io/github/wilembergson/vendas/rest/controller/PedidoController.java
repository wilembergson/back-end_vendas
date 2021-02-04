package io.github.wilembergson.vendas.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.wilembergson.vendas.domain.entity.ItemPedido;
import io.github.wilembergson.vendas.domain.entity.Pedido;
import io.github.wilembergson.vendas.domain.entity.enums.StatusPedido;
import io.github.wilembergson.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.wilembergson.vendas.rest.dto.InformacoesItemPedidoDTO;
import io.github.wilembergson.vendas.rest.dto.InformacoesPedidoDTO;
import io.github.wilembergson.vendas.rest.dto.PedidoDTO;
import io.github.wilembergson.vendas.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private PedidoService service;

	public PedidoController(PedidoService service) {
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save(@RequestBody @Valid PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
		String novoStatus = dto.getNovoStatus();
		service.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
	}
	
	@GetMapping("{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service.obterPedidoCompleto(id).map(p -> converter(p)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
	}
	
	private InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO.builder()
									.codigo(pedido.getId())
									.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
									.cpf(pedido.getCliente().getCpf())
									.nomeCliente(pedido.getCliente().getNome())
									.total(pedido.getTotal())
									.status(pedido.getStatus().name())
									.items(converter2(pedido.getItens()))
									.build();
	}
	
	private List<InformacoesItemPedidoDTO> converter2(List<ItemPedido> itens){
		if(itens.isEmpty()) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(item -> InformacoesItemPedidoDTO
											.builder().descricaoProduto(item.getProduto().getDescricao())
											.precoUnitario(item.getProduto().getPreco())
											.quantidade(item.getQuantidade())
											.build()
									).collect(Collectors.toList());
	}
}
