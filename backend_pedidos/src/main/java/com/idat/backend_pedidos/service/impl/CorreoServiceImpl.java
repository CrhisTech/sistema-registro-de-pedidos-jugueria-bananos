package com.idat.backend_pedidos.service.impl;

import com.idat.backend_pedidos.service.CorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CorreoServiceImpl implements CorreoService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    @Value("${app.mail.from-name}")
    private String nombreRemitente;

    @Override
    public void enviarContrasenaTemporal(String correo, String nombreCompleto, String contrasenaTemporal) {
        String texto = """
                Hola %s:

                Recibimos una solicitud para recuperar el acceso a tu cuenta de la app de la Juguería Bananos.

                Tu contraseña temporal es: %s

                Inicia sesión con ella y luego cámbiala desde Mi perfil > Cambiar contraseña.
                Si no solicitaste este cambio, comunícate con nosotros.

                Juguería Bananos
                """.formatted(nombreCompleto, contrasenaTemporal);
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, StandardCharsets.UTF_8.name());
            helper.setFrom(remitente, nombreRemitente);
            helper.setTo(correo);
            helper.setSubject("Recuperación de contraseña - Juguería Bananos");
            helper.setText(texto);
            mailSender.send(mensaje);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            throw new MailPreparationException("No se pudo preparar el correo de recuperación", ex);
        }
    }
}
