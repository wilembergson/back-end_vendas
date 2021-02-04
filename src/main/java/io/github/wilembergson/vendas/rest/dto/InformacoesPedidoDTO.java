package io.github.wilembergson.vendas.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;

@Builder
public class InformacoesPedidoDTO {

	private Integer codigo;
	private String cpf;
	private String nomeCliente;
	private BigDecimal total;
	private String dataPedido;
	private String status;
	private List<InformacoesItemPedidoDTO> items;
	
	public InformacoesPedidoDTO() {
	}
	
	public InformacoesPedidoDTO(Integer codigo, String cpf, String nomeCliente, BigDecimal total, String dataPedido, String status, List<InformacoesItemPedidoDTO> items) {
		this.codigo = codigo;
		this.cpf = cpf;
		this.nomeCliente = nomeCliente;
		this.total = total;
		this.status = status;
		this.setDataPedido(dataPedido);
		this.items = items;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<InformacoesItemPedidoDTO> getItems() {
		return items;
	}

	public void setItems(List<InformacoesItemPedidoDTO> items) {
		this.items = items;
	}

	public String getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(String dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
