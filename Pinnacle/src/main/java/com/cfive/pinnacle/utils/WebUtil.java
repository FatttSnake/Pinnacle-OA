package com.cfive.pinnacle.utils;

import com.cfive.pinnacle.entity.permission.LoginUser;
import com.cfive.pinnacle.exception.DataValidationFailedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class WebUtil {
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return (LoginUser) principal;
    }

    public static boolean hasAuthority(String authority) {
        return hasAnyAuthority(authority);
    }

    public static boolean hasAnyAuthority(String... authorities) {
        for (String authority : authorities) {
            if (getLoginUser().getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority))) {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> convertStringToList(String str, Class<T> clazz) {
        List<T> tList = new ArrayList<>();
        try {
            if (str != null && !str.isBlank()) {
                String[] strings = str.split(",");
                for (String string : strings) {
                    if (Integer.class.equals(clazz)) {
                        tList.add((T) Integer.valueOf(string));
                    }
                    if (Long.class.equals(clazz)) {
                        tList.add((T) Long.valueOf(string));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataValidationFailedException();
        }
        return tList;
    }
}
