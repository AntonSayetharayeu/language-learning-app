package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.LoginDTO;
import learningapp.service.interfaces.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO dto) {
        String token = authenticationService.login(dto.getUserName(), dto.getUserPassword());
        return ResponseEntity.ok(token);
    }
}
