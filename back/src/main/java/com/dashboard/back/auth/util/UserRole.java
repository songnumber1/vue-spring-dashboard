package com.dashboard.back.auth.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserRole {
    public static List<String> getUserRoles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        if (userDetails == null) {
            return null;
        }

        System.out.println("=================================");

        Iterator<? extends GrantedAuthority> iter = userDetails.getAuthorities().iterator();
        List<String> userroles = new ArrayList<>();

        while (iter.hasNext()) {
            GrantedAuthority auth = iter.next();
            userroles.add(auth.getAuthority());
        }

        return userroles;
    }

    public static String GetUserRoleHeightest() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        if (userDetails == null) {
            return null;
        }

        System.out.println("=================================");

        Iterator<? extends GrantedAuthority> iter = userDetails.getAuthorities().iterator();
        List<String> userroles = new ArrayList<>();

        while (iter.hasNext()) {
            GrantedAuthority auth = iter.next();
            userroles.add(auth.getAuthority());
        }

        if (userroles.size() == 1) {
            return userroles.get(0);
        } else {
            return null;
        }
    }
}
