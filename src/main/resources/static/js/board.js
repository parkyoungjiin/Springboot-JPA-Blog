let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{ // save : 게시글 저장
            this.save();
        });
        $("#btn-delete").on("click", ()=>{ // deleteById : 게시글 삭제
            this.deleteById();
        });
        $("#btn-update").on("click", ()=>{ // update : 게시글 수정 
            this.update();
        });
        $("#btn-reply-save").on("click", ()=>{ //replysave : 답글 작성
            this.replySave();
        });
   
    },
//---------save 함수(글쓰기 로직)-------------
    save:function(){
        let data = {
			title: $("#title").val(),
			content: $("#content").val(),
			
		};
			
		$.ajax({
			type: "POST",
			url: "/api/board",
			//stringify : 자바스크립트 오브젝트를 JSON으로 변경.
			data: JSON.stringify(data), //HTTP Body 데이터 -> MIME 타입 설정이 필요함.
			//contentType: body 데이터가 어떤 타입인지 설정.
			contentType: "application/json; charset=utf-8",
			//dataType : 응답 데이터를 어떻게 받을 지 설정.
			dataType: "json" // 응답 
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
//			console.log(resp)
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
    },// 함수 끝
    
//---------deleteById 함수(글쓰기 삭제 로직)-------------
    deleteById:function(){
		let id = $("#board_id").text();
//		var username = $("#board_username").val();
//        let data = {
//			title: $("#title").val(),
//			content: $("#content").val(),
//			
//		};
			
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id,
			//dataType : 응답 데이터를 어떻게 받을 지 설정.
			dataType: "json" // 응답 
		}).done(function(resp){
			alert("글 삭제가 완료되었습니다.");
//			console.log(resp)
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
    },// 함수 끝
    
//---------update 함수(글쓰기 삭제 로직)-------------
    update:function(){
		let id = $("#id").val();
        let data = {
			title: $("#title").val(),
			content: $("#content").val(),
			
		};
			
		$.ajax({
			type: "PUT",
			url: "/api/board/"+id,
			//stringify : 자바스크립트 오브젝트를 JSON으로 변경.
			data: JSON.stringify(data), //HTTP Body 데이터 -> MIME 타입 설정이 필요함.
			//contentType: body 데이터가 어떤 타입인지 설정.
			contentType: "application/json; charset=utf-8",
			//dataType : 응답 데이터를 어떻게 받을 지 설정.
			dataType: "json" // 응답 
		}).done(function(resp){
			alert("글 수정이 완료되었습니다.");
//			console.log(resp)
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
    },// 함수 끝
    
    //---------replysave 함수(답글 작성 로직)-------------
    replySave:function(){
        let data = {
			boardId: $("#boardId").val(),
			userId: $("#userId").val(),
			content: $("#reply-content").val()
		};
		
		console.log(data)
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`, //`` 백틱을 사용한 코드임. (문자열, 변수를 같이 사용할 수 있다.)
			//stringify : 자바스크립트 오브젝트를 JSON으로 변경.
			data: JSON.stringify(data), //HTTP Body 데이터 -> MIME 타입 설정이 필요함.
			//contentType: body 데이터가 어떤 타입인지 설정.
			contentType: "application/json; charset=utf-8",
			//dataType : 응답 데이터를 어떻게 받을 지 설정.
			dataType: "json" // 응답 
		}).done(function(resp){
			alert("댓글작성이 완료되었습니다.");
//			console.log(resp)
			location.href = `/board/${data.boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
    },// 함수 끝
    
    //---------replyDelete 함수(답글 삭제 로직)-------------
    replyDelete:function(boardId, replyId){
		
		console.log(boardId, replyId)
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`, //`` 백틱을 사용한 코드임. (문자열, 변수를 같이 사용할 수 있다.)
			//dataType : 응답 데이터를 어떻게 받을 지 설정.
			dataType: "json" // 응답 
		}).done(function(resp){
			alert("댓글작성이 완료되었습니다.");
			console.log(resp)
			location.href = `/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
    },// 함수 끝
    
 
}//index 끝

index.init();