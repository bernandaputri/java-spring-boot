package id.bca7.demoSpring.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Fullname is required!")
    private String fieldFullname;

    @NotBlank(message = "Email is required!")
    private String fieldEmail;

    @NotBlank(message = "Username is required!")
    private String fieldUsername;

    @NotBlank(message = "Password is required!")
    private String fieldPassword;
}
