package com.alkemy.java.converter;

import com.alkemy.java.DTO.TransactionDto;
import com.alkemy.java.DTO.UserDto;
import com.alkemy.java.DTO.WalletDto;
import com.alkemy.java.model.Transaction;
import com.alkemy.java.model.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WalletConverter {


    public WalletDto convertEntityToDtoWallet(Wallet wallet)
    {
        ModelMapper mapper = new ModelMapper();
        WalletDto dto = mapper.map(wallet,WalletDto.class);
        UserDto userDto = mapper.map(wallet.getUser(),UserDto.class);
        dto.setUser(userDto);
        return dto;
    }


    public List<WalletDto> convertEntityToDtoWalletList(List<Wallet> walletList)
    {
        return walletList.stream().map(this::convertEntityToDtoWallet).collect(Collectors.toList());
    }

    public TransactionDto convertEntityToDtoTransaction (Transaction transaction)
    {
        ModelMapper mapper = new ModelMapper();
        TransactionDto dto = mapper.map(transaction,TransactionDto.class);
        UserDto userDto = mapper.map(transaction.getUser(),UserDto.class);
        dto.setUser(userDto);
        return dto;
    }

    public List<TransactionDto> convertEntityToDtoTransactionList(List<Transaction> transactionList)
    {
        return transactionList.stream().map(this::convertEntityToDtoTransaction).collect(Collectors.toList());
    }
}
