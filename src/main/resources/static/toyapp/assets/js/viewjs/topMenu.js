
$(document).ready(function(){
	
	console.log('==topMenu.js==');
	
	$('#title').text(loginUser.userNm + '님의 대시보드');
	$('#userNm').text(loginUser.userNm + '님');
	$('#userEmail').text(loginUser.emailAdr ? loginUser.emailAdr : 'Kakao');
	$('#profileImg').attr('src',loginUser.profile);
});

function signOutFn(){
	console.log('로그아웃 중...');
	
	commonAjaxCall('/oauth/kakaoLogout',null,logoutResult);
}

function logoutResult(result){
	location.replace("/toyapp/test/login.do");
}

function profileViewFn(){
	console.log('==profileViewFn==');
	location.replace("/toyapp/profile/profile.do");
}