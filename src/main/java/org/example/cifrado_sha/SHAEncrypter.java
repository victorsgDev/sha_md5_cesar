package org.example.cifrado_sha;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.Scanner;

public class SHAEncrypter {
    public static void main(String[] args) {
        String password = "mypassword";
        String email = "myuser@gmail.com";
        String hashedPassword = hashPassword(password);
        String hashedEmail = hashEmail(email);

        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println("Hashed User: " + hashedEmail);
        saveToFile("hashed_data.txt", hashedEmail, hashedPassword);

        //Ahora el usuario se le pide que ingrese su email y su password
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su email: ");
        String emailUser = scanner.nextLine();
        System.out.println("Ingrese su password: ");
        String passwordUser = scanner.nextLine();

        String hashedEmailUser = hashEmail(emailUser);
        String hashedPasswordUser = hashPassword(passwordUser);

        //Verifico que el email y el password ingresados por el usuario sean correctos mediante el archivo
        //hashed_data.txt
        String[] data = readFromFile("hashed_data.txt");
        String hashedEmailFile = data[0];
        String hashedPasswordFile = data[1];

        //Si el usuario ingresa su email y su password, se debe verificar que sean correctos
        //Si son correctos, se debe mostrar un mensaje de acceso permitido y si no, se debe mostrar un mensaje de acceso denegado
        if (hashedEmailUser.equals(hashedEmailFile) && hashedPasswordUser.equals(hashedPasswordFile)) {
            System.out.println("Acceso permitido");
            System.out.println("Bienvenido " + emailUser);

        } else {
            System.out.println("Acceso denegado");
        }


    }

    private static String[] readFromFile(String s) {
        String[] data = new String[2];
        try {
            File file = new File(s);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                data[i] = scanner.nextLine();
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
    public static String hashPassword (String password){
        MessageDigest md = null;
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
    }

    public static String hashEmail (String email){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashInBytes = md.digest(email.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void saveToFile (String fileName, String hashedEmail, String hashedPassword){
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
            //Escribimos en el archivo tanto el usuario como la contraseña
            writer.write(hashedEmail + "\n" + hashedPassword);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}