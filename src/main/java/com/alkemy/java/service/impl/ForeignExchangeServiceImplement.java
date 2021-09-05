package com.alkemy.java.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alkemy.java.exception.BadRequestException;
import com.alkemy.java.model.ForeignExchange;
import com.alkemy.java.model.Transaction;
import com.alkemy.java.model.TransactionType;
import com.alkemy.java.model.User;
import com.alkemy.java.model.Wallet;
import com.alkemy.java.model.DTO.DolarApiDTO;
import com.alkemy.java.model.DTO.DolarOficialDTO;
import com.alkemy.java.model.DTO.ForeignExchangeDTO;
import com.alkemy.java.model.DTO.ResponseDollarSellDTO;
import com.alkemy.java.model.DTO.ResponseDollarsBoughtDTO;
import com.alkemy.java.model.DTO.TransactionForeignExchangeDTO;
import com.alkemy.java.repo.IForeignExchangeRepo;
import com.alkemy.java.repo.ITransactionRepo;
import com.alkemy.java.repo.IUserRepo;
import com.alkemy.java.repo.IWalletRepo;
import com.alkemy.java.service.IForeignExchangeService;

@Service
public class ForeignExchangeServiceImplement implements IForeignExchangeService {
	
	@Autowired
	private IForeignExchangeRepo exchangeRepo;
	
	@Autowired
	private IWalletRepo walletRepo;
	
	@Autowired
	private ITransactionRepo transactionRepo;
	
	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	RestTemplate clienteRest;

	@Value("${api.dolarSi}")
	private String apiDolarSi;
	
	private final static String STATUS_OK = "ok";
	
	private final static String DETAIL_BUY = "Compra de Dolares";
	
	private final static String DETAIL_SELL = "Venta de Dolares";
	

	
	public ForeignExchange createForeignExchange(int idUser) {
		
		Optional<User> user = userRepo.findById(idUser);
		ForeignExchange foreignExchange = new ForeignExchange(user.get());
		return exchangeRepo.save(foreignExchange);
	}

	
	public boolean exist(int idUser) {
		
		int exist = exchangeRepo.exist(idUser);
		
		if(exist == 0) {
			return false;
		} else if (exist != 0) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public ForeignExchangeDTO getForeignExchangeDTO(int idUser) {
		
		ForeignExchangeDTO dto = new ForeignExchangeDTO();
		
		if (!exist(idUser)) {
			dto.build( createForeignExchange(idUser));
		}else if (exist(idUser)) {
			dto.build(exchangeRepo.getForeignExchange(idUser));
		}
		
		return dto;
	}

	
	public DolarOficialDTO getOfficialDollar() {
		
		List<DolarApiDTO> list = Arrays.asList(clienteRest.getForObject(apiDolarSi, DolarApiDTO[].class));
		
		DolarOficialDTO dolarOficial = new DolarOficialDTO();
		
		list.forEach(x -> { if(x.getCasa().getNombre().equalsIgnoreCase("Dolar Oficial")) {
				dolarOficial.build(x);}
		});
		
		return dolarOficial;
	}

	
	
	
	
	
	
	
	
	
	
	

	public float transformPesoToDollarInBuy(TransactionForeignExchangeDTO foreing) {
		
		float pesosSell = foreing.getAmount();
		float dollarSalePrice = getOfficialDollar().getSalePrice();
		float dollarBought = pesosSell / dollarSalePrice ;
		
		return dollarBought;
	}
	
	public float transformDollarsToPesosInBuy(TransactionForeignExchangeDTO foreing) {
		
		float dollarBuy = foreing.getAmount();
		float dollarSalePriceInPesos = getOfficialDollar().getSalePrice();
		float costInPesos = dollarBuy * dollarSalePriceInPesos;
		
		return costInPesos;
	}
	
	public float transformDollarTopesoInSell(TransactionForeignExchangeDTO foreing) {
		
		float dollarSell = foreing.getAmount();
		float dollarPurchasePrice = getOfficialDollar().getPurchasePrice();
		float pesosBought =  dollarPurchasePrice * dollarSell;
		
		return pesosBought;
	}
	
	public float transformPesosToDollarInSell(TransactionForeignExchangeDTO foreing) {
		
		float pesosBuy = foreing.getAmount();
		float dollarPurchasePrice = getOfficialDollar().getPurchasePrice();
		float pesosBought =  pesosBuy / dollarPurchasePrice;
		
		return pesosBought;
	}
	
	
	
	
	
	
	
	
	
	

	
	@Override
	@Transactional
	public ResponseDollarsBoughtDTO transferWalletToForeingExchangeForPesos(TransactionForeignExchangeDTO foreing) throws Exception{
		
		float pesosToBuy =  foreing.getAmount();
		float pesosAvailableForUser = walletRepo.findWalletByUserId(foreing.getIdUser()).getBalance().floatValue();
		if (pesosToBuy > pesosAvailableForUser) throw new BadRequestException("saldo insuficiente para esta operacion");
		else if (pesosToBuy == 0)  throw new BadRequestException("no es posible hacer compras con valor cero");
		else if (pesosToBuy < 0 )  throw new BadRequestException("operacion invalida, compra menor a cero");
		else {

			Wallet wallet = walletRepo.findWalletByUserId(foreing.getIdUser());
			wallet.setBalance(wallet.getBalance() - pesosToBuy);
			walletRepo.save(wallet);

			ForeignExchange exchange = exchangeRepo.getForeignExchange(foreing.getIdUser());
			float dollarbuy = transformPesoToDollarInBuy(foreing);
			exchange.setBalance(exchange.getBalance() + dollarbuy);
			exchangeRepo.save(exchange);
	
			transactionRepo.save( new Transaction( LocalDateTime.now() , (double)(foreing.getAmount()),
					DETAIL_BUY, STATUS_OK, userRepo.findById(foreing.getIdUser()).get(),
					TransactionType.COMPRA_DIVISA));
	
				return new ResponseDollarsBoughtDTO(dollarbuy, getOfficialDollar().getSalePrice(), pesosToBuy);
		}
		
	}
	
	
	@Override
	@Transactional
	public ResponseDollarsBoughtDTO transferWalletToForeingExchangeForDollars(TransactionForeignExchangeDTO foreing) throws Exception{
		
		float dollarsToBuyInPesos = transformDollarsToPesosInBuy(foreing);
		float pesosAvailableForUser = walletRepo.findWalletByUserId(foreing.getIdUser()).getBalance().floatValue();
		if(dollarsToBuyInPesos > pesosAvailableForUser)  throw new BadRequestException("saldo insuficiente para esta operacion");
		else if (dollarsToBuyInPesos == 0)  throw new BadRequestException("no es posible hacer compras con valor cero");
		else if (dollarsToBuyInPesos < 0  )  throw new BadRequestException("operacion invalida, compra menor a cero");
		else {
			
			Wallet wallet = walletRepo.findWalletByUserId(foreing.getIdUser());
			wallet.setBalance(wallet.getBalance() - dollarsToBuyInPesos);
			walletRepo.save(wallet);
			
			ForeignExchange exchange = exchangeRepo.getForeignExchange(foreing.getIdUser());
			exchange.setBalance(exchange.getBalance() + foreing.getAmount());
			exchangeRepo.save(exchange);
			
			transactionRepo.save( new Transaction( LocalDateTime.now() , (double)dollarsToBuyInPesos,
					DETAIL_BUY, STATUS_OK, userRepo.findById(foreing.getIdUser()).get(),
					TransactionType.COMPRA_DIVISA));
			
			
			return new ResponseDollarsBoughtDTO(foreing.getAmount(), getOfficialDollar().getSalePrice(), dollarsToBuyInPesos);
			
		}

	}
	

	
	
	@Override
	@Transactional
	public ResponseDollarSellDTO transferForeingExchangeToWalletForDollars(TransactionForeignExchangeDTO foreing) throws Exception{
		
		float dollarToSell =  foreing.getAmount();
		float dollarAvailableForUser = exchangeRepo.getForeignExchange(foreing.getIdUser()).getBalance().floatValue() ;
		if (dollarToSell > dollarAvailableForUser)  throw new BadRequestException("saldo insuficiente para esta operacion");
		else if (dollarToSell == 0)  throw new BadRequestException("no es posible hacer venta con valor cero");
		else if (dollarToSell < 0 )  throw new BadRequestException("operacion invalida, venta menor a cero");
		else {

			ForeignExchange exchange = exchangeRepo.getForeignExchange(foreing.getIdUser());
			exchange.setBalance(exchange.getBalance() - dollarToSell);
			exchangeRepo.save(exchange);
			
			Wallet wallet = walletRepo.findWalletByUserId(foreing.getIdUser());
			float pesosToBuy = transformDollarTopesoInSell(foreing);
			wallet.setBalance(wallet.getBalance() + pesosToBuy);
			walletRepo.save(wallet);
	
			transactionRepo.save( new Transaction( LocalDateTime.now() , (double)pesosToBuy,
					DETAIL_SELL, STATUS_OK, userRepo.findById(foreing.getIdUser()).get(),
					TransactionType.VENTA_DIVISA));
			
			return new ResponseDollarSellDTO(dollarToSell,  getOfficialDollar().getPurchasePrice() ,pesosToBuy );
		}
		
		
	}
	
	
	@Override
	@Transactional
	public ResponseDollarSellDTO transferForeingExchangeToWalletForPesos(TransactionForeignExchangeDTO foreing) throws Exception{
		
		float dollarToSell =  transformPesosToDollarInSell(foreing);
		float dollarAvailableForUser = exchangeRepo.getForeignExchange(foreing.getIdUser()).getBalance().floatValue() ;
		if (dollarToSell > dollarAvailableForUser)  throw new BadRequestException("saldo insuficiente para esta operacion");
		else if (dollarToSell == 0)  throw new BadRequestException("no es posible hacer venta con valor cero");
		else if (dollarToSell < 0 )  throw new BadRequestException("operacion invalida, venta menor a cero");
		else {

			ForeignExchange exchange = exchangeRepo.getForeignExchange(foreing.getIdUser());
			exchange.setBalance(exchange.getBalance() - dollarToSell);
			exchangeRepo.save(exchange);
			
			Wallet wallet = walletRepo.findWalletByUserId(foreing.getIdUser());
			wallet.setBalance(wallet.getBalance() + foreing.getAmount());
			walletRepo.save(wallet);
		
			transactionRepo.save( new Transaction( LocalDateTime.now() , (double)foreing.getAmount(),
					DETAIL_SELL, STATUS_OK, userRepo.findById(foreing.getIdUser()).get(),
					TransactionType.VENTA_DIVISA));
			
			return new ResponseDollarSellDTO(dollarToSell,  getOfficialDollar().getPurchasePrice() ,foreing.getAmount() );
		}
		
		
	}
	
	
	

}
