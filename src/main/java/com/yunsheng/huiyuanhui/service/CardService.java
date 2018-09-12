package com.yunsheng.huiyuanhui.service;

import com.yunsheng.huiyuanhui.model.HyCards;
import com.yunsheng.huiyuanhui.util.PageRequest;

@Deprecated
public interface CardService {
    int insertRecord(HyCards hyCards);

    PageRequest<HyCards> queryRecordsByPage(HyCards hyCards, PageRequest<HyCards> pageRequest);
}
