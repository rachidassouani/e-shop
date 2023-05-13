package io.rachidassouani.eshopbackend.setting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.rachidassouani.eshopcommon.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer>{

	List<Currency> findAllByOrderByNameAsc();
}
