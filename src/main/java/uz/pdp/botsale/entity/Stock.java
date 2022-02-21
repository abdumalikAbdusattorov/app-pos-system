package uz.pdp.botsale.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.botsale.entity.template.AbsNameInteger;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Stock extends AbsNameInteger {
    private String name;
    private Double percent;
    private Timestamp startData;
    private Timestamp endData;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "stock",cascade = CascadeType.ALL)
    private List<StockDetail> stockDetailList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Market market;
    private boolean active = true;
}
