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
		
			<h3>${board.title }</h3>
			<hr />
				
			<div>
				${board.content }
			</div>
			<hr />
			
			<!-- 답글 -->
	<div class="card">
		<form action="">
		<!-- 답글 작성 시 게시글 번호, 내용이 필요하기에 hidden으로 boardId를 넘긴다. -->
			<input type="hidden" id="boardId" value="${board.id }">
			<input type="hidden" id="userId" value="${principal.user.id }">
			<div class="card-body">
				<textarea id="reply-content" rows="1" class="form-control"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
	</div>

	<br>
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply--box" class="list-group">
			<c:forEach var="reply" items="${board.reply }">
				<li id="reply--1" class="list-group-item d-flex justify-content-between">
					<div>${reply.content }</div>
					<div class="d-flex">
						<div class="font-italic">작성자 : ${reply.user.username }&nbsp;</div>
						<button class="badge">삭제</button>
					</div>
				</li>
			</c:forEach>
		</ul>
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



