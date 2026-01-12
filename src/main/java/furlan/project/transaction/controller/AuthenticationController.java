package furlan.project.transaction.controller;

import furlan.project.transaction.dto.AuthenticationResponseDTO;
import furlan.project.transaction.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling authentication requests and issuing JWT tokens.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    /**
     * Simple login endpoint to generate a token for testing.
     * In a real scenario, this would validate credentials against a database.
     * * @param username the name of the user requesting a token
     * @return ResponseEntity containing the generated JWT
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestParam String username) {
        // For now, we trust the username provided and generate a token
        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(new AuthenticationResponseDTO(token));
    }
}