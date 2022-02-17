package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Book;
import br.com.erudio.proxy.CambioProxy;
import br.com.erudio.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book endPoint")
@RestController
@RequestMapping("/book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy proxy;
	@Operation(summary = "Find a especific book by your id")
	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable long id, @PathVariable String currency) {
		var book = repository.getById(id);
		if(book == null) throw new RuntimeException("Book not found");
		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		
		var port = environment.getProperty("local.server.port"); 
		book.setEnvironment("Book port "+port+ " Cambio port "+cambio.getEnvironment());
		book.setPrice(cambio.getConvertedValue());
		return book;
	}
	

	//http://localhost:8000/book-service/8/USD/BRL
	/*@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable long id, @PathVariable String currency) {
		var book = repository.getById(id);
		if(book == null) throw new RuntimeException("Book not found");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
		
		var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/"+
		"{amount}/{from}/{to}", Cambio.class, params);
		var cambio = response.getBody();
	
		var port = environment.getProperty("local.server.port"); 
		book.setEnvironment(port);
		book.setPrice(cambio.getConvertedValue());
		return book;
	}*/
	
}
