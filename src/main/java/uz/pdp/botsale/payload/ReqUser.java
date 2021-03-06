package uz.pdp.botsale.payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ReqUser {
    private UUID id;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private List<String> roleNameList;
    private Boolean enabled;
}
