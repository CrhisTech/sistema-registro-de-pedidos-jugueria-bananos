package com.idat.backend_pedidos.service.impl;

import com.idat.backend_pedidos.dto.request.ActualizarClienteRequest;
import com.idat.backend_pedidos.dto.request.CambiarContrasenaRequest;
import com.idat.backend_pedidos.dto.request.LoginRequest;
import com.idat.backend_pedidos.dto.request.RecuperarContrasenaRequest;
import com.idat.backend_pedidos.dto.request.RegistroClienteRequest;
import com.idat.backend_pedidos.dto.response.ClienteResponse;
import com.idat.backend_pedidos.dto.response.MensajeResponse;
import com.idat.backend_pedidos.entity.Cliente;
import com.idat.backend_pedidos.exception.ConflictoException;
import com.idat.backend_pedidos.exception.CredencialesInvalidasException;
import com.idat.backend_pedidos.exception.RecursoNoEncontradoException;
import com.idat.backend_pedidos.exception.ReglaNegocioException;
import com.idat.backend_pedidos.mapper.ClienteMapper;
import com.idat.backend_pedidos.repository.ClienteRepository;
import com.idat.backend_pedidos.service.ClienteService;
import com.idat.backend_pedidos.service.CorreoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private static final String CARACTERES_TEMPORAL = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    private static final int LONGITUD_TEMPORAL = 10;
    private static final String MENSAJE_RECUPERACION =
            "Si el correo está registrado, te enviamos una contraseña temporal para que inicies sesión";

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final CorreoService correoService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final SecureRandom random = new SecureRandom();

    @Override
    @Transactional
    public ClienteResponse registrar(RegistroClienteRequest request) {
        if (!request.contrasena().equals(request.confirmarContrasena())) {
            throw new ReglaNegocioException("La contraseña y su confirmación no coinciden");
        }
        String correo = normalizarCorreo(request.correo());
        if (clienteRepository.existsByCorreo(correo)) {
            throw new ConflictoException("Ya existe una cuenta registrada con ese correo electrónico");
        }
        Cliente cliente = clienteMapper.toEntity(request);
        cliente.setContrasena(passwordEncoder.encode(request.contrasena()));
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse iniciarSesion(LoginRequest request) {
        Cliente cliente = clienteRepository.findByCorreo(normalizarCorreo(request.correo()))
                .filter(c -> passwordEncoder.matches(request.contrasena(), c.getContrasena()))
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));
        return clienteMapper.toResponse(cliente);
    }

    @Override
    @Transactional
    public MensajeResponse recuperarContrasena(RecuperarContrasenaRequest request) {
        clienteRepository.findByCorreo(normalizarCorreo(request.correo())).ifPresent(cliente -> {
            String temporal = generarContrasenaTemporal();
            cliente.setContrasena(passwordEncoder.encode(temporal));
            correoService.enviarContrasenaTemporal(cliente.getCorreo(), cliente.getNombreCompleto(), temporal);
        });
        return new MensajeResponse(MENSAJE_RECUPERACION);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse obtenerPerfil(Integer idCliente) {
        return clienteMapper.toResponse(buscarCliente(idCliente));
    }

    @Override
    @Transactional
    public ClienteResponse actualizarPerfil(Integer idCliente, ActualizarClienteRequest request) {
        Cliente cliente = buscarCliente(idCliente);
        cliente.setNombreCompleto(request.nombreCompleto().trim());
        cliente.setCelular(request.celular());
        cliente.setDireccion(request.direccion().trim());
        return clienteMapper.toResponse(cliente);
    }

    @Override
    @Transactional
    public MensajeResponse cambiarContrasena(Integer idCliente, CambiarContrasenaRequest request) {
        Cliente cliente = buscarCliente(idCliente);
        if (!passwordEncoder.matches(request.contrasenaActual(), cliente.getContrasena())) {
            throw new ReglaNegocioException("La contraseña actual no es correcta");
        }
        cliente.setContrasena(passwordEncoder.encode(request.contrasenaNueva()));
        return new MensajeResponse("Tu contraseña se cambió correctamente");
    }

    private Cliente buscarCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el cliente con id " + idCliente));
    }

    private String normalizarCorreo(String correo) {
        return correo.trim().toLowerCase();
    }

    private String generarContrasenaTemporal() {
        StringBuilder temporal = new StringBuilder(LONGITUD_TEMPORAL);
        for (int i = 0; i < LONGITUD_TEMPORAL; i++) {
            temporal.append(CARACTERES_TEMPORAL.charAt(random.nextInt(CARACTERES_TEMPORAL.length())));
        }
        return temporal.toString();
    }
}
