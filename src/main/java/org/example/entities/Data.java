package org.example.entities;

import java.io.Serializable;

// Record class para construir el objeto a almacenar en .dat
public record Data(String email, String password) implements Serializable {

}
