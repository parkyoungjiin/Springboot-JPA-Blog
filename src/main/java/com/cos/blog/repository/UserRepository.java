package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;
//Integer = User테이블의 PK가 Integer이다.
//-------JpaRepository 특징 ------------
// CRUD의 함수를 대부분 갖고 있기에 extends를 통해 interface를 생성해주면 별도의 함수 생성 없이도 사용 가능하다.
// - DAO(jsp) 역할
// - @Repository는 생략 가능하다.

//-------JpaRepository 함수 종류------------
//save  => insert, update를 동시에 할 수 있음.
//findAll
//Delete
//Count
public interface UserRepository extends JpaRepository<User, Integer>{

}
