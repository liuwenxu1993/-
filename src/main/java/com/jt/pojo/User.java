package com.jt.pojo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class User {
	
	private Integer id;
	private String name;
	private String phone;
	private String pwd;
	
}
