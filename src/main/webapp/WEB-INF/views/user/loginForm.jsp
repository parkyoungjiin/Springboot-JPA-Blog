<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 헤더 -->
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="#" method="post">
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" class="form-control" name="username" placeholder="Enter Username" id="username">
		</div>

		<div class="form-group">
			<label for="pwd">Password</label> 
			<input type="password" class="form-control" name="password" placeholder="Enter password" id="password">
		</div>
		
		<div class="form-group form-check">
			<label class="form-check-label">
				<input class="form-check-input" type="checkbox" name="remember"> Remember me
			</label>
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>
</div>

<script src="/js/user.js">

</script>
<!-- 푸터 -->
<%@ include file="../layout/footer.jsp"%>



