package com.josepablo.batch.persona.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Persona {

    private String nombre;
    private String apellido;
    private Integer anio;

}
