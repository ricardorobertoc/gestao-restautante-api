package br.com.ricardo.gestaorestaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ricardo.gestaorestaurante.model.Pedido;
import br.com.ricardo.gestaorestaurante.model.SituacaoPedido;

public interface PedidosRepository extends JpaRepository<Pedido, Long>{
	
	List<Pedido> findByNumeroMesaAndSituacao(Integer numeroMesa, SituacaoPedido situacao);
	List<Pedido> findBySituacaoOrSituacao(SituacaoPedido novo, SituacaoPedido concluido);
	
}
