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
		}
		
		// console.log(data);
		
		// ajax호출시 default가 비동기 호출임.
		 // ajax통신을 이용해서 3개의 데이터를 json으로 변경해서 insert요청을함!!
		$.ajax({
			type: "POST",
			url:"/api/user"
		}).done(function(){
		  // 정상이면 done의 함수 실행
		}).fail(function(){
		 // 실패면 FAIL의 함수실행
		});
	}
}

index.init();

