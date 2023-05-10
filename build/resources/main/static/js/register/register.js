const user_id = document.getElementById("user_id");
const user_pw = document.getElementById("user_pw");
const user_pw_re = document.getElementById("user_pw_re");
const user_name = document.getElementById("user_name");
const msgbox = document.getElementById('msgbox');

// 아이디 중복확인
let idck=false;
function overlap(){
    if(user_id.value==''){
        user_id.classList.remove('pass');
    }else{
        $.ajax({
            type : 'GET',           // 타입 (get, post, put 등등)
            url : '/register/search?userid='+user_id.value,           // 요청할 서버url
            async : true,            // 비동기화 여부 (default : true)
            headers : {              // Http header
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
            },
            success : function(result) { // 결과 성공 콜백함수
                if(result.status=='OK'){
                    user_id.classList.remove('pass');
                    user_id.classList.add('nonpass');
                    msgbox.innerHTML='이미 사용중인 아이디입니다'
                    idck=false;
                }else{
                    user_id.classList.remove('nonpass');
                    user_id.classList.add('pass');
                    msgbox.innerHTML=''
                    idck=true;
                }
            }
        })
    }
}


// 선 제거
function normal(){    
    user_pw.classList.remove('pass');
    user_pw_re.classList.remove('pass');
    user_pw.classList.remove('nonpass');
    user_pw_re.classList.remove('nonpass');
}
function nonpass(){
    user_pw.classList.remove('pass');
    user_pw_re.classList.remove('pass');
    user_pw.classList.add('nonpass');
    user_pw_re.classList.add('nonpass');
}
function pass(){
    user_pw.classList.add('pass');
    user_pw_re.classList.add('pass');
    user_pw.classList.remove('nonpass');
    user_pw_re.classList.remove('nonpass');
}


//비번불일치
user_pw.addEventListener('focusout',function(){
    if(user_pw_re.value!=''&&user_pw_re.value!=user_pw.value){
        nonpass()
    }else if(user_pw_re.value!=''&&user_pw_re.value==user_pw.value){
        pass()
    }else{
        normal()
    }
})

user_pw_re.addEventListener('focusout',function(){
    if(user_pw.value!=''&&user_pw_re.value!=user_pw.value){
        nonpass()
    }else if(user_pw.value!=''&&user_pw_re.value==user_pw.value){
        pass()
    }else{
        normal()
    }
})






//유효성검사

function chkPW(){
    var pw=user_pw_re.value;
    var num = pw.search(/[0-9]/g);
    var eng = pw.search(/[a-z]/ig);
    var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

    if(pw.length < 10 || pw.length > 20){
        msgbox.innerHTML='비밀번호는 10자리 ~ 20자리 이내로 입력해주세요';
    return false;
    }else if(pw.search(/\s/) != -1){
        msgbox.innerHTML="비밀번호는 공백 없이 입력해주세요";
    return false;
    }else if( (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ){
        msgbox.innerHTML="비밀번호는 영문,숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요";
    return false;
    }else {
    
    return true;
    }
}
function submittest(){
    var RegExp = /^[a-zA-Z0-9]{4,15}$/; //id와유효성 검사 정규식
    var n_RegExp = /^[가-힣a-zA-Z]{2,15}$/; //이름 유효성검사 정규식

    if(user_id.value==''){
        msgbox.innerHTML='아이디를 입력해주세요';
        user_id.focus();
        return false;
    }
    if(!idck){
        msgbox.innerHTML='아이디 중복확인을 해주세요'
        return false;
    }
    if(user_pw.value==''){
        msgbox.innerHTML='비밀번호를 입력해주세요';
        user_pw.focus();
        return false;
    }
    if(user_pw_re.value==''){
        msgbox.innerHTML='비밀번호확인을 입력해주세요'
        user_pw_re.focus();
        return false;
    }
    if(user_pw.value!=user_pw_re.value){
        user_pw.classList.add('nonpass');
        user_pw_re.classList.add('nonpass');
        msgbox.innerHTML='비밀번호를 확인해주세요'
        return false;
    }else{
        user_pw.classList.remove('nonpass');
        user_pw_re.classList.remove('nonpass');
        user_pw.classList.add('pass');
        user_pw_re.classList.add('pass');
    }
    if(!chkPW()){
        return false;
    }
    if(user_name.value==''){
        msgbox.innerHTML='이름을 입력해주세요'
        user_name.focus();
        return false;
    }
    if(!n_RegExp.test(user_name.value)){
        msgbox.innerHTML='이름은 2~15 한/영자만 가능합니다'
        return false;
    }

    fetch('/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "data":{
                userId:user_id.value,
                userPw:user_pw.value,
                userName:user_name.value,
            }
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data)
            if (data.status == 'OK') {
                alert('회원가입완료');
                location.href = "login"
            } else {
                alert('등록에 실패하였습니다. 다시한번 확인해주세요')
            }
        });


}