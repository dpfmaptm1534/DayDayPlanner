package com.example.demo.service;


import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.entity.Sheet;
import com.example.demo.model.network.Header;

import com.example.demo.model.network.request.SheetApiRequest;

import com.example.demo.model.network.response.SheetApiResponse;
import com.example.demo.model.repository.MoneyMemberRepository;
import com.example.demo.model.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SheetApiLogicService {
    private final SheetRepository sheetRepository;
    private final MoneyMemberRepository moneyMemberRepository;

    public Header<SheetApiResponse> response(Sheet sheet){
        SheetApiResponse sheetApiResponse = SheetApiResponse.builder()
                .plannerTitle(sheet.getPlannerTitle())
                .id(sheet.getId())
                .build();
        return Header.OK(sheetApiResponse);
    }
    public MoneyMember member(Long memberid){
        MoneyMember moneyMember = MoneyMember.builder()
                .id(memberid)
                .build();
        return moneyMember;
    }


    public Header<SheetApiResponse> update(SheetApiRequest request) {
        Optional<MoneyMember> member = moneyMemberRepository.findById(request.getMemberId());
        Optional<Sheet> sheet = sheetRepository.findById(request.getId());
        Sheet newSheet = null;
        if(sheet.get().getMember()==member.get()){
            newSheet = sheetRepository.findById(request.getId()).get();
            newSheet.setPlannerTitle(request.getPlannerTitle());
        }
        return response(sheetRepository.save(newSheet));
    }


    public List list(Long memberId){
        // System.out.println("memberId: "+ memberId);
        List<Sheet> lists = sheetRepository.findAllByMemberIdOrderByIdDesc(memberId);
    return lists;
    }

    public Header<SheetApiResponse> createSheet(SheetApiRequest request) {
        Optional<MoneyMember> member = moneyMemberRepository.findById(request.getMemberId());
        Sheet sheet = Sheet.builder()
                .plannerTitle("")
                .build();
        sheet.setMember(member.get());
        Sheet newSheet = sheetRepository.save(sheet);
        return response(newSheet);
    }
    public String deleteSheet(SheetApiRequest request){
        // System.out.println("deleteSheet() servicelogic start");
        Optional<Sheet> optSheet = sheetRepository.findById(request.getId());
        if(optSheet.isPresent()){
            Sheet board = optSheet.get();
            sheetRepository.deleteById(request.getId());
            return "true";
        }else{
            return "false";
        }
    }
}
