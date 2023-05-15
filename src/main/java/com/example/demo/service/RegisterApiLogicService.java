package com.example.demo.service;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.RegisterApiRequest;
import com.example.demo.model.network.response.RegisterApiResponse;
import com.example.demo.model.repository.MoneyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterApiLogicService extends BaseService<RegisterApiRequest, RegisterApiResponse, MoneyMember> {

    private final MoneyMemberRepository moneyMemberRepository;

    public Header<RegisterApiResponse> response(MoneyMember moneyMember){
        RegisterApiResponse registerApiResponse = RegisterApiResponse.builder()
                .userId(moneyMember.getUserId())
                .userPw(moneyMember.getUserPw())
                .userName(moneyMember.getUserName())
                .build();
        return Header.OK(registerApiResponse);
    }
    @Override
    public Header<RegisterApiResponse> create(Header<RegisterApiRequest> request) {
        RegisterApiRequest body = request.getData();
        MoneyMember moneyMember = MoneyMember.builder()
                .userId(body.getUserId())
                .userPw(body.getUserPw())
                .userName(body.getUserName())
                .profileImage("/image/index/defaultProfileImg.jpeg")
                .build();
        MoneyMember newMoneyMember = baseRepository.save(moneyMember);
        return response(newMoneyMember);
    }

    @Override
    public Header<RegisterApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<RegisterApiResponse> update(Header<RegisterApiRequest> request) {
        return null;
    }

    @Override
    public Header<RegisterApiResponse> delete(Long id) {
        return null;
    }

    public Header<RegisterApiResponse> searchid(String userid){
        // System.out.println("서비스단 userid:"+userid);
        return moneyMemberRepository.findByUserId(userid)
                .map(item -> response(item))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }
}
