package com.haoback.goods.entity;

import com.haoback.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 商品类目表
 * Created by nong on 2017/3/21.
 */
@Entity
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "goods_type")
@Data
public class GoodsType extends BaseEntity<Long> {

    private static final long serialVersionUID = 5505345825445987906L;

    /**
     * 类目编码
     */
    @Column(name = "CODE", length = 25)
    private String code;

    /**
     * 类目名称
     */
    @Column(name = "NAME", length = 255)
    private String name;

    /**
     * 父类目编码，如果为根则为"root"
     */
    @Column(name = "ROOT_CODE", length = 25)
    private String rootCode;

}
