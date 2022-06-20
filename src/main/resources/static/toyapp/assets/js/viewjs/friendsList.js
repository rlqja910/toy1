
var redirectUrl = 'http://localhost:8080/toyapp/oauth/kakaoLogin';

$(document).ready(function(){
	
	console.log('==friendsList.js==');
	console.log(token);
	
	console.log(friendData);
	
	var friendListHtml = '';
	
	if(friendData.elements.length){
		for(var i=0; i < friendData.elements.length; i++){
			friendListHtml += '<tr>';
			friendListHtml += '<td>' + friendData.elements[i].profile_nickname +'</td>';
			friendListHtml += '<td>' + friendData.elements[i].profile_nickname +'</td>';
			friendListHtml += '<td>' + friendData.elements[i].uuid +'</td>';
			friendListHtml += '<td>' + (friendData.elements[i].favorite==='true'?'O':'X') +'</td>';
			friendListHtml += '<td>' + '<a href="javascript:void(0)" onclick=sendMsg("'+friendData.elements[i].uuid+'") class="btn btn-secondary">전송</a>' +'</td>';
			friendListHtml += '</tr>';
		}
	}else{
		friendListHtml += '<tr>';
			friendListHtml += '<td colspan=3 style="text-align:center;">친구가 없습니다.</td>';
			friendListHtml += '</tr>';
	}
	
	$('#friendList').html(friendListHtml);
	
});

function sendMe(){
	commonAjaxCall2('http://api.openweathermap.org/data/2.5/weather?lat=37.242558926502994&lon=127.10865442048673&appid=3337138afb92b967b3867a54db1048f1'
					,weatherCallBackFn);
}

function weatherCallBackFn(resp){
	console.log(resp);
		
	console.log(resp);
                console.log("현재온도 : "+ (resp.main.temp- 273.15) );
                nowTem = resp.main.temp;
                console.log("현재습도 : "+ resp.main.humidity);
                nowHum = resp.main.humidity;
                console.log("날씨 : "+ resp.weather[0].main );
                wether = resp.weather[0].main;
                console.log("상세날씨설명 : "+ resp.weather[0].description );
                weatherDetail = resp.weather[0].description;
                console.log("날씨 이미지 : "+ resp.weather[0].icon );
                weatherImg = resp.weather[0].icon;
                console.log("바람   : "+ resp.wind.speed );
                windy = resp.wind.speed;
                console.log("나라   : "+ resp.sys.country );
                nation = resp.sys.country;
                console.log("도시이름  : "+ resp.name );
                cityNm = resp.name;
                console.log("구름  : "+ (resp.clouds.all) +"%" ); 
                cloudy = (resp.clouds.all) +"%";  
                
		
		Kakao.API.request({
		  url: '/v1/api/talk/friends/message/default/send',
		  data: {
			receiver_uuids: [uuid],
		    template_object: {
		      object_type: 'text',
		      text: '안녕하세요? 기범맨입니다. 한보라마을 경남아너스빌 9단지의 현재온도는 '+nowTem+', 현재습도는 '+nowHum +', 날씨는 '+wether+', 상세날씨설명은 '+weatherDetail +', 날씨 이미지는 '
		      						+weatherImg+', 바람은 '+windy +', 나라는 '+nation+', 도시이름은 '+cityNm +', 구름은 '+cloudy+'입니다.',
		      link: {
		          web_url: 'https://google.com',
		        },
		      button_title: '바로 확인',
		    },
		  },
		  success: function(response) {
		    console.log(response);
		  },
		  fail: function(error) {
		    console.log(error);
		  },
		});
}

function sendMsg(id){
	console.log('메시지 보내기');
	
	uuid = id;
	
	commonAjaxCall2('http://api.openweathermap.org/data/2.5/weather?lat=37.242558926502994&lon=127.10865442048673&appid=3337138afb92b967b3867a54db1048f1'
					,weatherCallBackFn);
	
}