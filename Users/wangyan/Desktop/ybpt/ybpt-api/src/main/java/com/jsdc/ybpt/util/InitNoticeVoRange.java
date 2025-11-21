package com.jsdc.ybpt.util;

import com.jsdc.ybpt.vo.notice.NoticeVo;

import java.util.ArrayList;

public class InitNoticeVoRange {
    public static void init(NoticeVo vo) {
        if (vo.getRangeList().size() > 0 ) {
            ArrayList<String> rangeList = vo.getRangeList();
            String s = rangeList.toString().replace("[", "").replace("]", "").replace(" ", "");
            vo.setRange(s);
        }
    }
}
