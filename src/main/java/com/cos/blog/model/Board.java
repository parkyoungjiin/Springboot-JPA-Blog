package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	
	private int count; // 조회
	
	@ManyToOne(fetch = FetchType.EAGER) // Board = Many, User = One
	// 필드명은 userId로 만들어지고, 연관 관계는 user와 연관관계로 FK 생성된다.
	@JoinColumn(name = "userId")
	private User user; // DB 오브젝트를 저장할 수 없다 <-> FK, 자바는 오브젝트를 저장할 수 있다.
	
	//JPA에서는 객체를 저장할 수 있다. @JoinColumn(name="컬럼명") 으로 어노ㅔ이션을 해줘야 함.
	//원리 : FK로 생성된다. (연관관계 맺어서 생성)
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) // JoinColumn 필요 X : Board에는 FK가 필요없음.
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	//OneToMany는 select할 때 reply도 Join을 해야 한다는 의미로 작성함.
	//mappedBy는 연관관계의 주인이 아니다. (FK가 아니기에, 컬럼을 만들지말라는 의미다.)
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
