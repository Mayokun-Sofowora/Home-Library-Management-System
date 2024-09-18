package com.dsa.HomeLibrarySystem.controller;

import com.dsa.HomeLibrarySystem.model.Member;
import com.dsa.HomeLibrarySystem.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberService memberService;

    @Autowired
    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Member loginRequest, HttpSession session) {
        Optional<Member> member = memberService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        // Store the member in the session or any form of context if needed
        if (member.isPresent()) {
            session.setAttribute("currentUser", member.get());
            return new ResponseEntity<>(member.get(), HttpStatus.OK);
        }
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
    @GetMapping("/current-user")
    public ResponseEntity<Member> getCurrentUser(HttpSession session) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        if (currentUser != null) {
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
