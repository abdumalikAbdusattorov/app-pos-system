package uz.pdp.botsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.botsale.collections.CategoryCol;
import uz.pdp.botsale.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    String findByName(String name);

    Page<Category> findAllByNameStartingWithIgnoreCase(String search, Pageable pageableById);

    Object findAllByActive(boolean active);

    Optional<CategoryCol> findCategoryById(Integer id);

}
