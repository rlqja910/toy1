
$(document).ready(function(){
	
	console.log('==menu.js==');
	
});

function friendsList(){
	console.log('친구목록 클릭');
	
	Kakao.Auth.authorize({
			  redirectUri: 'http://localhost:8080/toyapp/friend/friendsList',
			  scope : 'friends',
			});
}