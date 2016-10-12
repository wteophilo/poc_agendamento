package br.com.wt.agendadoador.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JacksonConfiguration {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("DD-MM-YYYY HH:mm:ss");

    @Autowired
    private void registerSerializersDeserializers(List<ObjectMapper> objectMappers) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

        objectMappers.forEach(objectMapper -> objectMapper.registerModule(simpleModule));
    }
    
}