package com.sirius.robots.bo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/30
 */
@Data
public class Fields implements Serializable {

    private static final long serialVersionUID = -7050524686724901434L;
    public String name = null;
	public String type = null;
	public boolean primaryKey = false;
	public boolean autoIncrease = false;
	public String javaType = null;
	public String comment = null;

	public Fields(String name, String type, boolean primaryKey) {
		this.name = name;
		this.type = type;
		this.primaryKey = primaryKey;
	}

}