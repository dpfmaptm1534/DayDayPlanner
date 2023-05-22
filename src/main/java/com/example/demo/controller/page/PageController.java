package com.example.demo.controller.page;

import com.example.demo.model.entity.MoneyBoard;
import com.example.demo.model.entity.Sheet;
import com.example.demo.model.repository.SheetRepository;
import com.example.demo.service.BoardApiLogicService;
import com.example.demo.service.KakaoLoginService;
import com.example.demo.service.SheetApiLogicService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class PageController {
    private final SheetApiLogicService sheetApiLogicService;
    private final BoardApiLogicService boardApiLogicService;
    private final SheetRepository sheetRepository;
    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("userId")!=null){
            return "redirect:/main";
        }
        return "login";
    }
    @GetMapping("login")
    public String login(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute("userId")!=null){
            return "redirect:/main";
        }
        return "login";
    }

    @GetMapping("regist")
    public String register() {
        return "register";
    }

    @GetMapping("kakaoLogin")
    public String main(HttpServletRequest httpServletRequest, ModelMap map,@RequestParam(value = "code", required = false) String code){
        HttpSession session = httpServletRequest.getSession();
        HashMap<String, Object> userInfo = new HashMap<>();
        String tk="";
        try{
            // System.out.println("1kakaoLoginService.getAccessToken(code);");
            tk = kakaoLoginService.getAccessToken(code);
        }catch (Exception e){
        }
        try {
            // System.out.println("2 kakaoLoginService.getUserInfo(tk)");
            userInfo = kakaoLoginService.getUserInfo(tk);
        }
        catch (Throwable e) {}
        // System.out.println("userId는 " + userInfo.get("userId"));
        // System.out.println("memberId는" + userInfo.get("memberId"));
        //카카오아디디로 이미 회원가입한 기록이있을경우
        if(userInfo.get("userId")!=null){
            session.setAttribute("accessToken",tk);
            session.setAttribute("loginType","kakao");
            session.setAttribute("memberId",userInfo.get("memberId"));
            session.setAttribute("userId",userInfo.get("userId"));
            session.setAttribute("userName",userInfo.get("userName"));
            session.setAttribute("profileImage",userInfo.get("profileImage"));
        }
        return "redirect:/main";
    }

    @GetMapping("main")
    public String main(HttpServletRequest httpServletRequest, ModelMap map) {
        // System.out.println("main 실행합니다");
        HttpSession session = httpServletRequest.getSession();
        // System.out.println("현재 세션에 등록된 Memeberid : " + (Long)session.getAttribute("memberId"));
        // System.out.println("세션에 등록된 profile이미지 : " + (String)session.getAttribute("profileImage"));
        Long memberId = (Long)session.getAttribute("memberId");
        String userId = (String)session.getAttribute("userId");
        String userName = (String)session.getAttribute("userName");
        String profileImage = (String)session.getAttribute("profileImage");
        String accessToken = (String)session.getAttribute("accessToken");
        if(session.getAttribute("userId")==null){
            return "redirect:/login";
        }
        // System.out.println("pagecontroller sheetapilogicservic.list start");
        List<Sheet> list = sheetApiLogicService.list(memberId);
        Long sheetId;
        String sheetName;

        try {
            sheetId = list.get(0).getId();
        }catch (IndexOutOfBoundsException e){
            sheetId = null;
        }
        // System.out.println("pagecontroller boardApiLogicService.read start");
        Map<Long, Map> boardList= boardApiLogicService.read(sheetId);

        try{
            sheetName = list.get(0).getPlannerTitle();
        }catch(IndexOutOfBoundsException e){
            sheetName=null;
        }
        System.out.println("토큰"+accessToken);
        map.addAttribute("sheetName",sheetName);
        map.addAttribute("sheetId",sheetId);
        map.addAttribute("sheetList",list);
        map.addAttribute("userId",userId);
        map.addAttribute("memberId",memberId);
        map.addAttribute("userName",userName);
        map.addAttribute("profileImage",profileImage);
        map.addAttribute("boardList",boardList);
        map.addAttribute("accessToken",accessToken);

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
        String accessToken = (String)session.getAttribute("accessToken");
        System.out.println("accessToken : " + accessToken);
        if(accessToken!=null){
            System.out.println("비어있지않아요 카카오로그아웃할게요");
            String reqURL = "https://kapi.kakao.com/v1/user/logout";
            try {
                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);

                int responseCode = conn.getResponseCode();
                System.out.println("responseCode : " + responseCode);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String result = "";
                String line = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }
                System.out.println(result);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("myinfo")
    public String myinfo(HttpServletRequest request, ModelMap map){
        HttpSession session = request.getSession();
        if(session.getAttribute("userId")==null){
            return "redirect:/login";
        }
        String accessToken = (String)session.getAttribute("accessToken");
        String loginType = (String)session.getAttribute("loginType");
        String profileImage = (String)session.getAttribute("profileImage");
        String userId = (String)session.getAttribute("userId");
        String userName = (String)session.getAttribute("userName");

        map.addAttribute("loginType",loginType);
        map.addAttribute("profileImage",profileImage);
        map.addAttribute("userId",userId);
        map.addAttribute("userName",userName);
    return "myinfo";
    }
    @GetMapping("myinfo/pwchange")
    public String pwchange(HttpServletRequest request, ModelMap map){
        HttpSession session = request.getSession();
        if(session.getAttribute("userId")==null){
            return "redirect:/login";
        }
        String accessToken = (String)session.getAttribute("accessToken");
        String loginType = (String)session.getAttribute("loginType");
        String profileImage = (String)session.getAttribute("profileImage");
        String userId = (String)session.getAttribute("userId");
        String userName = (String)session.getAttribute("userName");

        map.addAttribute("loginType",loginType);
        map.addAttribute("profileImage",profileImage);
        map.addAttribute("userId",userId);
        map.addAttribute("userName",userName);
        return "pwchange";
    }


}
