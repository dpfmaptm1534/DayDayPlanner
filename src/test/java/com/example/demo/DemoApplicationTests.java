package com.example.demo;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.Sheet;
import com.example.demo.model.network.request.SheetApiRequest;
import com.example.demo.model.repository.MoneyBoardRepository;
import com.example.demo.model.repository.SheetRepository;
import com.example.demo.service.SheetApiLogicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

//    @Test
//    void contextLoads() {
//    }

    @Autowired
    private SheetRepository sheetRepository;
    @Autowired
    private MoneyBoardRepository moneyBoardRepository;
    @Autowired
    private SheetApiLogicService sheetApiLogicService;

    @Test
    public void sheetlist(){
        Long memberId = 1L;
        List<Sheet> lists = sheetRepository.findAllByMemberIdOrderByIdDesc(memberId);

        System.out.println(lists);
    }

    @Test
    public void api(){
        List<Sheet> list = sheetApiLogicService.list(1L);
        System.out.println(list.get(0).getId());

    }
}
