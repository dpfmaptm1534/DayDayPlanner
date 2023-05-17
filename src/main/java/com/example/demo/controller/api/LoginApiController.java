package com.example.demo.controller.api;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.LoginApiRequest;
import com.example.demo.service.LoginApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginApiLogicService loginApiLogicService;

    @PostMapping("")
    public String login(@RequestBody Header<LoginApiRequest> request, HttpServletRequest httpServletRequest){
        Map<String, MoneyMember> ck = loginApiLogicService.checkid(request);
        String code = ck.keySet().toString().substring(1,2);
                if(code.equals("3")){
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("memberId",ck.get(code).getId());
            session.setAttribute("userId",request.getData().getUserId());
            session.setAttribute("userPw",request.getData().getUserPw());
            session.setAttribute("userName",ck.get(code).getUserName());
            session.setAttribute("profileImage",ck.get(code).getProfileImage());
                }
        return code;
    }



}
