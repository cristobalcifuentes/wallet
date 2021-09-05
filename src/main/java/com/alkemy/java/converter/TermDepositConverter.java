package com.alkemy.java.converter;

import com.alkemy.java.DTO.TermDepositDto;
import com.alkemy.java.DTO.UserDto;
import com.alkemy.java.model.TermDeposit;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TermDepositConverter {

    public TermDepositDto convertEntityToDtoTermDeposit(TermDeposit termDeposit)
    {
        ModelMapper mapper = new ModelMapper();
        TermDepositDto termDepositDto = mapper.map(termDeposit,TermDepositDto.class);
        UserDto userDto = mapper.map(termDeposit.getUser(),UserDto.class);
        termDepositDto.setUserDto(userDto);
        return termDepositDto;
    }
}
