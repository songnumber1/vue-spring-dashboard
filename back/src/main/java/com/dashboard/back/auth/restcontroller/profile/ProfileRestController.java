package com.dashboard.back.auth.restcontroller.profile;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dashboard.back.auth.entity.profile.Profile;
import com.dashboard.back.auth.repository.ProfileRepository;
import com.dashboard.back.auth.security.handler.AuthWorkHandler;
import com.dashboard.back.auth.util.ResponseData;

@RestController
@RequestMapping("/profile/api")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProfileRestController {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
	private AuthWorkHandler authWorkHandler;

	@Autowired
    EntityManager em;

    @GetMapping("/select")
    public ResponseEntity<?> ProfileSelect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Profile> result = profileRepository.findAll();

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

	@RequestMapping(value="/profile_save", method = RequestMethod.POST)
	public ResponseEntity<?> profile_save(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest mRequest)
	throws Exception{
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

		File file = new File("src\\main\\resources\\static\\assets");
        String rootPath = file.getAbsolutePath();
        String profileImagePath = rootPath + "\\profile_img";

		File dir = new File(profileImagePath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		String name = mRequest.getParameter("name");
		String job = mRequest.getParameter("job");
		String address = mRequest.getParameter("address");
		String image = mRequest.getParameter("image");

		// // 넘어온 파일을 리스트로 저장
		List<MultipartFile> mf = mRequest.getFiles("fileData");
		if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {
			System.out.println("1");
		}
		else {
			for (int i = 0; i < mf.size(); i++) {
				//System.out.println(profileImagePath + "\\" + mf.get(i).getOriginalFilename());
				mf.get(i).transferTo(new File(profileImagePath + "\\" + mf.get(i).getOriginalFilename()));
			}
		}

		Profile profile = Profile.builder().name(name).job(job).address(address).img(image).build();

		profileRepository.save(profile);
		
		return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", "", null);
	}

	// @PostMapping("/skill/add")
    // public ResponseEntity<?> SiteSave(HttpServletRequest request, HttpServletResponse response, @RequestBody Skill skill) {
    //     if (!request.isUserInRole("ROLE_ADMIN")) {
	// 		authWorkHandler.logoutDataDelete(request, response);
	// 		return null;
	// 	}

    //     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	// 	UserDetails userDetails = (UserDetails) principal;

	// 	if (userDetails == null) {
	// 		authWorkHandler.logoutDataDelete(request, response);
	// 		return null;
	// 	}

		

    //     profileRepository.save(Skill);
    //     return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", null, null);
    // }
}
