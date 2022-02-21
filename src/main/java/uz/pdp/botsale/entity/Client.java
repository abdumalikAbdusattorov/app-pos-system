package uz.pdp.botsale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.botsale.entity.template.AbsNameEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client extends AbsNameEntity {
    private String name;
    private Double debt;
    private Double payed;
    private String phoneNumber;
    private String loyalCard;

//    private boolean active = true;

/*    public Client(String loyalCard) {
        this.loyalCard = loyalCard;
    }*/
}
