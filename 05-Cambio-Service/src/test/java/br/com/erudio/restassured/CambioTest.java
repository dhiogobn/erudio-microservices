package br.com.erudio.restassured;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class CambioTest {
	
	@Test
	public void testCambio() {
		//Configurar o caminho comum de acesso a minha api rest
		baseURI = "http://localhost";
		port = 8000;
		basePath = "/cambio-service";

		//Login na API rest

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\n");
		stringBuilder.append("  \"id\": 1,\r\n");
		stringBuilder.append("  \"from\": \"USD\",\n");
		stringBuilder.append("  \"to\": \"BRL\",\r\n");
		stringBuilder.append("  \"conversionFactor\": 5.73,\n");
		stringBuilder.append("  \"convertedValue\": 28.65,\n");
		stringBuilder.append("  \"environment\": \"8000\"\n");
		stringBuilder.append("}");
		String corpo = given()
						.body(stringBuilder.toString())
							.contentType(ContentType.JSON)
					.when()
						.get("/5/USD/BRL")
					.then()
						.extract()
							.path("from");		
		//registrar a viagem
		given().pathParam("amount", 5).pathParam("from", "USD").pathParam("to", "BRL")
		.when().get("/{amount}/{from}/{to}")
		.then().log().all().assertThat().statusCode(200);
	}

}
