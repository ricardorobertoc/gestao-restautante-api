package br.com.ricardo.gestaorestaurante.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Item é obrigatório!")
	private String nomeItem;
	
	@NotNull(message = "Valor é obrigatório!")
	private BigDecimal valor;
	
	@NotNull(message = "Número da mesa é obrigatório!")
	private Integer numeroMesa;
	
	@Enumerated(EnumType.STRING)
	private SituacaoPedido situacao;
	
	
	
}
