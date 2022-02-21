package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.botsale.collections.BrandCol;
import uz.pdp.botsale.entity.Brand;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    String findByName(String name);

    Page<Brand> findAllByNameStartingWithIgnoreCase(String search, Pageable pageable);

    @Query(value = "select * from brand where id=:id", nativeQuery = true)
    Optional<BrandCol> findBrandById(Integer id);
}
