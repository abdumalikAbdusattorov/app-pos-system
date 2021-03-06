package uz.pdp.botsale.repository;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.botsale.entity.Role;

@Projection(name = "customRole", types = {Role.class})
public interface CustomRole {
    Integer getId();
    String getName();
}
