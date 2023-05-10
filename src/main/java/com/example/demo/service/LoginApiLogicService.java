package com.example.demo.service;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.LoginApiRequest;
import com.example.demo.model.network.response.LoginApiResponse;
import com.example.demo.model.network.response.RegisterApiResponse;
import com.example.demo.model.repository.MoneyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoginApiLogicService {


    private final MoneyMemberRepository moneyMemberRepository;

    // 1:비밀번호 불일치  |  2:존재하지않는 계정   |  3:로그인성공
    public Map<String,MoneyMember> checkid(Header<LoginApiRequest> request){
        //실제계정조회

        Map map = new HashMap();
        MoneyMember member = moneyMemberRepository.findByUserId(request.getData().getUserId())
                .orElse(null);
        if(member!=null){
            if(member.getUserPw().equals(request.getData().getUserPw())) {
                map.put("3",member);
            }else{
                map.put("1",null);
            }
        }else {
            map.put("2",null);
        }
        return map;
    }
}
