package org.example.encrypter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Stream;

@FunctionalInterface
public interface EncrypterApi {
    String encrypt(String email, String password);

    static EncrypterApi cesarEncrypter() {
        var abecedario = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        var palabraCifrada = new StringBuilder();
        return (email, password) -> {
            Stream.of(email, password).forEach(palabra -> {
                palabraCifrada.append(" ");
                var palabraSplit = palabra.split("");
                Arrays.stream(palabraSplit).forEach(s -> Arrays.stream(abecedario).forEach(letra -> {
                    if (s.equals(letra)) {
                        palabraCifrada.append(abecedario[Arrays.asList(abecedario).indexOf(letra) + 3]);
                    }
                }));
            });
            var palabraCifradaFinal = palabraCifrada.toString().split(" ");
            return "Email: %s, Contraseña: %s".formatted(palabraCifradaFinal[1], palabraCifradaFinal[2]);
        };
    }

    static EncrypterApi shaEncrypter() {
        return (email, password) -> {
            try {
                var md = MessageDigest.getInstance("SHA-256");
                var datosEncriptados =
                        Stream.of(email, password)
                                .map(palabra -> md.digest(palabra.getBytes(StandardCharsets.UTF_8)))
                                .map(bytes -> {
                                    var sb = new StringBuilder();
                                    for (byte b : bytes) {
                                        sb.append(String.format("%02x", b));
                                    }
                                    return sb.toString();
                                })
                                .toList();

                var path = Paths.get("src/main/resources/hashed_data.txt");
                var lines = Files.lines(path).toList();
                if (datosEncriptados.get(0).equals(lines.get(0)) && datosEncriptados.get(1).equals(lines.get(1))) {
                    return "Acceso permitido";
                } else {
                    return "Acceso denegado";
                }
            } catch (NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}