package com.example.demo.service;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.MemberApiRequest;
import com.example.demo.model.network.request.PasswordApiRequest;
import com.example.demo.model.network.response.MemberApiResponse;
import com.example.demo.model.repository.MoneyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberApiLogicService {
    private final MoneyMemberRepository moneyMemberRepository;

    public Header<MemberApiResponse> response(MoneyMember moneyMember){
        MemberApiResponse moneyApiResponse = MemberApiResponse.builder()
                .userName(moneyMember.getUserName())
                .build();
        return Header.OK(moneyApiResponse);
    }

    public Header<MemberApiResponse> modifyName(MemberApiRequest moneyMember){
        Optional<MoneyMember> member = moneyMemberRepository.findById(moneyMember.getId());
        MoneyMember newMember = member.get();
        newMember.setUserName(moneyMember.getUserName());
        return response(moneyMemberRepository.save(newMember));
    }

    public String chpassword(PasswordApiRequest request){
        Optional<MoneyMember> member = moneyMemberRepository.findById(request.getId());
        if(member.isPresent()){
            MoneyMember updateMember = member.get();
            if(updateMember.getUserPw().equals(request.getOldPw())){
                updateMember.setUserPw(request.getNewPw());
                moneyMemberRepository.save(updateMember);
                return "success";
            }else{
                return "inconsistency";
            }

        }else{
            return "nullid";
        }
    }

}

