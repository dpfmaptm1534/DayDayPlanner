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