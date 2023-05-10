package com.example.demo.controller;

import com.example.demo.ifs.CrudInterface;
import com.example.demo.model.network.Header;
import com.example.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
// 컨트롤러 추상화
// 추상클래스 이용해 컨트롤러 정의, API컨트롤러에 상속해줌으로써 중복코드사용 줄일수있음
public abstract class CrudController<Req,Res,Entity> implements CrudInterface<Req,Res> {

    // 상속받는 클래스만 접근가능
    @Autowired(required = false)
    protected BaseService<Req,Res,Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        return baseService.create(request);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("{}")
    public Header delete(@PathVariable Long id) {
        return baseService.delete(id);
    }
}