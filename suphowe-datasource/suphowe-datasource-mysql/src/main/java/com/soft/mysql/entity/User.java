package com.soft.mysql.entity;

import lombok.Data;

/**
 * 用户实体类
 * @author suphowe
 */
@Data
public class User {

	Integer id;
	
	String usercode;
	
	String username;

	String usertype;

	String orgcode;

	String orgname;

	String corpcode;

	String corname;

	String deptcode;

	String deptname;

	String email;

	String sex;

	String cdsid;

	String supervisorid;

	String managerlevel;

	String descrcn;

	String descren;

	String emplRcd;

	String nationalid;

	String positionnbr;

	String lastHireDt;

	String emplClass;

	String deptid;

	String hrStatus;
}
