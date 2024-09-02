package com.dsa.HomeLibrarySystem.service;

import com.dsa.HomeLibrarySystem.model.Member;
import com.dsa.HomeLibrarySystem.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> authenticate(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member saveOrUpdateMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
