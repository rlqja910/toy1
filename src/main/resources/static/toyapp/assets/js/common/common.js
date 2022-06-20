
function commonAjaxCall(url, dataParam, callBackFunc, beforeFunc, afterFunc, successMsg, errorAfterFunc){
	console.log('toyapp' + url);
	$.ajax({
		type : 'post',
		url : 'http://localhost:8080/toyapp' + url,
		xhrFields : {
			withCredentials : true,
		},
		contentType : 'application/json; charset=utf-8',
		traditional : true,
		data : JSON.stringify(dataParam),
		cache : false,
		crossDomain : true,
		timeout : 60000,
		success : function(result){
			callBackFunc ? callBackFunc(result) : '';
		},
		beforeSend : function(){
			
		},
		complete : function(request,status,error){
			
		},
		error : function(request,status,error){
			
		},
	});
};

function commonAjaxCall2(url,callBackFunc, beforeFunc, afterFunc, successMsg, errorAfterFunc){
	$.ajax({
		type : 'post',
		url : url,
		success : function(result){
			callBackFunc ? callBackFunc(result) : '';
		},
		beforeSend : function(){
			
		},
		complete : function(request,status,error){
			
		},
		error : function(request,status,error){
			
		},
	});
};

function krGender(usGender){
	if(usGender === 'male'){
		return '남성';
	}else if(usGender === 'femail'){
		return '여성';
	}else{
		return '알수없음';
	}
}

function ageRange(data){
	if(data){
		return data+'세';
	}else{
		return '알수없음';
	}
}

function ageRange(data){
	if(data){
		return data.substr(0,2)+'월 '+data.substr(2,2)+'일';
	}else{
		return '알수없음';
	}
}