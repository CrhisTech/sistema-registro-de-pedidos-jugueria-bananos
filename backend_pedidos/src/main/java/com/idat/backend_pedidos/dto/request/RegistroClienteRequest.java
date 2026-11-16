package com.idat.backend_pedidos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistroClienteRequest(
        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 150, message = "El nombre completo no debe superar los 150 caracteres")
        String nombreCompleto,

        @NotBlank(message = "El celular es obligatorio")
        @Pattern(regexp = "\\d{9}", message = "El celular debe tener 9 dígitos")
        String celular,

        @NotBlank(message = "La dirección exacta es obligatoria")
        @Size(max = 250, message = "La dirección no debe superar los 250 caracteres")
        String direccion,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no es válido")
        @Size(max = 150, message = "El correo no debe superar los 150 caracteres")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
        String contrasena,

        @NotBlank(message = "La confirmación de la contraseña es obligatoria")
        String confirmarContrasena
) {
}
