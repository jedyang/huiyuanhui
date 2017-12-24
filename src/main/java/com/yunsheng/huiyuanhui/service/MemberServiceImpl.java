package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.mapper.MemberMapper;
import com.yunsheng.huiyuanhui.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "memberService")
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public int insertRecord(Member member) {
        return memberMapper.insert(member);
    }
}
