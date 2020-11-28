package com.yu.project04;

import com.yu.project04.domain.Member;
import com.yu.project04.domain.Profile;
import com.yu.project04.repository.MemberRepository;
import com.yu.project04.repository.ProfileRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit // 테스트 결과 commit
public class ProfileTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void insertMembersTest(){
        IntStream.range(1, 101).forEach(i -> {
            Member member = new Member();
            member.setUid("user" + i);
            member.setUpw("pw" + i);
            member.setUname("사용자" + i);
            memberRepository.save(member);
        });
    }

    @Test
    public void insertProfileTest(){
        Member member = new Member(); // Member의 PK인 uid만 필요하므로 임시 생성
        member.setUid("user1");

        for(int i = 1; i < 5; i++){
            Profile profile = new Profile();
            profile.setFname("face" + i + ".jpg");

            if(i == 1){
                profile.setCurrent(true);
            }
            profile.setMember(member);
            profileRepository.save(profile);
        }
    }

    @Test
    public void testFetchJoin1(){
        List<Object[]> result = memberRepository.getMemberWithProfileCount("user1");

        result.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void testFetchJoin2(){
        List<Object[]> result = memberRepository.getMemberWithProfile("user1");

        result.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }
}
