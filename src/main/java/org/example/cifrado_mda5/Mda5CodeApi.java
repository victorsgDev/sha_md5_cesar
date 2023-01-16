package org.example.cifrado_mda5;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@FunctionalInterface
public interface Mda5CodeApi {
    String cifrar(String[] args);

    static Mda5CodeApi runCifrar() {
        FileOutputStream file;
        ObjectOutputStream buffer = null;
        try {
            file = new FileOutputStream("src/main/resources/fichero_cifrado.dat");
            buffer = new ObjectOutputStream(file);
        } catch (IOException e) {
            System.out.println("No se puede abrir el fichero");
            System.out.println(e.getMessage());
        }

        ObjectOutputStream finalBuffer = buffer;
        return args -> {

            Arrays.stream(args).forEach(
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
                            String finalHashtext = hashtext;
                            try {
                                assert finalBuffer != null;
                                finalBuffer.writeObject(finalHashtext);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }

                    }
            );

            return "Cifrado realizado correctamente";
        };
    }
}

