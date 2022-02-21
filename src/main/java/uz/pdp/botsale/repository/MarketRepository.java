package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.botsale.collections.MarketCol;
import uz.pdp.botsale.entity.Market;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Integer> {

    String findByName(String name);

    Page<MarketCol> findAllByNameStartingWithIgnoreCase(String search, Pageable pageableById);

    Page<Market> findByNameStartingWithIgnoreCase(String search, Pageable pageableById);

    Optional<MarketCol> findMarketById(Integer id);
}
