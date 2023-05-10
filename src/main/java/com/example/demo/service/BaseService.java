package com.example.demo.service;

import com.example.demo.ifs.CrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
// Service 추상화, <Req,Res,Entity>까지 Entity를 추가로 받는데, API컨트롤러에서 Entity를 받으면
// Service 추상클래스에서 해당 Entity의 Repository 의존성 주입
public abstract class BaseService<Req,Res,Entity> implements CrudInterface<Req,Res> {

    // 해당 Entity의 Repository 의존성 주입
    @Autowired(required = false)
    protected JpaRepository<Entity,Long> baseRepository; // 상속받는 클래스만 접근가능
    // JpaRepository<Item,Long>
    // JpaRepository<User,Long> ...

}