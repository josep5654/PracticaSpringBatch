package com.josepablo.batch.persona.infraestructure.BatchConfiguration;

import com.josepablo.batch.persona.infraestructure.BatchConfiguration.listener.JobListener;
import com.josepablo.batch.persona.infraestructure.BatchConfiguration.procesor.PersonaItemProcessor;
import com.josepablo.batch.persona.model.Persona;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class Batchconfiguration {
    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Persona> reader() {
        return new FlatFileItemReaderBuilder<Persona>()
                .name("PersonaItemReader")
                .resource(new ClassPathResource("MOCK_DATA.csv"))
                .delimited().names(new String[]{"nombre", "apellido", "anio"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Persona>() {{
                    setTargetType(Persona.class);
                }})
                .build();

    }

    @Bean
    public PersonaItemProcessor processor() {
        return new PersonaItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Persona>writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Persona>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO persona (nombre,apellido,anio)VALUES(:nombre,:apellido,:anio)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importPersonaJob(JobListener jobListener, Step step){
        return jobBuilderFactory.get("importPersonaJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobListener)
                .flow(step)
                .end().build();
    }
    @Bean
    public Step step1(JdbcBatchItemWriter<Persona> writer){
        return  stepBuilderFactory.get("step1")
                .<Persona,Persona>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
