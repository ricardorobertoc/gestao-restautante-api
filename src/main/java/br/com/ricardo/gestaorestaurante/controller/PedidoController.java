package br.com.ricardo.gestaorestaurante.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ricardo.gestaorestaurante.model.ContaMesa;
import br.com.ricardo.gestaorestaurante.model.Pedido;
import br.com.ricardo.gestaorestaurante.model.SituacaoPedido;
import br.com.ricardo.gestaorestaurante.service.PedidoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping
	public ResponseEntity<Pedido> incluirPedido(@Valid @RequestBody Pedido pedido) {
		pedido.setSituacao(SituacaoPedido.NOVO);
		pedidoService.salvarPedido(pedido);
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Pedido>> buscarTodosPedidos() {
		List<Pedido> pedidos = pedidoService.buscarTodos();
		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}
	
	@GetMapping("/pendentes")
	public ResponseEntity<List<Pedido>> buscarPedidosPendentes() {
		List<Pedido> pedidos = pedidoService.buscarPendentes();
		return new ResponseEntity<>(pedidos, HttpStatus.OK);
	}

	@GetMapping("/{pedidoId}")
	public ResponseEntity<Pedido> buscarPedido(@PathVariable Long pedidoId) {
		Optional<Pedido> pedido = pedidoService.buscarId(pedidoId);
		if (pedido.isPresent()) {
			return ResponseEntity.ok(pedido.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{pedidoId}")
	public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long pedidoId, @Valid @RequestBody Pedido pedido) {
		if (!pedidoService.existePedido(pedidoId)) {
			return ResponseEntity.notFound().build();
		}
		pedido.setId(pedidoId);
		pedidoService.salvarPedido(pedido);
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}

	@PutMapping("/mesa/{mesaId}")
	public ResponseEntity<ContaMesa> fecharContaMesa(@PathVariable Integer mesaId) {
		return new ResponseEntity<>(pedidoService.fecharConta(mesaId), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
