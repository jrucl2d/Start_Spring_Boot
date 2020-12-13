package com.yu.project09.repository;

import com.yu.project09.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {
}
