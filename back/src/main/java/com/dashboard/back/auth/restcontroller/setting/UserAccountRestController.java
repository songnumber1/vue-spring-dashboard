package com.dashboard.back.auth.restcontroller.setting;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.auth.entity.setting.User;
import com.dashboard.back.auth.exception.AccountException;
import com.dashboard.back.auth.exception.AccountExceptionType;
import com.dashboard.back.auth.repository.UserRepository;
import com.dashboard.back.auth.security.handler.AuthWorkHandler;
import com.dashboard.back.auth.util.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/setting/api/userAccount")
public class UserAccountRestController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthWorkHandler authWorkHandler;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/join/Proc")
    public ResponseEntity<?> joinAdminSave(HttpServletRequest request, HttpServletResponse response,
            @RequestBody User user) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            authWorkHandler.logoutDataDelete(request, response);
            return null;
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        if (userDetails == null) {
            authWorkHandler.logoutDataDelete(request, response);
            return null;
        }

        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null
                || user.getPassword().isEmpty()) {
            throw new AccountException(AccountExceptionType.REUQIRED_PARAMETER_ERROR);
        }

        User userEntity = userRepository.findByUsername(user.getUsername());

        if (userEntity != null) {
            throw new AccountException(AccountExceptionType.DUPLICATED_USER);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userRepository.save(user);

        return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", null, null);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{account}/select")
    public ResponseEntity<?> adminAccountSelect(HttpServletRequest request, HttpServletResponse response,
            @PathVariable(value = "account") String account) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            authWorkHandler.logoutDataDelete(request, response);
            return null;
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        if (userDetails == null) {
            authWorkHandler.logoutDataDelete(request, response);
            return null;
        }

        String role = "ROLE_" + account.toUpperCase();

        // List<User> result = userRepository.findAll().stream().filter(x ->
        // x.GetRoleList().contains(filterRole))
        // .collect(Collectors.toList());

        List<User> result = userRepository.findAccountList(role);

        System.out.println(result);

        final StringWriter sw = new StringWriter();
        final ObjectMapper mapper = new ObjectMapper();
        String resultToString = null;

        try {
            mapper.writeValue(sw, result);
            resultToString = sw.toString();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", resultToString, null);
    }
}
