package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.botsale.collections.ProductCol;
import uz.pdp.botsale.entity.Product;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByNameStartingWithIgnoreCase(String search, Pageable pageableById);

    String findByName(String name);

    Optional<Product> findByBarCode(String barcode);
}
