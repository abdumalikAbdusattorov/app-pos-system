package uz.pdp.botsale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.botsale.entity.template.AbsNameInteger;

import javax.persistence.*;
import java.util.List;

/**
 * @author dfghjk
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Market extends AbsNameInteger {
    @Column(unique = true)
    private String name;
    @Column(columnDefinition = "text")
    private String address;
    private Double lan;
    private Double lat;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Purchase> purchaseList;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Cash> cashList;
    private boolean active = true;
}
