package com.josepablo.batch.persona.infraestructure.BatchConfiguration.listener;


import com.josepablo.batch.persona.model.Persona;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



@Component
@AllArgsConstructor
public class JobListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {

        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            LOG.info("FINALIZO EL JOB");

            jdbcTemplate.query("SELECT nombre, apellido, anio FROM persona",(rs,row)-> new Persona(rs.getString(1),rs.getString(2),rs.getInt(3)))
                    .forEach(persona -> LOG.info("Registro <"+ persona+">"));
        }



        super.afterJob(jobExecution);
    }
}
