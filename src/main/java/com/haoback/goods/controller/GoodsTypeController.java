package com.haoback.goods.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.goods.entity.GoodsType;
import com.haoback.goods.service.GoodsTypeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品类型控制器
 */
@Controller
@RequestMapping("/goods_type")
// 操作权限控制 *、create、update、delete、view
@PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsTypeController', 'view')")
public class GoodsTypeController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    /**
     * 分页查询商品类型
     * @param goodsType
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findByPage(GoodsType goodsType, Integer pageNo, Integer pageSize){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        // 分页对象
        Pageable pageable = new PageRequest(pageNo, pageSize);

        // 查询条件
        Specification<GoodsType> specification = (Root<GoodsType> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> where = new ArrayList<>();

            if(StringUtils.isNotBlank(goodsType.getName())){
                Predicate namePre = cb.like(root.get("name").as(String.class), "%"+goodsType.getName()+"%");
                where.add(namePre);
            }

            if(goodsType.getDeleted() != null){
                Predicate deletedPre = cb.equal(root.get("deleted").as(Boolean.class), goodsType.getDeleted());
                where.add(deletedPre);
            }

            return CollectionUtils.isNotEmpty(where) ? cb.and(where.toArray(new Predicate[]{})) : null;
        };

        Page<GoodsType> page = goodsTypeService.findByPage(specification, pageable);

        datas.put("code", "1");
        datas.put("data", page);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 查询类目详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findDetails(Long id){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        GoodsType goodsType = null;
        if(id != null){
            goodsType = goodsTypeService.findById(id);
        }

        datas.put("code", "1");
        datas.put("data", goodsType);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 新增/更新
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsTypeController', 'create,update')")
    public AjaxResult saveOrUpdate(GoodsType goodsType){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        if(goodsType.getId() == null){// 新增
            goodsTypeService.save(goodsType);
        }else{// 更新
            goodsTypeService.update(goodsType);
        }

        datas.put("code", "1");

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 删除类目
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsTypeController', 'delete')")
    public AjaxResult delete(Long id){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        goodsTypeService.logicDelete(id);

        datas.put("code", "1");

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}
