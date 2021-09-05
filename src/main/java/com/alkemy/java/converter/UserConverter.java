package com.alkemy.java.converter;

import com.alkemy.java.DTO.TokenDTO;
import com.alkemy.java.DTO.UserDto;
import com.alkemy.java.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserConverter {

    public UserDto convertEntityToDtoUser(User user)
    {
        ModelMapper mapper = new ModelMapper();
        UserDto dto = mapper.map(user, UserDto.class);
        return dto;
    }

    public TokenDTO convertEntityToDtoToken(User user) {
        ModelMapper mapper = new ModelMapper();
        TokenDTO dto = mapper.map(user, TokenDTO.class);
        return dto;
    }

    public List<UserDto> convertEntityToDtoUserList(List<User> userList)
    {
        return userList.stream().map(this::convertEntityToDtoUser).collect(Collectors.toList());
    }
}
