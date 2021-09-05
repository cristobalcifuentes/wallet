package com.alkemy.java.controller;


import com.alkemy.java.DTO.TransactionDto;
import com.alkemy.java.DTO.WalletDto;
import com.alkemy.java.converter.WalletConverter;
import com.alkemy.java.model.Transaction;
import com.alkemy.java.model.Wallet;
import com.alkemy.java.service.ITransactionService;
import com.alkemy.java.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("wallet")
public class WalletController {

    @Autowired
    private IWalletService walletService;

    @Autowired
    private ITransactionService transactionService;

    private final WalletConverter walletConverter = new WalletConverter();

    @GetMapping("users")
    public ResponseEntity<WalletDto> getWallet(Authentication authentication) throws Exception {
        Integer userId = Integer.valueOf(authentication.getName());
        WalletDto walletDto = walletConverter.convertEntityToDtoWallet(walletService.findWalletByUserId(userId));
        return new ResponseEntity<WalletDto>(walletDto,HttpStatus.OK);
    }

    @PostMapping("deposits")
    public ResponseEntity<WalletDto> deposit(@RequestParam("money") Double money, @RequestParam("detail") String detail, Authentication authentication) throws Exception {

       Integer idUser = Integer.valueOf(authentication.getName());
        WalletDto walletDto = walletConverter.convertEntityToDtoWallet(walletService.deposit(money,detail,idUser));
        return new ResponseEntity<WalletDto>(walletDto,HttpStatus.OK);

    }

    @PostMapping("transfers")
    public ResponseEntity<List<WalletDto>> transfer(@RequestParam("money") Double money,@RequestParam("detail") String detail,Authentication authentication, @RequestParam("userToTransfer") String user2) throws Exception {
        Integer userId = Integer.valueOf(authentication.getName());
        List<WalletDto> walletDto = walletConverter.convertEntityToDtoWalletList(walletService.transferMoneyUsertoUser(money, userId, user2, detail));
        return new ResponseEntity<List<WalletDto>>(walletDto, HttpStatus.OK);
    }

    @PostMapping("expenses")
    public ResponseEntity<WalletDto> spendMoney(@RequestParam("money") Double money,@RequestParam("detail") String detail,Authentication authentication) throws Exception{
        Integer userId = Integer.valueOf(authentication.getName());
        return new ResponseEntity<WalletDto>(walletConverter.convertEntityToDtoWallet(walletService.expense(userId, money, detail)), HttpStatus.OK);
    }

    @PutMapping("transactions")
	public ResponseEntity<TransactionDto> updateTransactionDetail(@RequestParam("transactionId") Integer transactionId, @RequestParam("newDetail") String newDetail ) throws Exception{

        TransactionDto transactionDto = walletConverter.convertEntityToDtoTransaction(transactionService.updateTransactionDetail(transactionId, newDetail));
        return new ResponseEntity<TransactionDto>(transactionDto, HttpStatus.OK);

	}	
    
    @GetMapping("transactions/list")
    public ResponseEntity<List<TransactionDto>> transactionListByUser(Authentication authentication){
    	
    	Integer userId = Integer.valueOf(authentication.getName());
    	List<TransactionDto> transactionDto = walletConverter.convertEntityToDtoTransactionList(transactionService.transactionListByUserId(userId));
    	return new ResponseEntity<List<TransactionDto>>(transactionDto, HttpStatus.OK);
    }
	
}
