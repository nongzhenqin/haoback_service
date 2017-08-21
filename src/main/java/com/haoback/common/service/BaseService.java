package com.haoback.common.service;

import java.io.Serializable;
import java.util.List;

import com.haoback.common.entity.BaseEntity;
import com.haoback.common.repository.BaseRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 抽象service层基类 提供一些简便方法
 * 泛型 ： M 表示实体类型；ID表示主键类型
 * @author nongz
 *
 * @param <M>
 * @param <ID>
 */
@SuppressWarnings("rawtypes")
public abstract class BaseService<M extends BaseEntity, ID extends Serializable> {
	
	@Autowired
	private BaseRepository<M, ID> baseRepository;
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return
	 */
	public M findById(ID id){
		return baseRepository.findOne(id);
	}
	
	/**
	 * 保存
	 * @param m
	 */
	public void save(M m){
		baseRepository.save(m);
	}
	
	/**
	 * 更新
	 * @param m
	 */
	public void update(M m){
		baseRepository.save(m);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(ID id){
		baseRepository.delete(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(ID[] ids){
		if(ids != null && ids.length > 0){
			for(ID id : ids){
				this.delete(id);
			}
		}
	}
	
	/**
	 * 通过实体删除
	 * @param m
	 */
	public void delete(M m){
		baseRepository.delete(m);
	}
	
	/**
	 * 通过实体批量删除
	 * @param ms
	 */
	public void delete(List<M> ms){
		if(CollectionUtils.isNotEmpty(ms)){
			for(M m : ms){
				this.delete(m);
			}
		}
	}
	
	/**
	 * 查询所有
	 */
	public List<M> findAll(){
		return baseRepository.findAll();
	}
	
	/**
	 * 查询所有并排序
	 */
	public List<M> findAll(Sort sort){
		return baseRepository.findAll(sort);
	}

	/**
	 * 根据条件查找
	 * @param specification
	 * @return
	 */
	public List<M> findAll(Specification<M> specification){
		return baseRepository.findAll(specification);
	}

	/**
	 * 根据条件查找并排序
	 * @param specification
	 * @param sort 可为null
	 * @return
	 */
	public List<M> findAll(Specification<M> specification, Sort sort){
		return baseRepository.findAll(specification, sort);
	}
	
	/**
	 * 分页
	 * @param pageable
	 * @return
	 */
	public Page<M> findByPage(Specification<M> specification, Pageable pageable){
		return baseRepository.findAll(specification, pageable);
	}
}
