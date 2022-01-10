let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{ // function(){}, () => {} 이렇게 하는 이유는
											// this를 바인딩 하기 위해서 사용함.
			this.save();
		});
	
	},
	
	save: function(){
		// alert("user의 save함수 호출됨");
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		// console.log(data)
		// ajax호출시 default가 비동기 호출임.
		 // ajax통신을 이용해서 3개의 데이터를 json으로 변경해서 insert요청을함!!
		//ajax가 통신을 성공하고 서버가 json을 return 해주면 자동으로 자바 오브젝트로 변환해줌.
		
		$.ajax({
			type: "POST",
			url:"/blog/api/user",
			data: JSON.stringify(data), // http의 body 데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지 MIME명시	
			dataType: "json"// 요청을 서버로해서 응답이 왔을 때 기본적으로 모든것이 버퍼로오기때문에 String인데
							// (생긴게 Json이라면 => javascript오브젝트로 변경해줌
		}).done(function(resp){
		  // 정상이면 done의 함수 실행
			alert("회원가입이 완료되었습니다.");
			//console.log(resp);
			location.href="/blog";
		}).fail(function(error){
		 // 실패면 FAIL의 함수실행
			alert(JSON.stringify(error));
		});
		
	}
}

index.init();

