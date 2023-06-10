package com.cafein.web.member.model;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	private int num;
	private String id;
	private String pw;
	private String name;
	private Date birthdate;
	private String gender;
}
