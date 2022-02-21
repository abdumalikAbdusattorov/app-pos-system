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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Purchase extends AbsNameEntity {
    @OneToMany(fetch = FetchType.LAZY)
    private List<PurchaseElements> purchaseElementsList;
    @OneToOne(fetch = FetchType.LAZY)
    private Company company;
    private Double Total;
    private boolean active = true;
}
