package bd.info.habib.personaldiarybackend.utils;

import bd.info.habib.personaldiarybackend.security.services.UserPrinciple;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

public class AuthUtils {

    public static String getUserName() {
        try {
            return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

    }

    public static UserPrinciple getAuthInfo() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
