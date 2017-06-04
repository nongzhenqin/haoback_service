package com.haoback.common.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 抽象DAO层基类 提供一些简便方法
 * @author nongz
 *
 * @param <M>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseRepository<M, ID extends Serializable> extends JpaRepository<M, ID> {
	/**
	 * 通过ID查找
	 * @param id
	 * @return
	 */
	public M findById(ID id);
	
}
