package com.couchbase.example;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CouchbaseTransactionsDemoApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

  @Test
  void testIndexPageLoads() {
    this.webTestClient.get()
      .uri("/")
      .exchange()
      .expectStatus().isOk();
  }

  @Test
  void testTransfers() {
    this.webTestClient.get()
      .uri("/api/credit/transfer?sourceUser=user1&targetUser=user2&amount=10")
      .exchange()
      .expectStatus().isOk()
      .expectBody(String.class).isEqualTo("true");
  }
}
