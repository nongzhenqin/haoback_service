package com.haoback.goods.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nong on 2017/6/21.
 */
@Getter
@Setter
public class GoodsTypeVo implements Serializable {
    private Long id;
    private String code;
    private String name;
    private String rootCode;
    private String remark;

    private List<GoodsVo> goodsList;
}
