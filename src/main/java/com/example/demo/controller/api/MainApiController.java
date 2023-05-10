package com.example.demo.controller.api;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.Sheet;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.BoardApiRequest;
import com.example.demo.model.network.request.SheetApiRequest;
import com.example.demo.model.network.response.BoardApiResponse;
import com.example.demo.model.network.response.SheetApiResponse;
import com.example.demo.service.BoardApiLogicService;
import com.example.demo.service.SheetApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("main")
@RequiredArgsConstructor
public class MainApiController {
    private final SheetApiLogicService sheetApiLogicService;
    private final BoardApiLogicService boardApiLogicService;


    @PostMapping("")
    public Header<SheetApiResponse> createSheet(@RequestBody SheetApiRequest request) {
        return  sheetApiLogicService.createSheet(request);
    }
    @PostMapping("/addPlan")
    public Header<BoardApiResponse> addPlan(@RequestBody BoardApiRequest request){
        System.out.println(request);
        return boardApiLogicService.addPlan(request);
    }
    @PutMapping("")
    public Header<SheetApiResponse> update(@RequestBody SheetApiRequest request){
        return sheetApiLogicService.update(request);
    }
    @DeleteMapping("/deleteSheet")
    public String deleteSheet(@RequestBody SheetApiRequest request){
        return sheetApiLogicService.deleteSheet(request);
    }
    @DeleteMapping("")
    public String deleteBoard(@RequestBody BoardApiRequest request){
        return boardApiLogicService.deleteBoard(request);
    }

}
