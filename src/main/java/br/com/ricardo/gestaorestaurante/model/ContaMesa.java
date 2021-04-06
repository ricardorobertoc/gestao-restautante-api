package br.com.ricardo.gestaorestaurante.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaMesa {
	
	private List<Pedido> pedidos;
	private BigDecimal valorConta;
	private String mensagem;
	
}
