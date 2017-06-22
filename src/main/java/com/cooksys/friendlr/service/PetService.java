package com.cooksys.friendlr.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.friendlr.exception.PersonNotFoundException;
import com.cooksys.friendlr.pojo.Person;
import com.cooksys.friendlr.pojo.Pet;

@Service
public class PetService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	List<Pet> allPets = new ArrayList<Pet>();
	List<Person> allOwners = new ArrayList<Person>();
	
	public List<Pet> getAllPets() {
		return allPets;
	}
	
	public Pet createPet(Pet pet) {
		pet.setId(allPets.size());
		allPets.add(pet);
		return pet;
	}
	
	public Person addPet(Integer personId, Integer petId) {
		checkIds(personId, petId);
		
		allOwners.get(personId).getPets().add(allPets.get(petId));
		return allOwners.get(petId);
	}
	
	public Pet getPet(Integer petId) {
		checkIds(petId);
		
		return allPets.get(petId);
	}

	public List<Person> getOwners(Integer petId) {
		checkIds(petId);
		
		return allPets.get(petId).getOwners();
	}
	
	public List<Pet> getPets(Integer personId){
		return this.allOwners.get(personId).getPets();
	}
	
	private void checkIds(Integer... ids) {
		
		for(Integer id : ids) {
			log.debug("Checking id: " + id);
			if(id >= allPets.size() || id < 0) {
				log.debug("id " + id + " is not valid! Throwing exception");
				throw new PersonNotFoundException(id);
			}
			log.debug("id " + id + " is valid");
		}
	}
}