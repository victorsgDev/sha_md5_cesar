package org.example.encrypter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

@FunctionalInterface
public interface EncrypterApi {
    String encrypt(String email, String password);

    static EncrypterApi cesarEncrypter() {
        var abecedario = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        var palabraCifrada = new StringBuilder();
        return (email, password) -> Stream.of(email, password)
                .map(String::toCharArray)
                .map(chars -> {
                    for (char c : chars) {
                        for (int j = 0; j < abecedario.length; j++) {
                            if (String.valueOf(c).equals(abecedario[j])) {
                                palabraCifrada.append(abecedario[j + 3]);
                            }
                        }
                    }
                    return palabraCifrada.toString();
                }).toList().toString(); //ambas palabras juntas! CORREGIR
    }

    static EncrypterApi shaEncrypter() {
        return (email, password) -> {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        };

    }
}

class Prueba{
    public static void main(String[] args) {
        var encrypter = EncrypterApi.cesarEncrypter();
        System.out.println(encrypter.encrypt("email", "password"));
    }
}