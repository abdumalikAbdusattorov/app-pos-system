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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AttachmentContent extends AbsNameEntity {
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Attachment attachment;

    private byte[] content;
}
