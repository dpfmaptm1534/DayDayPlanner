const userId = document.getElementById('userId');
const userPw = document.getElementById('userPw');
const loginBtn = document.getElementById('loginBtn');


function login(){
    fetch('login/', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "data":{
                userId:userId.value,
                userPw:userPw.value
            }
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            //1:비밀번호 불일치  |  2:존재하지않는 계정   |  3:로그인성공
            if (data == '3') {
                location.href = "main"
            } else if( data == '1'){
                alert('비밀번호가 일치하지 않습니다');
            } else{
                alert('등록되지 않은 아이디입니다')
            }
        });
}

$(document).ready(function(){
    var key = getCookie("key");
    $("#userId").val(key);
    if($("#userId").val() != ""){
        $("#idSaveCheck").attr("checked", true);
    }
    $("#idSaveCheck").change(function(){
        if($("#idSaveCheck").is(":checked")){
            setCookie("key", $("#userId").val(), 7);//7일간 보관
        }else{
            deleteCookie("key");
        }
    });
    $("#userId").keyup(function(){
        if($("#idSaveCheck").is(":checked")){
            setCookie("key", $("#userId").val(), 7);//7일간 보관
        }
    });
});
function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}
function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}
function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}