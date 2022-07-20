package bd.info.habib.personaldiarybackend.controller;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.request.LoginRequest;
import bd.info.habib.personaldiarybackend.dto.request.PassChangeRequest;
import bd.info.habib.personaldiarybackend.dto.request.SignUpRequest;
import bd.info.habib.personaldiarybackend.dto.response.JwtResponse;
import bd.info.habib.personaldiarybackend.dto.response.ProfileResponse;
import bd.info.habib.personaldiarybackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiDataResponse<String>> signUpUser(@RequestBody SignUpRequest signUpRequest) throws ValidationException {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ApiDataResponse<JwtResponse>> signInUser(@RequestBody LoginRequest loginRequest) {
        return authService.signIn(loginRequest);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiDataResponse<ProfileResponse>> getLoggedInProfile(@RequestHeader(name = "Authorization", required = true)
                                                                                       String jwtToken) {
        return authService.getLoggedUserProfile(jwtToken);
    }

    @PutMapping("/changePass")
    public ResponseEntity<ApiDataResponse<String>> changePassword(@RequestHeader(name = "Authorization",
            required = true) String jwtToken, @RequestBody PassChangeRequest passChangeRequest) {

        return authService.changePassword(jwtToken, passChangeRequest);
    }

    @GetMapping("/serverCheck")
    public String getServerStatStatus() {
        return "The Server is Running";
    }

}
