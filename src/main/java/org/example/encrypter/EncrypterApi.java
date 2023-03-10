package org.example.encrypter;

import org.example.entities.Data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        // Creamos el iniciador y el filtro para trabajar con nuestro .dat
        FileOutputStream file;
        ObjectOutputStream buffer = null;
        try {
            // Instanciamos ambos
            file = new FileOutputStream("src/main/resources/hashed_data.dat");
            buffer = new ObjectOutputStream(file);
        } catch (IOException e) {
            System.out.println("No se puede abrir el fichero");
            System.out.println(e.getMessage());
        }

        ObjectOutputStream finalBuffer = buffer;
        return (email, password) -> {
            List<String> datos = new ArrayList<>() {{
                add(email);
                add(password);
            }};
            List<String> datosEncrypt = new ArrayList<>();
            datos.forEach(
                    arg -> {
                        try {
                            // Encriptamos el argumento
                            MessageDigest md = MessageDigest.getInstance("SHA-256");
                            byte[] messageDigest = md.digest(arg.getBytes());
                            BigInteger no = new BigInteger(1, messageDigest);
                            String hashtext = no.toString(16); //texto encriptado
                            while (hashtext.length() < 32) {
                                hashtext = "0" + hashtext;
                            }
                            datosEncrypt.add(hashtext);

                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }

                    }
            );

            Data data = new Data(datosEncrypt.get(0), datosEncrypt.get(1));
            try {
                assert finalBuffer != null;
                finalBuffer.writeObject(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Cifrado realizado correctamente";
        };

    }

    static EncrypterApi Md5Encrypter() {
        // Creamos el iniciador y el filtro para trabajar con nuestro .dat
        FileOutputStream file;
        ObjectOutputStream buffer = null;
        try {
            // Instanciamos ambos
            file = new FileOutputStream("src/main/resources/fichero_cifrado.dat");
            buffer = new ObjectOutputStream(file);
        } catch (IOException e) {
            System.out.println("No se puede abrir el fichero");
            System.out.println(e.getMessage());
        }

        ObjectOutputStream finalBuffer = buffer;
        return (email, password) -> {
            List<String> datos = new ArrayList<>() {{
                add(email);
                add(password);
            }};
            List<String> datosEncrypt = new ArrayList<>();
            datos.forEach(
                    arg -> {
                        try {
                            // Encriptamos el argumento
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            byte[] messageDigest = md.digest(arg.getBytes());
                            BigInteger no = new BigInteger(1, messageDigest);
                            String hashtext = no.toString(16); //texto encriptado
                            while (hashtext.length() < 32) {
                                hashtext = "0" + hashtext;
                            }
                            datosEncrypt.add(hashtext);

                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }

                    }
            );

            Data data = new Data(datosEncrypt.get(0), datosEncrypt.get(1));
            try {
                assert finalBuffer != null;
                finalBuffer.writeObject(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Cifrado realizado correctamente";
        };
    }
}
