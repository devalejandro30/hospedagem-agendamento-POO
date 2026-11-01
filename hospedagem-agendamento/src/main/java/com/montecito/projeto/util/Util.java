package com.montecito.projeto.util;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Lança DateTimeParseException se o texto não estiver no formato dd/MM/yyyy HH:mm
    public static LocalDateTime lerDataHora(String texto) {
        return LocalDateTime.parse(texto.trim(), FORMATO);
    }

    public static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora == null ? "pendente" : dataHora.format(FORMATO);
    }

    public static String formatarMoeda(double valor) {
        return NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(valor);
    }
}