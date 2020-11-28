package com.yu.project04.repository;

import com.yu.project04.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, String> {

    @Query("SELECT m.uid, count(p) FROM Member m LEFT OUTER JOIN Profile p " +
            "ON m.uid = p.member WHERE m.uid = ?1 GROUP BY m") // 선택적으로 가져올 때는 Object[]를 사용했음
    public List<Object[]> getMemberWithProfileCount(String uid);  // List의 결과는 Row 수, Object[]는 컬럼을 나타냄

    @Query("SELECT m, p FROM Member m LEFT OUTER JOIN Profile p " +
            "ON m.uid = p.member WHERE m.uid= ?1 AND p.current = true")
    public List<Object[]> getMemberWithProfile(String uid);
}
