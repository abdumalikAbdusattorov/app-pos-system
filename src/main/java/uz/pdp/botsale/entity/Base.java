package uz.pdp.botsale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.botsale.entity.template.AbsNameEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Base extends AbsNameEntity {
    @OneToMany(fetch = FetchType.LAZY)
    private List<PurchaseElements> purchaseElementsList;
    @OneToOne(fetch = FetchType.LAZY)
    private Market market;
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
    private Double count;
    private Double sellprice;
    private Double stockpercent;//opshi astatka soni
}
