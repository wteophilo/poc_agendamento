package br.com.wt.agendadoador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@Controller
public class Boot {

	public static void main(String[] args) {
		SpringApplication.run(Boot.class, args);
	}

}
