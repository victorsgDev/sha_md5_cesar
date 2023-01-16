package org.example;

import org.example.encrypter.EncrypterApi;

import java.util.Scanner;

public class Main {

    /*
    Contraseña = mypassword
    Correo = myuser@gmail.com
     */

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("""
                ¿Qué tipo de cifrado desea utilizar?
                1. Cifrado Cesar
                2. Cifrado MD5
                3. Iniciar Sesión MD5
                4. Cifrado SHA
                 """);
        var opcion = scanner.nextLine();

        switch (opcion) {
            case "1" -> {
                System.out.println("Ingrese el correo");
                var correo = scanner.nextLine();
                System.out.println("Ingrese la contraseña");
                var password = scanner.nextLine();
                System.out.println(EncrypterApi.cesarEncrypter().encrypt(correo, password));
            }
            case "2" -> {
                System.out.println("Ingrese el correo");
                var correo = scanner.nextLine();
                System.out.println("Ingrese la contraseña");
                var password = scanner.nextLine();
                System.out.println(EncrypterApi.shaEncrypter().encrypt(correo, password));
            }
            case "3" -> System.out.println("Has seleccionado la tercera opción");

            default -> System.out.println("Opción no válida");
        }
    }
}