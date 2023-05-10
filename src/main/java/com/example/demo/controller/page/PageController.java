package com.example.demo.controller.page;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.Sheet;
import com.example.demo.model.repository.SheetRepository;
import com.example.demo.service.BoardApiLogicService;
import com.example.demo.service.SheetApiLogicService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {
    private final SheetApiLogicService sheetApiLogicService;
    private final BoardApiLogicService boardApiLogicService;
    private final SheetRepository sheetRepository;


    @GetMapping("/")
    public String index() {
        return "/login";
    }
    @GetMapping("login")
    public String login() {
        return "/login";
    }

    @GetMapping("regist")
    public String register() {
        return "/register";
    }

    @GetMapping("main")
    public String main(HttpServletRequest httpServletRequest, ModelMap map) {
        HttpSession session = httpServletRequest.getSession();
        Long memberId = (Long)session.getAttribute("memberId");
        String userId = (String)session.getAttribute("userId");
        String userName = (String)session.getAttribute("userName");
        String profileImage = (String)session.getAttribute("profileImage");
        if(session.getAttribute("userId")==null){
            return "redirect:/login";
        }
        System.out.println("pagecontroller sheetapilogicservic.list start");
        List<Sheet> list = sheetApiLogicService.list(memberId);
        Long sheetId;
        String sheetName;

        try {
            sheetId = list.get(0).getId();
        }catch (IndexOutOfBoundsException e){
            sheetId = null;
        }
        System.out.println("pagecontroller boardApiLogicService.read start");
        Map<Long, Map> boardList= boardApiLogicService.read(sheetId);

        try{
            sheetName = list.get(0).getPlannerTitle();
        }catch(IndexOutOfBoundsException e){
            sheetName=null;
        }

        map.addAttribute("sheetName",sheetName);
        map.addAttribute("sheetId",sheetId);
        map.addAttribute("sheetList",list);
        map.addAttribute("userId",userId);
        map.addAttribute("memberId",memberId);
        map.addAttribute("userName",userName);
        map.addAttribute("profileImage",profileImage);
        map.addAttribute("boardList",boardList);

        return "index";
    }

    @GetMapping("main/default")
    public String sheetDefault(HttpServletRequest httpServletRequest, ModelMap map){
    HttpSession session = httpServletRequest.getSession();
    if(session.getAttribute("userId")==null){
        return "redirect:/login";
    }
        return "defaultPage";
}

    @GetMapping("main/{sheetId}")
    public String sheetDetail(@PathVariable Long sheetId, HttpServletRequest httpServletRequest, ModelMap map){
        HttpSession session = httpServletRequest.getSession();
        Long memberId = (Long)session.getAttribute("memberId");
        String userId = (String)session.getAttribute("userId");
        String userName = (String)session.getAttribute("userName");
        String profileImage = (String)session.getAttribute("profileImage");
        if(session.getAttribute("userId")==null){
            return "redirect:/login";
        }
        Map<Long, Map> boardList= boardApiLogicService.read(sheetId);
        List<Sheet> list = sheetApiLogicService.list(memberId);
        String sheetName = sheetRepository.findById(sheetId).get().getPlannerTitle();

        map.addAttribute("sheetName",sheetName);
        map.addAttribute("sheetId",sheetId);
        map.addAttribute("sheetList",list);
        map.addAttribute("userId",userId);
        map.addAttribute("memberId",memberId);
        map.addAttribute("userName",userName);
        map.addAttribute("profileImage",profileImage);
        map.addAttribute("boardList",boardList);
        return "content";
    }
    @GetMapping("logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }
}
