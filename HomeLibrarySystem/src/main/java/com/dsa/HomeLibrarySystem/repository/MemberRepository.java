package com.dsa.HomeLibrarySystem.repository;

import com.dsa.HomeLibrarySystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
