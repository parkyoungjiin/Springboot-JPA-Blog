let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{ //function을 사용하지 않는 이유 : this를 바인딩 하기 위함.
            this.save();
        });
        $("#btn-login").on("click", ()=>{ //function을 사용하지 않는 이유 : this를 바인딩 하기 위함.
            this.login();
        });
        $("#btn-update").on("click", ()=>{ //function을 사용하지 않는 이유 : this를 바인딩 하기 위함.
            this.update();
        });
    },
//---------save 함수(회원가입 로직)-------------
    save:function(){
        let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
			
		};
		
		//console.log(data)
		//ajax 통신을 이용하여 3개의 데이터를 json으로 변경하여 insert
		//ajax가 통신 성공하고, 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환.
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			//stringify : 자바스크립트 오브젝트를 JSON으로 변경.
			data: JSON.stringify(data), //HTTP Body 데이터 -> MIME 타입 설정이 필요함.
			//contentType: body 데이터가 어떤 타입인지 설정.
			contentType: "application/json; charset=utf-8",
			//dataType : 응답 데이터를 어떻게 받을 지 설정.
			dataType: "json" // 응답 
		}).done(function(resp){
			if(resp.status == 500){
				alert("회원가입에 실패했습니다.");
				console.log(resp)
				
			}else{
				alert("회원가입에 성공했습니다.");
				console.log(resp)
				location.href = "/";

			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
    },// 함수 끝
   
////---------login 함수(로그인 로직)-------------
//     login:function(){
//        let data = {
//			username: $("#username").val(),
//			password: $("#password").val(),
//		};
//		
//		//console.log(data)
//		//ajax 통신을 이용하여 3개의 데이터를 json으로 변경하여 insert
//		//ajax가 통신 성공하고, 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환.
//		$.ajax({
//			type: "POST",
//			url: "/api/user/login",
//			//stringify : 자바스크립트 오브젝트를 JSON으로 변경.
//			data: JSON.stringify(data), //HTTP Body 데이터 -> MIME 타입 설정이 필요함.
//			//contentType: body 데이터가 어떤 타입인지 설정.
//			contentType: "application/json; charset=utf-8",
//			//dataType : 응답 데이터를 어떻게 받을 지 설정.
//			dataType: "json" // 응답 
//		}).done(function(resp){
//			alert("로그인이 완료되었습니다.");
////			console.log(resp)
//			location.href = "/";
//		}).fail(function(error){
//			alert(JSON.stringify(error));
//			
//		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
//    }
//---------update 함수(로그인 로직)-------------
     update:function(){
        let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
			
		};
		
		console.log(data)
		//ajax 통신을 이용하여 3개의 데이터를 json으로 변경하여 insert
		//ajax가 통신 성공하고, 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환.
		$.ajax({
			type: "PUT",
			url: "/api/user/update",
			//stringify : 자바스크립트 오브젝트를 JSON으로 변경.
			data: JSON.stringify(data), //HTTP Body 데이터 -> MIME 타입 설정이 필요함.
			//contentType: body 데이터가 어떤 타입인지 설정.
			contentType: "application/json; charset=utf-8",
			//dataType : 응답 데이터를 어떻게 받을 지 설정.
			dataType: "json" // 응답 
		}).done(function(resp){
			alert("회원정보 수정이 완료되었습니다.");
//			console.log(resp)
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		}); // ajax 통신을 이용해서 3개의 파라미터를 json으로 변경하여 insert 요청 
    }
    
}

index.init();