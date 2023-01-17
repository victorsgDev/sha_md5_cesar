package org.example.entities;

import java.io.Serializable;

public record Data(String email, String password) implements Serializable {

}
