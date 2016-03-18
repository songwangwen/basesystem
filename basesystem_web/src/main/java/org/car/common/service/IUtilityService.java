package org.car.common.service;

import org.car.common.model.FieldExist;

public interface IUtilityService {
	
	/**
	 * 验证某个字段是否已存在
	 * @param field 参数
	 * @return 0.表示不存在   大于0表示已存在
	 * @see FieldExist
	 */
	public int fieldExist(FieldExist field);
}
