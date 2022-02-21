package uz.pdp.botsale.payload;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReqCompany {
    private Long id;
    private String name;
    @Pattern(regexp = "^[+][9][9][8][0-9]{9}$", message = "Phone number must be 13 digits.")
    private String phoneNumber;
    private String agentName;
}
