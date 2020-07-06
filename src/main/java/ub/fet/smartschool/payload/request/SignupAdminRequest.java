package ub.fet.smartschool.payload.request;


import lombok.Data;
import ub.fet.smartschool.model.Role;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class SignupAdminRequest {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 20)
    private String nationalid;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String realnames;

    @NotBlank
    @Size(max = 20)
    private String address;
    @Size(max = 1)
    private char sex;


    @Size(max = 15)
    private String m_status;

    private LocalDateTime regdate;

    private LocalDate dob;

    private Set<String> roles = new HashSet<>();

}
