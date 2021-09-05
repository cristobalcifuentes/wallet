package com.alkemy.java.controller;

import java.net.URI;

import javax.validation.Valid;

import com.alkemy.java.DTO.TermDepositDto;
import com.alkemy.java.converter.TermDepositConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alkemy.java.model.TermDeposit;
import com.alkemy.java.service.ITermDepositService;

@RestController
@RequestMapping("/termDeposits")
public class TermDepositController {
	
	@Autowired
	private ITermDepositService termDepositService;

	private final TermDepositConverter termDepositConverter = new TermDepositConverter();
	
	@PostMapping
	public ResponseEntity<TermDepositDto> take(@Valid @RequestBody TermDeposit termDeposit) throws Exception{
		
		TermDepositDto td = termDepositConverter.convertEntityToDtoTermDeposit(termDepositService.takeTermDeposit(termDeposit));
		return new ResponseEntity<TermDepositDto>(td, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<TermDeposit> withdraw(@PathVariable("id") Integer id) throws Exception{
        
		termDepositService.withdrawDeposit(id);
		return new ResponseEntity<TermDeposit>(HttpStatus.OK);
	}
	
}
