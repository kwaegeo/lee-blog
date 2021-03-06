package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴 
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터 
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이 된다.
	
	private int count; //조회수
	
	@ManyToOne(fetch=FetchType.EAGER) //Many = Board, User = One   (한명의 유저는 여러개의 게시물을 작성 가능)
	@JoinColumn(name="userId")
	private User user; //DB는 오브젝트를 저장할 수 없기 때문에 FK를 사용하는데, 자바는 오브젝트 저장가능
	                                   //필드 이름은 userId이고, 유저객체를 참조하기 때문에 자동으로 foreign key가 생성됨
	
	@OneToMany(mappedBy ="board",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //mappedBy 연관관계의 주인이 아니다 (난FK가 아니에요) DB에 칼럼을 만들지 마세요.
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate; //데이터가 추가 될때 자동으로 시간이 들어간다.
	
}
