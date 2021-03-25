package br.com.ricardo.gestaorestaurante.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ricardo.gestaorestaurante.model.ContaMesa;
import br.com.ricardo.gestaorestaurante.model.Pedido;
import br.com.ricardo.gestaorestaurante.model.SituacaoPedido;
import br.com.ricardo.gestaorestaurante.repository.PedidosRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidosRepository pedidosRepository;
	
	public Pedido salvarPedido(Pedido pedido) {
		return pedidosRepository.save(pedido);
	}

	public List<Pedido> buscarTodos() {
		return pedidosRepository.findAll();
	}
	
	public List<Pedido> buscarPendentes() {
		return pedidosRepository.findBySituacaoOrSituacao(SituacaoPedido.NOVO, SituacaoPedido.CONCLUIDO);
	}

	public Optional<Pedido> buscarId(Long pedidoId) {
		return pedidosRepository.findById(pedidoId);
	}

	public boolean existePedido(Long pedidoId) {
		return pedidosRepository.existsById(pedidoId);
	}

	public ContaMesa fecharConta(Integer mesaId) {
		List<Pedido> pedidos = pedidosRepository.
				findByNumeroMesaAndSituacao(mesaId, SituacaoPedido.CONCLUIDO);
		ContaMesa contaMesa = new ContaMesa();
		if (pedidos.size() == 0) {
			contaMesa.setMensagem("Mesa não tem pedidos concluídos");
			return contaMesa;
		}
		contaMesa.setMensagem("Conta da mesa " + mesaId + " fechada com sucessso");
		contaMesa.setValorConta( calcularContaMesa(pedidos));
		return contaMesa;
	}

	private BigDecimal calcularContaMesa(List<Pedido> pedidos) {
		BigDecimal contaMesa = new BigDecimal(00.00);
		for (Pedido pedido : pedidos) {
			pedido.setSituacao(SituacaoPedido.FECHADO);
			pedidosRepository.save(pedido);
			contaMesa = contaMesa.add(pedido.getValor());
		}
		return contaMesa;
	}

}
