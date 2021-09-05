package com.alkemy.java.service.impl;

import com.alkemy.java.exception.BadRequestException;
import com.alkemy.java.exception.NotFoundException;
import com.alkemy.java.model.Transaction;
import com.alkemy.java.model.TransactionType;
import com.alkemy.java.model.User;
import com.alkemy.java.model.Wallet;
import com.alkemy.java.repo.ITransactionRepo;
import com.alkemy.java.repo.IUserRepo;
import com.alkemy.java.repo.IWalletRepo;
import com.alkemy.java.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WalletServiceImpl implements IWalletService {

    @Autowired
    private IWalletRepo walletRepo;
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private ITransactionRepo transactionRepo;

    private static final String OK = "ok";

    @Override
    public Double getWalletBalance(Integer walletId) {
        return walletRepo.getOne(walletId).getBalance();
    }

    @Override
    public Wallet deposit(Double money, String detail, Integer userId) throws Exception{
        Optional<User> userFound = userRepo.findById(userId);
        System.out.println(userId);
        if(userFound.isPresent())
        {
                User user = userFound.get();
                Wallet wallet = findWalletByUserId(user.getUserId());
                Transaction transaction;
                LocalDateTime date = LocalDateTime.now();

                if (money <= 0)
                {
                    throw new BadRequestException("No se puede transferir saldo menor o igual a cero");
                }

                if (wallet == null) {
                    wallet = new Wallet();
                    wallet.setBalance(money);
                    wallet.setUser(user);
                    transaction = new Transaction(date,money,detail,OK,user,TransactionType.DEPOSITO);
                    transactionRepo.save(transaction);
                } else {
                    wallet.setBalance(wallet.getBalance() + money);
                    transaction = new Transaction(date,money,detail,OK,user,TransactionType.DEPOSITO);
                    transactionRepo.save(transaction);
                }

                wallet.setActive(true);

                return walletRepo.save(wallet);
        }else {
            throw new NotFoundException("Usuario inexistente");
        }
    }

	@Override
	public Wallet findWalletByUserId(Integer userId) throws Exception {

		return walletRepo.findWalletByUserId(userId);
	}

	@Override
	public void updateWalletBalance(Double newBalance) throws Exception {

		walletRepo.updateWalletBalance(newBalance);

	}

    @Override
    public List<Wallet> transferMoneyUsertoUser(Double money, Integer userId, String name2, String detail) throws Exception {
    
        Optional<User> userFound = userRepo.findById(userId);
        if(userFound.isPresent()) {
            User user1 = userFound.get();
            User user2 = userRepo.findByUsername(name2);
            LocalDateTime date = LocalDateTime.now();
            if (user2 == null) {
                throw new NotFoundException("Usuario inexistente");
            }
            Wallet wallet1 = findWalletByUserId(user1.getUserId());
            Wallet wallet2 = findWalletByUserId(user2.getUserId());

            if (money <= 0)
            {
                throw new BadRequestException("No se puede transferir saldo menor o igual a cero");
            } else if (money > wallet1.getBalance()) {
                throw new BadRequestException("No se puede transferir saldo mayor al actual");
            } else if (user1.equals(user2)) {
                throw new BadRequestException("No se puede transferir saldo a si mismo");

        } else {
            wallet1.setBalance(wallet1.getBalance() - money);
            wallet2.setBalance(wallet2.getBalance() + money);
            List<Wallet> walletList = new ArrayList<>();
            walletList.add(wallet1);
            walletList.add(wallet2);
            Transaction transaction = new Transaction(date,money,detail,"ok",user2,TransactionType.DEPOSITO);
            Transaction transaction2 = new Transaction(date,money,detail,"ok",user1,TransactionType.GASTO);
            transactionRepo.save(transaction);
            transactionRepo.save(transaction2);
            return walletList;
            }
        }
        else
        {
            throw new NotFoundException("Usuario inexistente");
        }

    }

    @Override
    public Wallet expense(Integer userId, Double money, String detail) throws Exception {

        Optional<User> userFound = userRepo.findById(userId);
        if(userFound.isPresent()) {
            User user = userFound.get();
            Wallet wallet = walletRepo.findWalletByUserId(userId);
            if (wallet == null) {
                throw new NotFoundException("La wallet del usuario no existe, debe realizar un primer deposito para crearla");
            } else if (money <= 0) {
                throw new BadRequestException("El monto no puede ser menor o igual a cero");
            } else if (money > wallet.getBalance()) {
                throw new BadRequestException("Saldo insuficiente");
            } else {
                wallet.setBalance(wallet.getBalance() - money);
                Transaction newTransaction = new Transaction(LocalDateTime.now(), money, detail, "Ok", user, TransactionType.GASTO);
                transactionRepo.save(newTransaction);
                return wallet;
            }
        } else {
            throw new NotFoundException("Usuario inexistente");
        }

    }

}

