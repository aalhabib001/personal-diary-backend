package bd.info.habib.personaldiarybackend.service;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.request.LoginRequest;
import bd.info.habib.personaldiarybackend.dto.request.PassChangeRequest;
import bd.info.habib.personaldiarybackend.dto.request.SignUpRequest;
import bd.info.habib.personaldiarybackend.dto.response.JwtResponse;
import bd.info.habib.personaldiarybackend.dto.response.ProfileResponse;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.ValidationException;

public interface AuthService {
    ResponseEntity<ApiDataResponse<String>> signUp(SignUpRequest signUpRequest) throws ValidationException;

    ResponseEntity<ApiDataResponse<JwtResponse>> signIn(LoginRequest loginRequest);

    ResponseEntity<ApiDataResponse<ProfileResponse>> getLoggedUserProfile(String jwtToken);

    ResponseEntity<ApiDataResponse<String>> changePassword(String jwtToken, PassChangeRequest passChangeRequest);
}
