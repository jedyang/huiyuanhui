package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.MemberMapper;
import com.yunsheng.huiyuanhui.model.Member;
import com.yunsheng.huiyuanhui.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public int insertRecord(Member member) {
        return memberMapper.insert(member);
    }

    @Override
    public List<Member> selectAllMember(Integer userId) {
        return null;
    }
}
