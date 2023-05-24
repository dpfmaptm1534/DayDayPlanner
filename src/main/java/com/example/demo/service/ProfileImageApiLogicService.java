package com.example.demo.service;

import com.example.demo.model.entity.MoneyMember;
import com.example.demo.model.entity.ProfileImage;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.request.ProfileImageApiRequest;
import com.example.demo.model.network.request.RegisterApiRequest;
import com.example.demo.model.network.response.ProfileImageApiResponse;
import com.example.demo.model.network.response.RegisterApiResponse;
import com.example.demo.model.repository.MoneyMemberRepository;
import com.example.demo.model.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileImageApiLogicService{

    private final ProfileImageRepository profileImageRepository;
    private final MoneyMemberRepository moneyMemberRepository;

    public Header<ProfileImageApiResponse> response(ProfileImage profileImage){
        ProfileImageApiResponse profileImageApiResponse = ProfileImageApiResponse.builder()
                .profileImageDirectory(profileImage.getProfileImageDirectory())
                .member(profileImage.getMember())
                .id(profileImage.getId())
                .build();
        return Header.OK(profileImageApiResponse);
    }

    public String searchid(Long memberId){
        // System.out.println("서비스단 userid:"+userid);
        Optional<ProfileImage> profile = profileImageRepository.findByMemberId(memberId);
        if(profile.isPresent()){
            return profile.get().getProfileImageDirectory();
        }else{
            return null;
        }
    }
    public String setDefault(Long memberId){
        MoneyMember moneyMember = moneyMemberRepository.findById(memberId).get();
        moneyMember.setProfileImage("/image/index/defaultProfileImg.jpeg");
        moneyMemberRepository.save(moneyMember);
        return "true";
    }
    public String setMyProfile(Long memberId){
        Optional<ProfileImage> profile = profileImageRepository.findByMemberId(memberId);
        MoneyMember moneyMember = moneyMemberRepository.findById(memberId).get();
        moneyMember.setProfileImage(profile.get().getProfileImageDirectory());
        moneyMemberRepository.save(moneyMember);
        return profile.get().getProfileImageDirectory();
    }
    public Header<ProfileImageApiResponse> create(MultipartFile file,Long memberId) throws IOException {
        String fileExtension = file.getOriginalFilename().split("[.]")[1];
        String pdir = "profile";
        String dir = "/home/ubuntu/";
        String sdir = dir+pdir;
        String timeInfo = "-" + LocalDateTime.now().toString();

        String serverPath=null;
        String fullPath=null;
        if (!file.isEmpty()) {
            serverPath = pdir+"/" + memberId+timeInfo+"."+fileExtension;
            fullPath = sdir + "/" + memberId+timeInfo+"."+fileExtension;

        }
        MoneyMember moneyMember = moneyMemberRepository.findById(memberId).get();
        moneyMember.setProfileImage(serverPath);
        moneyMemberRepository.save(moneyMember);

        Optional<ProfileImage> optProfileImage = profileImageRepository.findByMemberId(memberId);
        ProfileImage profileImage = null;
        if(optProfileImage.isPresent()){
            File originfile = new File(dir+optProfileImage.get().getProfileImageDirectory());
            boolean fileDeleted = originfile.delete();
            System.out.println(fileDeleted);
            file.transferTo(new File(fullPath));
            profileImage = optProfileImage.get();
            profileImage.setProfileImageDirectory(serverPath);
            profileImageRepository.save(profileImage);
            System.out.println("수정함");
        }else{
            file.transferTo(new File(fullPath));
            profileImage= ProfileImage.builder()
                    .profileImageDirectory(serverPath)
                    .build();
            profileImage.setMember(moneyMember);
            profileImageRepository.save(profileImage);
            System.out.println("새로등록함");
        }


        return response(profileImage);
    }

}
