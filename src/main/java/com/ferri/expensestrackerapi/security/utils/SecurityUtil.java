package com.ferri.expensestrackerapi.security.utils;

import com.ferri.expensestrackerapi.security.services.UserDetailsImpl;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static UserDetailsImpl getUser() {
        try {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                    SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() instanceof UserDetailsImpl){
                return (UserDetailsImpl) authentication.getPrincipal();
            }
        }catch (Exception e){
            throw new AccessDeniedException("Do not has Access");
        }

        return null;
    }

    public static Long getUserId(){
        UserDetailsImpl user = getUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
