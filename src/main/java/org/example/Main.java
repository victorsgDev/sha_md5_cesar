package org.example;

import org.example.desencripter.AuthApi;
import org.example.encrypter.EncrypterApi;

import java.util.Scanner;

public class Main {

    /*
      El usuario debe registrarse primero
     */

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        boolean runing = true;
        while (runing){
            System.out.println("""
                ¿Qué tipo de cifrado desea utilizar?
                1. Cifrado Cesar
                2. Registrarse SHA-256
                3. Iniciar sesión con cifrado SHA-256
                4. Registrarse MD5
                5. Iniciar sesión con cifrado MD5
                6. Fin del programa
                 """);
            var opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> {
                    System.out.println("Ingrese el correo");
                    var correo = scanner.nextLine();
                    System.out.println("Ingrese la contraseña");
                    var password = scanner.nextLine();
                    var resultado = EncrypterApi.cesarEncrypter().encrypt(correo, password);
                    System.out.println(resultado);
                }
                case "2" -> {
                    System.out.println("Ingrese el correo");
                    var correo = scanner.nextLine();
                    System.out.println("Ingrese la contraseña");
                    var password = scanner.nextLine();
                    EncrypterApi.shaEncrypter().encrypt(correo, password);
                }
                case "3" -> {
                    System.out.println("Ingrese el correo");
                    var correo = scanner.nextLine();
                    System.out.println("Ingrese la contraseña");
                    var password = scanner.nextLine();
                    var resultado = AuthApi.loginSha().auth(correo, password);
                    if (resultado) {
                        System.out.println("Credenciales correctas");
                    } else {
                        System.out.println("Usuario o contraseña incorrectos");
                    }
                }
                case "4" -> {
                    System.out.println("Ingrese el correo");
                    var correo = scanner.nextLine();
                    System.out.println("Ingrese la contraseña");
                    var password = scanner.nextLine();
                    EncrypterApi.Md5Encrypter().encrypt(correo, password);
                }

                case "5" -> {
                    System.out.println("Ingrese el correo");
                    var correo = scanner.nextLine();
                    System.out.println("Ingrese la contraseña");
                    var password = scanner.nextLine();
                    var resultado = AuthApi.loginMd5().auth(correo, password);
                    if (resultado) {
                        System.out.println("Credenciales correctas");
                    } else {
                        System.out.println("Usuario o contraseña incorrectos");
                    }
                }
                case "6" -> {
                    System.out.println("¡Hasta pronto!");
                    runing = false;
                }

                default -> System.out.println("Opción no válida");
            }
        }

    }
}
