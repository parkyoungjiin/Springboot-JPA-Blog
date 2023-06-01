<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
		<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
		<c:if test="${board.user.id == principal.user.id }">
			<a class="btn btn-warning" href="/board/${board.id }/updateForm">수정</a>
			<button class="btn btn-danger" id="btn-delete">삭제</button>
		</c:if>
		<hr>
		<div>
			글번호 : <span id="board_id"><i>${board.id } </i></span>
			,
			작성자 : <span id="board_username"><i>${board.user.username } </i></span>
			<hr>
		</div>
		<div class="form-group">
			<label for="title">Title</label> 
			<h3>${board.title }</h3>
			<hr>
		</div>
				
		<div class="form-group ">
			<label for="content">Content</label>
			<div>
				${board.content }
			</div>
		</div>
		
</div>

<script>
	$('.summernote').summernote({
		placeholder : 'Hello Bootstrap 4',
		tabsize : 2,
		height : 300
	});
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>



