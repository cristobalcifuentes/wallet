package com.alkemy.java;

import com.alkemy.java.model.Transaction;
import com.alkemy.java.model.User;
import com.alkemy.java.model.Wallet;
import com.alkemy.java.repo.ITransactionRepo;
import com.alkemy.java.repo.IUserRepo;
import com.alkemy.java.repo.IWalletRepo;
import com.alkemy.java.service.IWalletService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class WalletTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private IWalletService walletService;

    @MockBean
    private IUserRepo userRepo;

    @MockBean
    private IWalletRepo walletRepo;

    @MockBean
    private ITransactionRepo transactionRepo;

    private User user = new User();

    private Wallet walletExpected = new Wallet();

    private Transaction transaction;

    private  LocalDateTime date = LocalDateTime.now();
    @Test
    @Before
    public void setUp() throws Exception {
        user.setUserId(1);
        user.setUsername("us@us.com");
        user.setPassword("123");


        Mockito.when(userRepo.findById(1))
                .thenReturn(Optional.ofNullable(user));


        walletExpected.setId(1);
        walletExpected.setUser(user);
        walletExpected.setBalance(200.0);
        walletExpected.setActive(true);
        walletExpected.setName("hols");

        Mockito.when(walletRepo.findWalletByUserId(user.getUserId()))
                .thenReturn(walletExpected);

        Mockito.when(transactionRepo.save(Mockito.any(Transaction.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Mockito.when(walletRepo.save(Mockito.any(Wallet.class)))
                .thenAnswer(i -> i.getArguments()[0]);

    }

    @Test
    public void testDepositFunctionService() throws Exception {

        Wallet walletActual = walletService.deposit(200.0,"dinero",1);
        assertEquals(walletExpected.getId(),walletActual.getId());

           }
    
    @Test
    public void testDepositEqualsToZero() throws Exception
    {
        String error = null;
        try {
            Wallet walletActual = walletService.deposit(0.0,"dinero",1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals("No se puede transferir saldo menor o igual a cero",error);
    }

    @Test
    public void testDepositEqualsToNegative() throws Exception
    {
        String error = null;
        try {
            Wallet walletActual = walletService.deposit(-5.0,"dinero",1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals("No se puede transferir saldo menor o igual a cero",error);
    }
}
