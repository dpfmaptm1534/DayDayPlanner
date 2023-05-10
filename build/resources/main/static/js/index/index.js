    // 프로필클릭시 드롭다운 메뉴
    const a = document.getElementById('profile_pic');
    let b = document.getElementById('dropbar');
    let ck = false;
    a.addEventListener('click', function (e) {
        if (!ck) {
            b.style.display = 'block';
            a.classList.add('open')
            ck = true;
        } else if (e.target == a && ck == true) {
            a.classList.remove('open')
            b.style.display = 'none';
            ck = false;
        }
    })

    // 생성버튼눌렀을때
    const aa = document.getElementById('plusbtn');
    let bb = document.getElementById('create_div');
    let ckck = false;
    let xbtn = document.getElementById('cr_xbtn')
    aa.addEventListener('click', function (e) {
        if (!ckck) {
            bb.style.display = 'block';
            aa.classList.add('open')
            ckck = true;
        } else if (e.target == aa && ckck == true) {
            aa.classList.remove('open')
            bb.style.display = 'none';
            ckck = false;
        }
    })
    xbtn.addEventListener('click', function (e) {
        aa.classList.remove('open')
        bb.style.display = 'none';
        ckck = false;
    })



    // 삭제버튼
    document.addEventListener('click', function (e) {

        if (e.target.classList.value == 'content_info_xbox') {
            if (e.target.parentElement.parentElement.parentElement.childElementCount <= 1) {
                e.target.parentElement.parentElement.parentElement.parentElement.remove();
            } else {
                e.target.parentElement.parentElement.remove();
            }
        }
    })



    //title save
    const title_input = document.getElementById('title_input');
    let past_title;
    title_input.addEventListener('focusin', (e) => {
        past_title = e.target.innerText;
    })
    title_input.addEventListener('focusout', (e) => {
        if (past_title != e.target.innerText) {
            console.log(e.target.innerText)
        }
    })

    //title modify icon
    const modify_btn = document.getElementById('modify_btn');
    modify_btn.addEventListener('click', () => {
        title_input.focus();
    })

    //leftdrawer열렸다 닫혔다
    const opened_drawer = document.getElementById('opened_drawer');
    const closed_drawer = document.getElementById('closed_drawer');
    const leftmenu = document.getElementById('leftmenu')
    const dark = document.getElementById('dark');
    closed_drawer.addEventListener('click',function(){
        leftmenu.classList.remove('cl');
        leftmenu.classList.add('op');
        dark.classList.add('dark');
    })
    opened_drawer.addEventListener('click',function(){
        leftmenu.classList.remove('op');        
        leftmenu.classList.add('cl');
        dark.classList.remove('dark');
 
    })
    dark.addEventListener('click',function(){
        leftmenu.classList.remove('op');        
        leftmenu.classList.add('cl');
        dark.classList.remove('dark');
    })

    //새로운 플래너생성
    const plannerlist = document.getElementById('sheetul');
    const copy=document.querySelector('.sheet.hide');
    const newbtn = document.querySelector('.sheetbtn');
    const memberId = document.getElementById('memberId');

    function deepCopy(){
        $.ajax({
            type : 'POST',           // 타입 (get, post, put 등등)
            url : '/main',           // 요청할 서버url
            dataType : "json",
            contentType:"application/json",
            data : JSON.stringify({
                memberId:memberId.value
            }),
            success : function(result) { // 결과 성공 콜백함수
                 console.log(result);
            }}
        )
                //
                // if(result.status=='OK'){
                //     const checkednode = document.querySelector('.sheet.check');
                //     console.log('hi')
                //     console.log(copy)
                //     const newNode=copy.cloneNode(true);
                //     newNode.childNodes[1].innerText='새로운 플래너'
                //     newNode.classList.remove('hide');
                //     plannerlist.prepend(newNode);
                //     checkednode.classList.remove('check');
                //     newNode.classList.add('check');
                // }else{
                //     console.log("실패하였습니다");
                // }
        //     }
        // })
    }

    //플래너삭제

