package org.example.encrypter;

import java.io.Serializable;

public record Data(String email, String password) implements Serializable {

}
