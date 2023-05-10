package com.example.demo.controller.api;

import com.example.demo.controller.CrudController;
import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.RegisterApiRequest;
import com.example.demo.model.network.response.RegisterApiResponse;
import com.example.demo.service.RegisterApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterApiController extends CrudController<RegisterApiRequest, RegisterApiResponse, MoneyMember> {
    private final RegisterApiLogicService registerApiLogicService;

    @GetMapping("/search")
    public Header<RegisterApiResponse> search(@RequestParam("userid") String userid){
        return registerApiLogicService.searchid(userid);
    }

    @Override
    public Header<RegisterApiResponse> create(@RequestBody Header<RegisterApiRequest> request) {
        System.out.println(request.getData());
        return registerApiLogicService.create(request);
    }
}
