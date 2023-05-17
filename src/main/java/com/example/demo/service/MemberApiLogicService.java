package com.example.demo.service;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.entity.Sheet;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.BoardApiRequest;
import com.example.demo.model.network.request.MemberApiRequest;
import com.example.demo.model.network.request.SheetApiRequest;
import com.example.demo.model.network.response.BoardApiResponse;
import com.example.demo.model.network.response.MemberApiResponse;
import com.example.demo.model.network.response.SheetApiResponse;
import com.example.demo.model.repository.MoneyBoardRepository;
import com.example.demo.model.repository.MoneyMemberRepository;
import com.example.demo.model.repository.SheetRepository;
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

}

