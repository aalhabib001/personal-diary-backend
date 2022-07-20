package bd.info.habib.personaldiarybackend.service;

import bd.info.habib.personaldiarybackend.dto.ApiDataResponse;
import bd.info.habib.personaldiarybackend.dto.request.LoginRequest;
import bd.info.habib.personaldiarybackend.dto.request.PassChangeRequest;
import bd.info.habib.personaldiarybackend.dto.request.SignUpRequest;
import bd.info.habib.personaldiarybackend.dto.response.JwtResponse;
import bd.info.habib.personaldiarybackend.dto.response.ProfileResponse;
import bd.info.habib.personaldiarybackend.model.Role;
import bd.info.habib.personaldiarybackend.model.RoleName;
import bd.info.habib.personaldiarybackend.model.UserModel;
import bd.info.habib.personaldiarybackend.repository.RoleRepository;
import bd.info.habib.personaldiarybackend.repository.UserRepository;
import bd.info.habib.personaldiarybackend.security.jwt.JwtProvider;
import bd.info.habib.personaldiarybackend.utils.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.ValidationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResponseEntity<ApiDataResponse<String>> signUp(SignUpRequest signUpRequest) throws ValidationException {

        if (userRepository.existsByUsername(signUpRequest.getUserName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken!");

        Set<String> rolesString = new HashSet<>();
        rolesString.add("USER");

        UserModel userModel = UserModel.builder()
                .name(signUpRequest.getName())
                .username(signUpRequest.getUserName())
                .roles(getRolesFromStringRoles(rolesString))
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        userModel.setUuid(UUID.randomUUID().toString());
        userRepository.save(userModel);

        return new ResponseEntity<>(new ApiDataResponse<String>(201, "Account Created", null), HttpStatus.CREATED);
    }


    public ResponseEntity<ApiDataResponse<JwtResponse>> signIn(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return new ResponseEntity<>(new ApiDataResponse<>(200, "Login Successful",
                new JwtResponse(jwt, "Bearer")), HttpStatus.OK);
    }

    public ResponseEntity<ApiDataResponse<String>> changePassword(String jwtToken, PassChangeRequest passChangeRequest) {
        String userName = jwtProvider.getUserNameFromJwt(jwtToken);
        Optional<UserModel> userModelOptional = userRepository.findByUsername(userName);

        if (userModelOptional.isPresent()) {
            UserModel userModel = userModelOptional.get();

            if (encoder.matches(passChangeRequest.getOldPass(), userModel.getPassword())) {
                userModel.setPassword(encoder.encode(passChangeRequest.getNewPass()));

                userRepository.save(userModel);

                return new ResponseEntity<>(new ApiDataResponse<>(200,
                        "Password Change Successful", null), HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password Doesn't match");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No User Found");
        }
    }

    public Set<Role> getRolesFromStringRoles(Set<String> roles2) throws ValidationException {
        Set<Role> roles = new HashSet<>();
        for (String role : roles2) {
            Optional<Role> roleOptional = roleRepository.findByName(RoleName.valueOf(role));
            if (roleOptional.isEmpty()) {
                throw new ValidationException("Role '" + role + "' does not exist.");
            }
            roles.add(roleOptional.get());
        }
        return roles;
    }

    private Set<String> getRolesStringFromRole(Set<Role> roles2) {
        Set<String> roles = new HashSet<>();
        for (Role role : roles2) {
            roles.add(role.getName().toString());
        }
        return roles;
    }

    public ResponseEntity<ApiDataResponse<ProfileResponse>> getLoggedUserProfile(String jwtToken) {
        String userName = AuthUtils.getUserName();

        UserModel user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Profile Found"));

        ProfileResponse profile = new ProfileResponse();
        BeanUtils.copyProperties(user, profile);

        return new ResponseEntity<>(new ApiDataResponse<>(200, "Profile Found", profile), HttpStatus.OK);
    }

}
