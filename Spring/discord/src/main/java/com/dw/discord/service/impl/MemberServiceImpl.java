package com.dw.discord.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dw.discord.dto.BaseResponse;
import com.dw.discord.dto.MemberDto;
import com.dw.discord.dto.MemberLogingDto;
import com.dw.discord.enumstatus.Gender;
import com.dw.discord.enumstatus.ResultCode;
import com.dw.discord.exception.InvalidRequestException;
import com.dw.discord.model.Member;
import com.dw.discord.repository.MemberRepository;
import com.dw.discord.service.MemberService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	//회원가입
	@Override
	public BaseResponse<Void> signUp(MemberDto memberDto) {
		Member member = memberRepository.findByLoginId(memberDto.getLoginId());
		if(member != null) {
			//예외상황
			throw new InvalidRequestException(memberDto.getLoginId(), "이미 등록된 아이디 입니다.");
		}
		member = new Member();
		member.setId(null);
		member.setLoginId(memberDto.getLoginId());
		member.setPassword(memberDto.getPassword());
		member.setBirthDate(LocalDate.parse(memberDto.getBirthDate(), 
				DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		member.setEmail(memberDto.getEmail());
		member.setGender(Gender.valueOf(memberDto.getGender()) );
		member.setName(memberDto.getName());
		
		memberRepository.save(member);
		return new BaseResponse<>(
					ResultCode.SUCCESS.name(),
					null,
					"회원가입이 완료되었습니다.");
		}
	//로그인
	@Override
	public BaseResponse<Void> login(MemberLogingDto memberLogingDto) {
		Member member = memberRepository.findByLoginId(memberLogingDto.getLoginId());
		if(member != null && 
				member.getPassword().matches(memberLogingDto.getPassword())) {return new BaseResponse<Void>
				(		ResultCode.SUCCESS.name(),
						null,
						"로그인이 완료되었습니다.");
			
		}else {
			throw new InvalidRequestException("loginID / Password", "ID 또는 PW가 일치하지 않습니다.");
		}
	}
	
}
