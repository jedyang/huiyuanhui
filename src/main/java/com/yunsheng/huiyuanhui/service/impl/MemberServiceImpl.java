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

    @Override
    public Member findByOpenId(String openId) {
        Member member = new Member();
        member.setOpenId(openId);
        List<Member> result = memberMapper.selectBySelective(member);
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Member findByMemberId(String memberId) {
        Member member = memberMapper.selectByPrimaryKey(Integer.parseInt(memberId));
        return member;
    }

    @Override
    public List<Member> queryAllMembersOfShop(Integer shopId) {
        List<Member> members = memberMapper.selectAllMembersOfShop(shopId);
        return members;
    }

    @Override
    public List<Member> queryAllMembersByShopAndKeyWord(Integer shopId, String keyword) {
        List<Member> members = memberMapper.queryAllMembersByShopAndKeyWord(shopId, keyword);
        return members;
    }

    @Override
    public int updateRecord(Member member) {
        int i = memberMapper.updateByPrimaryKeySelective(member);
        return i;
    }
}
