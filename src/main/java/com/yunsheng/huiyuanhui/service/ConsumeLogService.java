package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.model.ConsumeLog;
import com.yunsheng.huiyuanhui.util.PageRequest;

import java.util.List;

public interface ConsumeLogService {
    int insertRecord(ConsumeLog consumeLog);

    PageRequest<ConsumeLog> queryRecordsByPage(ConsumeLog consumeLog, PageRequest<ConsumeLog> pageRequest);
}
