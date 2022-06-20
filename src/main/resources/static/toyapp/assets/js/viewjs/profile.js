
var redirectUrl = 'http://localhost:8080/toyapp/oauth/kakaoLogin';

$(document).ready(function(){
	
	console.log('==profile.js==');
	$('#userNm2').text(loginUser.userNm);
	$('#userEmail2').text(loginUser.emailAdr ? loginUser.emailAdr : 'Kakao');
	$('#profileImg2').attr('src',loginUser.profile);
	$('#ageRange').text(ageRange(loginUser.ageRange));
	$('#birthDate').text(ageRange(loginUser.birthDate));
	$('#gender').text(krGender(loginUser.gender));
	
});

