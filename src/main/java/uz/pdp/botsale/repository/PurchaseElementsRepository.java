package uz.pdp.botsale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.botsale.collections.PurchaseElementsCol;
import uz.pdp.botsale.entity.PurchaseElements;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PurchaseElementsRepository extends JpaRepository<PurchaseElements, Long> {

//    @Query(value = "SELECT SUM(purchase.count) - (SELECT SUM(sell.count) FROM sell WHERE sell.product_id = $id) as total FROM purchase WHERE purchase.product_id =$id", nativeQuery = true)
//    List<Object[]> asdfselectBenefit(@Param(value = "productId") Long productId);

//    @Query(value = "select  p.id, p.income_price, p.present_count from purchase_elements p where p.product_id_id=:productId and p.present_count<>0 order by created_at desc limit 1", nativeQuery = true)
//    List<Object[]> selectBenefit(@Param(value = "productId") Long productId);

//    @Query(value = "select count ((p.count * p.income_price)) from purchase_elements p join purchase p1 where p.purchase_id=:id", nativeQuery = true)
//    Double findByPurchaseId(Long id);

    @Query(value = "select sum (p.count * p.income_price) from purchase_elements p where created_at between '1990-09-01 15:13:09'=:startDate and '2080-09-01 19:43:57'=:endDate", nativeQuery = true)
    Double findAllByCountAndProduct(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    Optional<PurchaseElementsCol> findPurchaseElementsById(Long id);

    @Query(value = "select * from purchase_elements ps where product_id_id = :id and (ps.present_count > 0) order by id limit 1", nativeQuery = true)
    PurchaseElements findByProductId(@Param("id") Long id);

//    @Modifying
//    @Query(value = "update purchase_elements pe set pe.present_count=0 where pe.id=:id", nativeQuery = true)
//    void update(@Param("id") Long id);

    @Query(value = "select * from purchase_elements ps where product_id_id = :id and (ps.present_count > 0) order by present_count desc limit 1", nativeQuery = true)
    PurchaseElements findPurchaseElementsByProductId(@Param("id") Long id);

    Optional<PurchaseElements> findByProductBarCode(String productBarcode);

//    @Query(value = "select * from purchase_elements ps where product_id_id = :id and (ps.present_count > 0) order by present_count desc limit 1", nativeQuery = true)
//    Optional<PurchaseElements> findStokeDetailByIdAndEndOrderBySellPrice(Long productId, Timestamp endData);

//    @Query(value = "SELECT SUM(purchase.count) - (SELECT SUM(sell.count) FROM sell WHERE sell.product_id = $id) as total FROM purchase WHERE purchase.product_id =$id", nativeQuery = true)
//    List<Object[]> asdfselesdsdctBenefit(@Param(value = "productId") Long productId);

}
