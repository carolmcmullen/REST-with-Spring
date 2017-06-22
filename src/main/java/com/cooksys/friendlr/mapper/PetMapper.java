package com.cooksys.friendlr.mapper;

import org.mapstruct.Mapper;

import com.cooksys.friendlr.dto.PetSansIdDto;
import com.cooksys.friendlr.dto.PetWithIdDto;
import com.cooksys.friendlr.pojo.Pet;

@Mapper(componentModel = "spring")
public interface PetMapper {
	
	PetSansIdDto toPetSansIdDto(Pet pt);
	
	Pet toPet(PetSansIdDto dto);
	
	PetWithIdDto toPetWithIdDto(Pet pt);
	
	Pet toPet(PetWithIdDto dto);

}
