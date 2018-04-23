package com.yunsheng.huiyuanhui.service.impl;

import com.yunsheng.huiyuanhui.mapper.ConsumeLogMapper;
import com.yunsheng.huiyuanhui.model.ConsumeLog;
import com.yunsheng.huiyuanhui.service.ConsumeLogService;
import com.yunsheng.huiyuanhui.util.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("consumeLogService")
public class ConsumeLogServiceImpl implements ConsumeLogService {

    @Autowired
    private ConsumeLogMapper consumeLogMapper;

    @Override
    public int insertRecord(ConsumeLog consumeLog) {
        int i = consumeLogMapper.insertSelective(consumeLog);
        return i;
    }

    @Override
    public PageRequest<ConsumeLog> queryRecordsByPage(ConsumeLog consumeLog, PageRequest<ConsumeLog> pageRequest) {
        List<ConsumeLog> consumeLogs = consumeLogMapper.selectBySelective(consumeLog, pageRequest);

        return pageRequest.data(consumeLogs);
    }

}
