package by.guz.fantasy.football.util;

import by.guz.fantasy.football.entity.enums.UserRoleEntity;
import by.guz.fantasy.football.security.impl.UserDetailsImpl;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtil {

    public static Long getUserId() {
        var userDetails = getUserDetails();
        return userDetails.getUserId();
    }

    public static UserRoleEntity getUserRole() {
        var userDetails = getUserDetails();
        return userDetails.getRole();
    }

    public static UserDetailsImpl getUserDetails() {
        return (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getDetails();
    }

}
