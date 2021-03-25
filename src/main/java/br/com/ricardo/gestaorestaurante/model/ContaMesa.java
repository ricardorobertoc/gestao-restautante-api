package br.com.ricardo.gestaorestaurante.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaMesa {
	
	private BigDecimal valorConta;
	private String mensagem;
}
