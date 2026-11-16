package com.idat.backend_pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ActualizarClienteRequest(
        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 150, message = "El nombre completo no debe superar los 150 caracteres")
        String nombreCompleto,

        @NotBlank(message = "El celular es obligatorio")
        @Pattern(regexp = "\\d{9}", message = "El celular debe tener 9 dígitos")
        String celular,

        @NotBlank(message = "La dirección exacta es obligatoria")
        @Size(max = 250, message = "La dirección no debe superar los 250 caracteres")
        String direccion
) {
}
