package org.car.common.model;

/**
 * 验证数据表中的数据值是否存在
 * @author songwangwen
 */
public class FieldExist {
	 private String tableName;//数据表名
     private String fieldValue;//比较列值
     private String keyField;	//主键值
     private String fieldName;		//比较列名字
     private String keyFieldName;	//主键列名字
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getKeyFieldName() {
		return keyFieldName;
	}
	public void setKeyFieldName(String keyFieldName) {
		this.keyFieldName = keyFieldName;
	}
}
