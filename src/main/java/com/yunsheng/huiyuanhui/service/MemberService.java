package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.model.Member;

import java.util.List;

public interface MemberService {

    int insertRecord(Member member);

    List<Member> selectAllMember(Integer userId);

    Member findByOpenId(String openId);

}
