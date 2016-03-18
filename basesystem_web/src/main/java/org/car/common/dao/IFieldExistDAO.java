package org.car.common.dao;

import org.car.common.model.FieldExist;
import org.springframework.stereotype.Repository;

/**
 * 验证某个字段是否已存在
 * @author songwangwen
 *
 */
@Repository
public interface IFieldExistDAO {
	/**
	 * 验证某个字段是否已存在
	 * @param field 参数
	 * @return 大于0，存在
	 *          等于0 不存在
	 * @see FieldExist
	 */
	public int fieldExist(FieldExist field);
}
