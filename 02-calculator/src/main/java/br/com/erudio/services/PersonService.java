package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class PersonService {
	
	private final AtomicLong counter = new AtomicLong();
	
	public Person findById(String id) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("dhiogo");
		person.setLastName("nobrega");
		person.setAddress("Campina grande- Paraíba - Brasil");
		person.setGender("Masculino");
		return person ;
	}

	public List<Person> findAll() {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i < 8; i++) {
			Person person = new Person();
			persons.add(addPerson(person));
		}

		return persons;
	}

	private Person addPerson(Person person) {
		person.setId(counter.incrementAndGet());
		person.setFirstName("dhiogo");
		person.setLastName("nobrega");
		person.setAddress("Campina grande- Paraíba - Brasil");
		person.setGender("Masculino");
		return person ;
	}
	public Person create(Person person) {
		return person;
	}
	
	public Person upDate(Person person) {
		return person;
	}

	public void delete(String id) {
	}
	
	

}
