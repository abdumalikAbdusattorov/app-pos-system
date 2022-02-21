package uz.pdp.botsale.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.botsale.entity.template.AbsNameEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PurchaseElements extends AbsNameEntity {
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
    private Double incomePrice;
    private Double sellPrice;
    private Double presentPrice;
    private Integer count;
    private Integer presentCount;
    private Integer minCount;
    private boolean active = true;
}