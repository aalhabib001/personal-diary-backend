package bd.info.habib.personaldiarybackend.utils;

import bd.info.habib.personaldiarybackend.security.services.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static String getUserName() {
        return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static UserPrinciple getAuthInfo() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
