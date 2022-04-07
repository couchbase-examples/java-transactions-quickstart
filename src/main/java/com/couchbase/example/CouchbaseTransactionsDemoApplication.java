package com.couchbase.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootApplication
public class CouchbaseTransactionsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouchbaseTransactionsDemoApplication.class, args);
	}

  @Bean
  ForwardedHeaderFilter forwardedHeaderFilter() {
     return new ForwardedHeaderFilter();
  }
}
