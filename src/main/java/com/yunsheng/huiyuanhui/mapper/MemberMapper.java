package com.yunsheng.huiyuanhui.mapper;

import com.yunsheng.huiyuanhui.model.Member;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("memberMapper")
public interface MemberMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer memberId);

    List<Member> selectBySelective(Member record);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    List<Member> selectAllMembersOfShop(Integer shopId);
}