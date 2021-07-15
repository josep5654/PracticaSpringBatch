package com.josepablo.batch.persona.infraestructure.BatchConfiguration.procesor;

import com.josepablo.batch.persona.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class PersonaItemProcessor implements ItemProcessor<Persona,Persona> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonaItemProcessor.class);


    @Override
    public Persona process(Persona persona) throws Exception {

        String nombre = persona.getNombre().toUpperCase();
        String apellido = persona.getApellido().toUpperCase();
        Integer anio  = persona.getAnio();

        Persona persona1 = new Persona(nombre,apellido,anio);

        LOG.info(("Se ha convertido a persona: "+persona1.toString()));

        return persona1;
    }
}
