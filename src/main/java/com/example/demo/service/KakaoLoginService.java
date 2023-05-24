package com.example.demo.service;

import com.example.demo.ifs.IKakaoLoginService;
import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.repository.MoneyMemberRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLoginService  implements IKakaoLoginService {

    private final MoneyMemberRepository moneyMemberRepository;

    @Override
    public String getAccessToken(String authorize_code) throws Exception {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");

            sb.append("&client_id=ee3dc4adc8c28899d3344a3b206f25b4"); // REST_API키 본인이 발급받은 key 넣어주기
            sb.append("&redirect_uri=http://13.124.130.62:7080/kakaoLogin"); // REDIRECT_URI 본인이 설정한 주소 넣어주기

            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            // System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            // System.out.println("response body : " + result);

            // jackson objectmapper 객체 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON String -> Map
            Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
            });

            access_Token = jsonMap.get("access_token").toString();
            refresh_Token = jsonMap.get("refresh_token").toString();

            // System.out.println("access_token : " + access_Token);
            // System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> getUserInfo(String access_Token) throws Throwable {
        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            // System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            // System.out.println("response body : " + result);
            // System.out.println("result type" + result.getClass().getName()); // java.lang.String

            try {
                // jackson objectmapper 객체 생성
                ObjectMapper objectMapper = new ObjectMapper();
                // JSON String -> Map
                Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
                });

                // System.out.println(jsonMap.get("properties"));

                Map<String, Object> properties = (Map<String, Object>) jsonMap.get("properties");
                Map<String, Object> kakao_account = (Map<String, Object>) jsonMap.get("kakao_account");

                // System.out.println(properties.get("nickname"));
                // System.out.println(kakao_account.get("email"));

                String nickname = properties.get("nickname").toString();
                String userId = jsonMap.get("id").toString(); //카카오로그인유저의 id로 사용할 예정
                String profileImage = properties.get("profile_image").toString();

                //이런 회원이 있는지 조회
                Optional<MoneyMember> mem = moneyMemberRepository.findByUserId(userId);

                if(mem.isEmpty()){ // 아이디가 DB에 존재하지않은 경우(즉, 회원이 아닐경우)
                    //회원등록시키고 멤버정보를 담아 보냄
                    MoneyMember moneyMember = MoneyMember.builder()
                            .userId(userId)
                            .userPw(userId+"kakao")
                            .userName(nickname)
                            .profileImage(profileImage)
                            .build();
                    MoneyMember newMoneyMember = moneyMemberRepository.save(moneyMember);
                    userInfo.put("memberId",newMoneyMember.getId());
                }else{ //아이디가 존재할경우 그대로 멤버정보를 담아 보냄
                    userInfo.put("memberId",mem.get().getId());
                }
                userInfo.put("userId",userId);
                userInfo.put("userName", nickname);
                userInfo.put("profileImage",profileImage);



            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

}
