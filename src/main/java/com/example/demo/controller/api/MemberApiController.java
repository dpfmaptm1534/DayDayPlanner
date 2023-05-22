package com.example.demo.controller.api;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.LoginApiRequest;
import com.example.demo.model.network.request.MemberApiRequest;
import com.example.demo.model.network.request.PasswordApiRequest;
import com.example.demo.model.network.request.SheetApiRequest;
import com.example.demo.model.network.response.MemberApiResponse;
import com.example.demo.model.network.response.SheetApiResponse;
import com.example.demo.service.LoginApiLogicService;
import com.example.demo.service.MemberApiLogicService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberApiLogicService memberApiLogicService;

    @PutMapping("/name")
    public Header<MemberApiResponse> update(@RequestBody MemberApiRequest request,HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userName",request.getUserName());
        return memberApiLogicService.modifyName(request);
    }
    @PutMapping("/chpassword")
    public Header<PasswordApiRequest> chpassword(@RequestBody Header<PasswordApiRequest> request){
        String status = memberApiLogicService.chpassword(request.getData());
        if(status.equals("success")){
            return Header.OK(request.getData());
        }else if(status.equals("inconsistency")){
            return Header.ERROR("inconsistency");
        }else{
            return Header.ERROR("nonId");
        }
    }
}
