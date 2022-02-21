package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.botsale.entity.Base;

import java.util.Optional;

public interface BaseRepository extends JpaRepository<Base, Long> {
    Optional<Object> findById(Integer id);

}
