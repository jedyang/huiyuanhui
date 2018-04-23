package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.model.Member;

import java.util.List;

public interface MemberService {

    int insertRecord(Member member);

    List<Member> selectAllMember(Integer userId);

    Member findByOpenId(String openId);

    Member findByMemberId(String memberId);

    List<Member>  queryAllMembersOfShop(Integer shopId);

    int updateRecord(Member member);


}
