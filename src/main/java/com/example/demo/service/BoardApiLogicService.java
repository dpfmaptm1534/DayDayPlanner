package com.example.demo.service;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.entity.Sheet;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.BoardApiRequest;
import com.example.demo.model.network.response.BoardApiResponse;
import com.example.demo.model.repository.MoneyBoardRepository;
import com.example.demo.model.repository.MoneyMemberRepository;
import com.example.demo.model.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardApiLogicService {
    private final MoneyBoardRepository moneyBoardRepository;
    private final SheetRepository sheetRepository;
    private final MoneyMemberRepository moneyMemberRepository;

    public Map<Long, Map> read(Long sheetId){
        List<MoneyBoard> list = moneyBoardRepository.findAllBySheetIdOrderByEventDate(sheetId);

//        HashMap은 저장순서 유지 안됨 / LinkedHashMap은 저장순서 유지됨
//        Map<Long,Map>dateAndMap = new HashMap<>();
        Map<Long,Map>dateAndMap = new LinkedHashMap<>();

        for(MoneyBoard board : list){
            if(!dateAndMap.containsKey(board.getEventDate())){
                dateAndMap.put(board.getEventDate(),new LinkedHashMap<Long,String>());
                dateAndMap.get(board.getEventDate()).put(board.getId(),board.getEventTitle());
            }else{
                dateAndMap.get(board.getEventDate()).put(board.getId(),board.getEventTitle());
            }
        }
        return dateAndMap;
    }

    public Header<BoardApiResponse> response(MoneyBoard moneyBoard){
        BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                .id(moneyBoard.getId())
                .eventDate(moneyBoard.getEventDate())
                .eventTitle(moneyBoard.getEventTitle())
                .sheet(moneyBoard.getSheet())
                .build();
        return Header.OK(boardApiResponse);
    }
    public Header<BoardApiResponse> addPlan(BoardApiRequest request) {

        MoneyBoard moneyBoard = MoneyBoard.builder()
                .eventDate(request.getEventDate())
                .eventTitle(request.getEventTitle())
                .build();

//      방법 1. detached entity passed to persist 에러발생시
//      MoneyBoard엔티티에 cascade = CascadeType.ALL 제거해야함
        Sheet sheet = Sheet.builder()
                .id(request.getSheetId())
                .build();
        MoneyMember member = MoneyMember.builder()
                .id(request.getMemberId())
                .build();
        moneyBoard.setSheet(sheet);
        moneyBoard.setMember(member);

//      방법 2. cascade = CascadeType.ALL 제거안해도 에러안남
//        Optional<Sheet> sheet = sheetRepository.findById(request.getSheetId());
//        Optional<MoneyMember> member = moneyMemberRepository.findById(request.getMemberId());
//        moneyBoard.setSheet(sheet.get());
//        moneyBoard.setMember(member.get());


//      공통
        MoneyBoard newBoard = moneyBoardRepository.save(moneyBoard);
        return response(newBoard);
    }
    public String deleteBoard(BoardApiRequest request){
        // System.out.println("deleteBoard() servicelogic start");
        Optional<MoneyBoard> optBoard = moneyBoardRepository.findById(request.getId());
        if(optBoard.isPresent()){
            MoneyBoard board = optBoard.get();
            moneyBoardRepository.deleteById(request.getId());
            return "true";
        }else{
            return "false";
        }
    }
}

