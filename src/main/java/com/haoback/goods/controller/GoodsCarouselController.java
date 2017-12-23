package com.haoback.goods.controller;

import com.haoback.common.entity.AjaxResult;
import com.haoback.goods.entity.GoodsCarousel;
import com.haoback.goods.service.GoodsCarouselService;
import com.haoback.goods.vo.GoodsCarouselVo;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮播图控制器
 */
@Controller
@RequestMapping("/carousel")
// 操作权限控制 *、create、update、delete、view
@PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsCarouselController', 'view')")
public class GoodsCarouselController {

    @Autowired
    private GoodsCarouselService goodsCarouselService;

    /**
     * 查询，不分页
     * @param deleted
     * @param isWxApp
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult search(Boolean deleted, Boolean isWxApp){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        Sort sort = new Sort(Sort.Direction.DESC, "deleted");

        // 查询条件
        Specification<GoodsCarousel> specification = (Root<GoodsCarousel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> condition = new ArrayList<>();
            if(deleted != null){
                Predicate deletedPredicate = cb.equal(root.get("deleted").as(Boolean.class), deleted);
                condition.add(deletedPredicate);
            }
            if(isWxApp != null){
                Predicate deletedPredicate = cb.equal(root.get("isWxApp").as(Boolean.class), isWxApp);
                condition.add(deletedPredicate);
            }

            return cb.and(condition.toArray(new Predicate[]{}));
        };

        List<GoodsCarousel> list = goodsCarouselService.findAll(specification, sort);

        datas.put("code", "1");
        datas.put("data", list);

        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 新增/更新
     * @param goodsCarouselVo
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasPermission('com.haoback.goods.controller.GoodsCarouselController', 'create,update')")
    public AjaxResult saveOrUpdate(GoodsCarouselVo goodsCarouselVo, HttpServletRequest request){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        String realPath = request.getSession().getServletContext().getRealPath("/");

        goodsCarouselService.saveOrUpdate(goodsCarouselVo, realPath);

        datas.put("code", "1");
        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 通过ID查找轮播图
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findById(Long id){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        GoodsCarousel goodsCarousel = goodsCarouselService.findById(id);
        datas.put("data", goodsCarousel);

        datas.put("code", "1");
        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }

    /**
     * 逻辑删除轮播图
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult delete(Long id){
        AjaxResult ajaxresult = new AjaxResult();
        Map<String, Object> datas = new HashMap<>();

        GoodsCarousel goodsCarousel = goodsCarouselService.findById(id);
        if(goodsCarousel != null){
            goodsCarousel.setDeleted(true);
            goodsCarouselService.update(goodsCarousel);
        }

        datas.put("code", "1");
        ajaxresult.setDatas(datas);
        ajaxresult.setStatus(HttpStatus.SC_OK);
        return ajaxresult;
    }
}
