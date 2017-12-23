package com.haoback.goods.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nong
 * @date 2017/6/21
 */
@Getter
@Setter
public class GoodsTypeVo implements Serializable {
    private static final long serialVersionUID = -2334930337337516853L;
    private Long id;
    private String code;
    private String name;
    private Integer sort;
    private String rootCode;
    private String icon;
    private String remark;

    private List<GoodsVo> goodsList;
}
