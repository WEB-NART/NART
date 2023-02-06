package com.nart.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageVo {
    private int pageSize;
    private int pageNum;

    public PageVo(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public <T> IPage<T> toIPage(Class<T> type) {
        return new Page<T>(this.pageNum, this.pageSize);
    }
}
