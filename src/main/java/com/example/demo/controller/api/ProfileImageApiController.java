package com.example.demo.controller.api;

import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.BoardApiRequest;
import com.example.demo.model.network.request.ProfileImageApiRequest;
import com.example.demo.model.network.request.SheetApiRequest;
import com.example.demo.model.network.response.BoardApiResponse;
import com.example.demo.model.network.response.ProfileImageApiResponse;
import com.example.demo.model.network.response.SheetApiResponse;
import com.example.demo.service.BoardApiLogicService;
import com.example.demo.service.ProfileImageApiLogicService;
import com.example.demo.service.SheetApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("profileapi")
@RequiredArgsConstructor
public class ProfileImageApiController {
    private final ProfileImageApiLogicService profileImageApiLogicService;
    @GetMapping("{memberId}")
    public String read(@PathVariable("memberId") Long memberId) {
        return profileImageApiLogicService.searchid(memberId);
    }
    @PostMapping("")
    public Header<ProfileImageApiResponse> create(
            @RequestParam(name ="file") MultipartFile file,
            @RequestParam(name="memberId") Long memberId) throws IOException {

        return profileImageApiLogicService.create(file,memberId);
    }
    @GetMapping("default/{memberId}")
    public String setDefault(@PathVariable("memberId")Long memberId, HttpServletRequest request){
        HttpSession session = request.getSession();
        profileImageApiLogicService.setDefault(memberId);
        session.setAttribute("profileImage","/image/index/defaultProfileImg.jpeg");
        return null;
    }
    @GetMapping("myprofile/{memberId}")
    public String setMyProfile(@PathVariable("memberId")Long memberId, HttpServletRequest request){
        HttpSession session = request.getSession();
        String myprofile = profileImageApiLogicService.setMyProfile(memberId);
        session.setAttribute("profileImage",myprofile);
        return myprofile;
    }
}
