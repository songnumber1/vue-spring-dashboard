package com.dashboard.back.auth.restcontroller.data;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.auth.entity.data.SiteItem;
import com.dashboard.back.auth.entity.data.SiteItem.SiteType;
import com.dashboard.back.auth.repository.SiteRepository;
import com.dashboard.back.auth.security.handler.AuthWorkHandler;
import com.dashboard.back.auth.util.ResponseData;

@RestController
@RequestMapping("/site/api")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class SiteRestController {
    @Autowired
    private SiteRepository siteRepository;

    @Autowired
	private AuthWorkHandler authWorkHandler;

	@Autowired
    EntityManager em;

    @PostMapping("/add")
    public ResponseEntity<?> SiteSave(HttpServletRequest request, HttpServletResponse response, @RequestBody SiteItem siteItem) {
		System.out.println(siteItem);
		
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

		if(siteItem.getSiteType() == null)
			siteItem.setSiteType(SiteType.bg_primary);

        siteRepository.save(siteItem);
        return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", null, null);
    }

	@PostMapping("/edit")
    public ResponseEntity<?> SiteEdit(HttpServletRequest request, HttpServletResponse response, @RequestBody SiteItem siteItem) {
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

		SiteItem findSiteItem = siteRepository.findById(siteItem.getId()).get();

		if (findSiteItem == null) {
			return ResponseData.CreateReponse(HttpStatus.BAD_REQUEST.value(), "Item not found", null, null);
		}

		System.out.println(findSiteItem);

		findSiteItem.setSiteName(siteItem.getSiteName());
		findSiteItem.setSiteId(siteItem.getSiteId());
		findSiteItem.setSitePw(siteItem.getSitePw());
		findSiteItem.setSiteUrl(siteItem.getSiteUrl());
		findSiteItem.setWriteDate(findSiteItem.getWriteDate());
		findSiteItem.setRemark(siteItem.getRemark());
		findSiteItem.setSiteType(siteItem.getSiteType());
		
		if(findSiteItem.getSiteType() == null)
		 	findSiteItem.setSiteType(SiteType.bg_primary);

		siteRepository.save(findSiteItem);
        return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", null, null);
    }

	@GetMapping("/search/{siteName}")
	public ResponseEntity<?> SiteSearch(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "siteName") String siteName) {
		System.out.println("server siteName : " + siteName);

		// 아래처럼 createquery로 해도 되고 위에 siteRepository.findSiteNameSearch(siteName)하여도 동일하다.
		//List<SiteItem> temp = siteRepository.findSiteNameSearch(siteName);
		TypedQuery<SiteItem>  query = em.createQuery("SELECT u FROM SiteItem u WHERE u.siteName like :siteName", SiteItem.class)
		.setParameter("siteName", "%" + siteName + "%");

		List<SiteItem> result = query.getResultList();

		System.out.println(result);

		final StringWriter sw = new StringWriter();
		final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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

    @GetMapping("/select")
    public ResponseEntity<?> SiteSelect(HttpServletRequest request, HttpServletResponse response) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails) principal;

		if (userDetails == null) {
			authWorkHandler.logoutDataDelete(request, response);
			return null;
		}

        List<SiteItem> result = siteRepository.findAll();

        final StringWriter sw = new StringWriter();
		final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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

	@PostMapping("/delete")
    public ResponseEntity<?> SiteDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody SiteItem siteItem) {
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

		siteRepository.delete(siteItem);
        return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", null, null);
    }

	@PostMapping("/getInfo")
    public ResponseEntity<?> SiteGetInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody SiteItem siteItem){
        SiteItem result = siteRepository.findById(siteItem.getId()).get();

        final StringWriter sw = new StringWriter();
		final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
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
