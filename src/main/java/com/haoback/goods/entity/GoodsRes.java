package com.haoback.goods.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 商品图片资源表
 * Created by nong on 2017/6/11.
 */
@Entity
@Table(name = "goods_res")
@Data
public class GoodsRes extends BaseEntity<Long> {

    private static final long serialVersionUID = -8062241732155988734L;

    /**
     * 所属商品
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="GOODS_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    private Goods goods;

    /**
     * 类型 thumbnail-缩略图 detail-详情图 carousel-轮播图
     */
    @Column(name = "TYPE", length = 20)
    private String type;

    /**
     * 图片文件名
     */
    @Column(name = "FILE_ID", length = 255)
    private String fileId;

    /**
     * 排序号，按数字顺序从小到大排序
     */
    @Column(name = "SORT", length = 1)
    private Integer sort;

    /**
     * 逻辑删除 false/0-未删除 true/1-删除
     */
    @Column(name = "IS_DELETED")
    private Boolean deleted = Boolean.FALSE;
}
