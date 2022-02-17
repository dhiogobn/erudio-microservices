package br.com.erudio.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Cambio;
import br.com.erudio.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio endPoint")
@RestController
@RequestMapping("/cambio-service")
public class CambioController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(CambioController.class);
	@Operation(summary = "Get Cambio from currency")
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(@PathVariable BigDecimal amount,@PathVariable String from,@PathVariable String to){
		var cambio = repository.findByFromAndTo(from, to);
		if(cambio == null) throw new RuntimeException("Currency Unsuported");
		var port = environment.getProperty("local.server.port");
		logger.info(port);
		System.out.println("testando port: ");
		System.out.println(port);
		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
		cambio.setEnvironment(port);
		return cambio;
	}
	
	
	
}
