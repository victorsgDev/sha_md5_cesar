package org.example.encrypter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface EncrypterApi {
    String encrypt(String email, String password);

    static EncrypterApi cesarEncrypter() {
        var abecedario = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        var palabraCifrada = new StringBuilder();

        Consumer<String> cifrar = palabra -> {
            palabraCifrada.append(" ");
            var palabraSplit = palabra.split("");
            Consumer<String> cambiarCaracter = s -> Arrays.stream(abecedario)
                    .forEach(letraAbecedario -> {
                        if (s.equals(letraAbecedario)) {
                            palabraCifrada.append(abecedario[Arrays.asList(abecedario).indexOf(letraAbecedario) + 3]);
                        }
                    });
            Arrays.stream(palabraSplit)
                    .forEach(cambiarCaracter);
        };

        return (email, password) -> {
            Stream.of(email, password).forEach(cifrar);
            var palabraCifradaFinal = palabraCifrada.toString().split(" ");
            return "Email: %s, Contraseña: %s".formatted(palabraCifradaFinal[1], palabraCifradaFinal[2]);
        };
    }

    static EncrypterApi shaEncrypter() {
        return (email, password) -> {
            try {
                var md = MessageDigest.getInstance("SHA-256");

                Function<String, byte[]> getBytes = palabra -> md.digest(palabra.getBytes(StandardCharsets.UTF_8));

                Function<byte[], String> bytesToString = bytes -> {
                    var sb = new StringBuilder();
                    for (byte b : bytes) {
                        sb.append(String.format("%02x", b));
                    }
                    return sb.toString();
                };

                var datosEncriptados =
                        Stream.of(email, password)
                                .map(getBytes.andThen(bytesToString))
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