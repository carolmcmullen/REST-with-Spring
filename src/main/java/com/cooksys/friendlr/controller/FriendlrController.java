package com.cooksys.friendlr.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.friendlr.dto.PersonSansIdDto;
import com.cooksys.friendlr.dto.PersonWithIdDto;
import com.cooksys.friendlr.dto.PetSansIdDto;
import com.cooksys.friendlr.dto.PetWithIdDto;
import com.cooksys.friendlr.mapper.PersonMapper;
import com.cooksys.friendlr.mapper.PetMapper;
import com.cooksys.friendlr.pojo.Person;
import com.cooksys.friendlr.pojo.Pet;
import com.cooksys.friendlr.service.FriendlrService;
import com.cooksys.friendlr.service.PetService;

@RestController
@RequestMapping("friendlr")
public class FriendlrController {

	private FriendlrService service;
	private PersonMapper mapper;
	private PetService petService;
	private PetMapper petMapper;

	public FriendlrController(FriendlrService service, PersonMapper mapper, PetMapper petMapper, PetService petService) {
		this.service = service;	
		this.mapper = mapper;
		this.petService = petService;
		this.petMapper = petMapper;
	}
	
	@GetMapping("person")
	public List<PersonWithIdDto> getAll() {
		return service.getAllPersons().stream().map(person -> mapper.toPersonWithId(person)).collect(Collectors.toList());
	}
	
	@GetMapping("person/{id}")
	public PersonSansIdDto get(@PathVariable Integer id) {
		return mapper.toPersonSansId(service.getPerson(id));
	}
	
	@PostMapping("person")
	public Person post(@RequestBody PersonSansIdDto dto, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return service.createPerson(mapper.toPerson(dto));
	}
	
	@PostMapping("person/{personId}/friends/{friendId}")
	public PersonWithIdDto post(@PathVariable Integer personId, @PathVariable Integer friendId, HttpServletResponse response) {
		return mapper.toPersonWithId(service.addFriend(personId, friendId));
	}

	@GetMapping("person/{personId}/friends")
	public List<PersonWithIdDto> getFriends(@PathVariable Integer personId) {
		return service.getFriends(personId).stream().map(person -> mapper.toPersonWithId(person)).collect(Collectors.toList());
	}
	
	@GetMapping("pet")
	public List<PetWithIdDto> getAllPets() {
		return petService.getAllPets().stream().map(pet -> petMapper.toPetWithIdDto(pet)).collect(Collectors.toList());
	}
	
	@GetMapping("pet/{id}")
	public PetSansIdDto getPet(@PathVariable Integer id) {
		return petMapper.toPetSansIdDto(petService.getPet(id));
	}
	
	@PostMapping("pet")
	public Pet post(@RequestBody PetSansIdDto dto, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return petService.createPet(petMapper.toPet(dto));
	}
	
	@PostMapping("person/{personId}/pets/{petId}")
	public PersonWithIdDto postP(@PathVariable Integer personId, @PathVariable Integer petId, HttpServletResponse response) {
		// return a specific pet for a specific person
		return mapper.toPersonWithId(petService.addPet(personId, petId));
	}

	@GetMapping("person/{personId}/pets")
	public List<PetWithIdDto> getPets(@PathVariable Integer personId) {
		// return person's pets
		return petService.getPets(personId).stream().map(person -> petMapper.toPetWithIdDto(person)).collect(Collectors.toList());
	}
	
}
