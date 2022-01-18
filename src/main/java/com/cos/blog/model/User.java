package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴 
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.
// @DynamicInsert // insert시 null인 필드를 빼버린다.
public class User {

	@Id //Primary key로 지정한다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스, auto-increment로 증가 시킬 것
	
	@Column(nullable = false, length = 30, unique=true) // 컬럼의 조건을 정하는 것
	private String username; //아이디
	
	@Column(length = 100) //123456 => 해쉬로 변경 (비밀번호 암호화)
	private String password; //비번

	@Column(nullable = false, length = 50)
	private String email; 
	
	//@ColumnDefault("user")
	//DB는 RoleType이 없으니
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋다. 회원의 권한을 지칭하는 필드인데 
	                                     // Type을 Enum으로 사용하면 이 값을 admin, user, manager이 3가지로 정할 수 있음. 
                                        // 도메인 	
	@CreationTimestamp // 시간이 자동입력 된다. 테이블에 insert가 될 때 
	private Timestamp createDate;
 
}
