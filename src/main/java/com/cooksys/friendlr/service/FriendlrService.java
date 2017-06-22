package com.cooksys.friendlr.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.friendlr.dto.PersonWithIdDto;
import com.cooksys.friendlr.exception.PersonNotFoundException;
import com.cooksys.friendlr.pojo.Person;


@Service
public class FriendlrService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	List<Person> allPeople = new ArrayList<Person>();
	
	public List<Person> getAllPersons() {
		return allPeople;
	}

	public Person getPerson(Integer personId) {
		checkIds(personId);
		
		return allPeople.get(personId);
	}

	public Person createPerson(Person person) {
		person.setId(allPeople.size());
		allPeople.add(person);
		return person;
	}

	public Person addFriend(Integer personId, Integer friendId) {
		checkIds(personId, friendId);
		
		allPeople.get(personId).getFriends().add(allPeople.get(friendId));
		return allPeople.get(personId);
	}

	public List<Person> getFriends(Integer personId) {
		checkIds(personId);
		
		return allPeople.get(personId).getFriends();
	}
	
	private void checkIds(Integer... ids) {
		
		for(Integer id : ids) {
			log.debug("Checking id: " + id);
			if(id >= allPeople.size() || id < 0) {
				log.debug("id " + id + " is not valid! Throwing exception");
				throw new PersonNotFoundException(id);
			}
			log.debug("id " + id + " is valid");
		}
	}
	
}
