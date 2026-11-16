package com.idat.backend_pedidos.service.impl;

import com.idat.backend_pedidos.dto.request.DetallePedidoRequest;
import com.idat.backend_pedidos.dto.request.RegistrarPedidoRequest;
import com.idat.backend_pedidos.dto.response.PedidoDetalleResponse;
import com.idat.backend_pedidos.dto.response.PedidoResumenResponse;
import com.idat.backend_pedidos.entity.Cliente;
import com.idat.backend_pedidos.entity.Pedido;
import com.idat.backend_pedidos.entity.PedidoDetalle;
import com.idat.backend_pedidos.entity.ProductoOpcion;
import com.idat.backend_pedidos.entity.Sucursal;
import com.idat.backend_pedidos.entity.enums.Modalidad;
import com.idat.backend_pedidos.exception.RecursoNoEncontradoException;
import com.idat.backend_pedidos.exception.ReglaNegocioException;
import com.idat.backend_pedidos.mapper.PedidoMapper;
import com.idat.backend_pedidos.repository.ClienteRepository;
import com.idat.backend_pedidos.repository.PedidoRepository;
import com.idat.backend_pedidos.repository.ProductoOpcionRepository;
import com.idat.backend_pedidos.repository.SucursalRepository;
import com.idat.backend_pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private static final String FORMATO_CODIGO = "BAN-%04d";

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoOpcionRepository productoOpcionRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    @Transactional
    public PedidoDetalleResponse registrar(Integer idCliente, RegistrarPedidoRequest request) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el cliente con id " + idCliente));
        Sucursal sucursal = sucursalRepository.findById(request.idSucursal())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la sucursal seleccionada"));

        Map<Integer, Integer> cantidadesPorOpcion = new LinkedHashMap<>();
        for (DetallePedidoRequest item : request.detalles()) {
            cantidadesPorOpcion.merge(item.idProductoOpcion(), item.cantidad(), Integer::sum);
        }

        Map<Integer, ProductoOpcion> opciones = productoOpcionRepository
                .buscarPorIds(cantidadesPorOpcion.keySet()).stream()
                .collect(Collectors.toMap(ProductoOpcion::getIdProductoOpcion, Function.identity()));
        if (opciones.size() != cantidadesPorOpcion.size()) {
            throw new ReglaNegocioException("Uno o más productos del carrito no existen");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setSucursal(sucursal);
        pedido.setModalidad(request.modalidad());
        pedido.setMetodoPago(request.metodoPago());
        pedido.setDireccionEntrega(request.modalidad() == Modalidad.DELIVERY ? cliente.getDireccion() : null);

        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Integer, Integer> item : cantidadesPorOpcion.entrySet()) {
            ProductoOpcion opcion = opciones.get(item.getKey());
            BigDecimal subtotal = opcion.getPrecio().multiply(BigDecimal.valueOf(item.getValue()));

            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setProductoOpcion(opcion);
            detalle.setCantidad(item.getValue());
            detalle.setPrecioUnitario(opcion.getPrecio());
            detalle.setSubtotal(subtotal);
            pedido.agregarDetalle(detalle);

            total = total.add(subtotal);
        }
        pedido.setMontoTotal(total);

        Pedido guardado = pedidoRepository.saveAndFlush(pedido);
        guardado.setCodigoPedido(FORMATO_CODIGO.formatted(guardado.getIdPedido()));
        return pedidoMapper.toDetalle(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResumenResponse> listarPorCliente(Integer idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new RecursoNoEncontradoException("No existe el cliente con id " + idCliente);
        }
        return pedidoRepository.findByClienteIdClienteOrderByFechaRegistroDesc(idCliente).stream()
                .map(pedidoMapper::toResumen)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDetalleResponse obtenerDetalle(Integer idCliente, Integer idPedido) {
        Pedido pedido = pedidoRepository.findByIdPedidoAndClienteIdCliente(idPedido, idCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el pedido solicitado"));
        return pedidoMapper.toDetalle(pedido);
    }
}
