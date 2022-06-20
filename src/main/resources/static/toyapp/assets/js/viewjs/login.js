
var confirmIdPw = 'N';
var restApiKey = '21af897c92b7e373a0a40551c972388a';
var redirectUrl = 'http://localhost:8080/toyapp/oauth/kakaoLogin';
var KAKAO_AUTH_URL = 'https://kauth.kakao.com/oauth/authorize?client_id='+restApiKey+'&redirect_uri='+redirectUrl+'&response_type=code';

$(document).ready(function(){
	
	console.log('==login.js==');
});

function c(){
	console.log('==sign up==');
}

function kakaoLoginFn(){
	console.log('==kakaoLoginFn==');
	
	var kakaoId = $('#kakaoId').val() ? $('#kakaoId').val() : '';
	var kakaoPw = $('#kakaoPw').val() ? $('#kakaoPw').val() : '';
	
	(kakaoId != '' && kakaoPw != '') ? confirmIdPw = 'Y' : alert('id, pw 입력바람.') ; 
	
	if(confirmIdPw === 'Y'){
		 console.log(KAKAO_AUTH_URL);
		    Kakao.Auth.authorize({
			  redirectUri: redirectUrl,
			});
	}
}

function kakaoLogin(member){
	console.log("==kakaoLogin==");
	console.log(member);
	
	commonAjaxCall('/login/login.json',member,goHome);
}

function goHome(result){
	console.log(result);
	
	if(result.success == 0){
		console.log('login 실패');
		alert('login 실패');
	}else if(result.success == 1){
		console.log('login 성공');
		location.replace("/toyapp/dashboard/main");
	}
}