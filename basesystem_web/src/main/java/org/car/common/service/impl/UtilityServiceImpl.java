package org.car.common.service.impl;

import org.car.common.dao.IFieldExistDAO;
import org.car.common.model.FieldExist;
import org.car.common.service.IUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilityServiceImpl implements IUtilityService {
	@Autowired
	private IFieldExistDAO fieldExistDAO;

	@Override
	public int fieldExist(FieldExist field) {
		int f = fieldExistDAO.fieldExist(field);
		return f;
	}
}
