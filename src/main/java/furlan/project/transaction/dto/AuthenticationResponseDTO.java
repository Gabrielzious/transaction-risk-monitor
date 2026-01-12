package furlan.project.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object representing the authentication response containing the JWT.
 */
@Data
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String token;
}