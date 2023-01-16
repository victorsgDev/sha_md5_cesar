package org.example.cifrado_cesar;

import java.util.Arrays;

@FunctionalInterface
public interface CesarEncrypter {

    String encrypt(String palabra);

    static CesarEncrypter start() {
        return palabra -> {
            var abecedario = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            var palabraCifrada = new StringBuilder();
            var palabraSplit = palabra.split("");
            Arrays.stream(palabraSplit)
                    .forEach(s -> Arrays.stream(abecedario)
                            .forEach(letra -> {
                                if (s.equals(letra)) {
                                    palabraCifrada.append(letra);
                                }
                            }));
            return palabraCifrada.toString();
        };
    }
}
