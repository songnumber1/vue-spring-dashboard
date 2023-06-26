package com.dashboard.back.auth.advice;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.dashboard.back.auth.entity.menu.MenuCategory;
import com.dashboard.back.auth.repository.MenuCategoryRepository;

@Component
@Aspect
public class MenuAdvice {
    @Autowired
    public MenuCategoryRepository menuCategoryRepository;

    @Around("execution(* com.dashboard.back.auth.controller..*Controller.*(..))")
    public Object getMenuList(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("getMenuList");

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            for (int i = 0; i < method.getParameters().length; i++) {
                String parameterName = method.getParameters()[i].getName();
                if (parameterName.equals("menuModel")) {

                    Object[] args = joinPoint.getArgs();

                    if (args[i] instanceof Model) {
                        Model model = (Model) args[i];
                        menuList(model);
                        model.addAttribute("reponseTableData", "11");
                    }
                    break;
                }
            }

            Object result = joinPoint.proceed();

            System.out.println(result);

            return result;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    private String menuList(Model model) {
        List<MenuCategory> categoryList = menuCategoryRepository.findActiveAll();
        final StringWriter sw = new StringWriter();
        final ObjectMapper mapper = new ObjectMapper();
        String resultToString = null;

        try {
            mapper.writeValue(sw, categoryList);
            resultToString = sw.toString();
            model.addAttribute("category", resultToString);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
