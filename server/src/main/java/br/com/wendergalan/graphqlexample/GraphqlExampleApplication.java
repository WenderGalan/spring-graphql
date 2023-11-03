package br.com.wendergalan.graphqlexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.wendergalan.graphqlexample")
public class GraphqlExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlExampleApplication.class, args);
	}
}
