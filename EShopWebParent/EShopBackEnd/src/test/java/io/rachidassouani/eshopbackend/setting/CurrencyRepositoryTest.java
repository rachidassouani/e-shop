package io.rachidassouani.eshopbackend.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import io.rachidassouani.eshopcommon.model.Currency;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CurrencyRepositoryTest {

	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Test
	public void createCurrenciesTest() {
		
		List<Currency> currencies = Arrays.asList(
			new Currency("United States Dollar", "$", "USD"),
			new Currency("British Pound", "£", "GPB"),
			new Currency("Japanese yen", "¥", "JPY"),
			new Currency("Euro", "€", "EUR"),
			new Currency("Moroccan Dirham", "DH", "MAD"),
			new Currency("Canadian Dollar", "$", "CAD"));
		currencyRepository.saveAll(currencies);
	
		List<Currency> currList = currencyRepository.findAll();
		
		assertThat(currList.size()).isEqualTo(6);
	}
	
	@Test
	public void findAllCurrenciesOrderByNameAsc() {
		List<Currency> currencies = currencyRepository.findAllByOrderByNameAsc();
		
		currencies.forEach(System.out::println);
		
		assertThat(currencies.size()).isGreaterThan(0);
	}
}
