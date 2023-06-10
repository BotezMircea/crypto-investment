package com.botez.mircea.cryptoinvestment.model.repository;

import com.botez.mircea.cryptoinvestment.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long>, JpaSpecificationExecutor<Currency> {

    @Query(value = "SELECT * " +
           "FROM Currency currency " +
           "WHERE currency.symbol = :currencySymbol " +
           "ORDER BY currency.timestamp ASC " +
           "LIMIT 1 ", nativeQuery = true)
    Currency findOldestValue(@Param("currencySymbol") String currencySymbol);

    @Query(value = "SELECT * " +
            "FROM Currency currency " +
            "WHERE currency.symbol = :currencySymbol " +
            "ORDER BY currency.timestamp DESC " +
            "LIMIT 1 ", nativeQuery = true)
    Currency findNewestValue(@Param("currencySymbol") String currencySymbol);

    @Query(value = "SELECT * " +
            "FROM Currency currency " +
            "WHERE currency.symbol = :currencySymbol " +
            "ORDER BY currency.price DESC " +
            "LIMIT 1 ", nativeQuery = true)
    Currency findMaxValue(@Param("currencySymbol") String currencySymbol);

    @Query(value = "SELECT * " +
            "FROM Currency currency " +
            "WHERE currency.symbol = :currencySymbol " +
            "ORDER BY currency.price ASC " +
            "LIMIT 1 ", nativeQuery = true)
    Currency findMinValue(@Param("currencySymbol") String currencySymbol);

    @Query("SELECT DISTINCT currency.symbol " +
           "FROM Currency currency ")
    List<String> findAllCurrencies();

    @Query(value = "SELECT * " +
            "FROM Currency currency " +
            "WHERE currency.symbol = :currencySymbol " +
            "AND currency.timestamp >= :startDate " +
            "AND currency.timestamp <= :endDate " +
            "ORDER BY currency.price ASC " +
            "LIMIT 1 ", nativeQuery = true)
    Currency findMinValue(@Param("currencySymbol") String currencySymbol,
                          @Param("startDate") Long startDate,
                          @Param("endDate") Long endDate);

    @Query(value = "SELECT * " +
            "FROM Currency currency " +
            "WHERE currency.symbol = :currencySymbol " +
            "AND currency.timestamp >= :startDate " +
            "AND currency.timestamp <= :endDate " +
            "ORDER BY currency.price DESC " +
            "LIMIT 1 ", nativeQuery = true)
    Currency findMaxValue(@Param("currencySymbol") String currencySymbol,
                          @Param("startDate") Long startDate,
                          @Param("endDate") Long endDate);
}
