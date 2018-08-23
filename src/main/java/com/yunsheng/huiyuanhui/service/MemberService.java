package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.model.Member;

import java.util.List;

public interface MemberService {

    int insertRecord(Member member);

    List<Member> selectAllMember(Integer userId);

    Member findByOpenId(String openId);

    Member findByMemberId(String memberId);

    List<Member>  queryAllMembersOfShop(Integer shopId);

    // 查询该店铺下所有会员，且加上关键字过滤
    List<Member>  queryAllMembersByShopAndKeyWord(Integer shopId, String keyword);

    int updateRecord(Member member);


}
