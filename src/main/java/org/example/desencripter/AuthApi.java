package org.example.desencripter;

import org.example.entities.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface AuthApi {
    boolean auth(String email, String password);

    static AuthApi loginMd5() {
        FileInputStream file;
        ObjectInputStream buffer = null;
        try {
            file = new FileInputStream("src/main/resources/fichero_cifrado.dat");
            buffer = new ObjectInputStream(file);
        } catch (IOException e) {
            System.out.println("No se puede abrir el fichero");
            System.out.println(e.getMessage());
        }
        ObjectInputStream finalBuffer = buffer;
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

            Data dataToLog = new Data(datosEncrypt.get(0), datosEncrypt.get(1));
            try {
                assert finalBuffer != null;
                // Leemos el objeto del fichero y almacenamos
                Data dataFromFile = (Data) finalBuffer.readObject();
                // Return true si los datos del fichero y los datos introducidos son iguales
                // Return false si los datos del fichero y los datos introducidos son diferentes
                return dataToLog.equals(dataFromFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // Si no se ha podido leer el fichero, devolvemos false
            return false;
        };
    }
}

