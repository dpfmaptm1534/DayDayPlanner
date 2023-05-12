    // 프로필클릭시 드롭다운 메뉴
    const profile_pic = document.getElementById('profile_pic');
    let dropbar = document.getElementById('dropbar');
    let ck = false;
    profile_pic.addEventListener('click', function (e) {
        if (!ck) {
            dropbar.style.display = 'block';
            profile_pic.classList.add('open')
            ck = true;
        } else if (e.target == profile_pic && ck == true) {
            profile_pic.classList.remove('open')
            dropbar.style.display = 'none';
            ck = false;
        }
    })

    //leftdrawer열렸다 닫혔다
    function openDrawer(){
        const leftmenu = document.getElementById('leftmenu');
        const dark = document.getElementById('dark');
        leftmenu.classList.remove('cl');
        leftmenu.classList.add('op');
        dark.classList.add('dark');
    }
    function closeDrawer(){
        const leftmenu = document.getElementById('leftmenu');
        const dark = document.getElementById('dark');
        leftmenu.classList.remove('op');
        leftmenu.classList.add('cl');
        dark.classList.remove('dark');
    }
    function dark(){
        const leftmenu = document.getElementById('leftmenu');
        const dark = document.getElementById('dark');
        leftmenu.classList.remove('op');
        leftmenu.classList.add('cl');
        dark.classList.remove('dark');
    }


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
                try{
                    document.getElementsByClassName('check').item(0).classList.remove('check');
                }catch(e){
                }
                const newNode= copy.cloneNode(true);
                newNode.childNodes[1].innerText='새로운 플래너'
                newNode.classList.remove('hide');
                newNode.classList.add('check')
                newNode.id = 'sheet'+result.data.id
                newNode.firstElementChild.setAttribute('onclick',`openPlanner(${result.data.id})`)
                newNode.lastElementChild.setAttribute('onclick',`removeSheet(${result.data.id})`)
                plannerlist.prepend(newNode);
                openPlanner(result.data.id);
            }}
        )

    }

    //플래너삭제
    document.addEventListener('click', function (e) {
        if (e.target.classList.value == 'remove_planner') {
                    $.ajax({
                        type : 'GET',           // 타입 (get, post, put 등등)
                        url : '/main/default',           // 요청할 서버url
                        async : true,
                        dataType : "HTML",
                        success : function(result) { // 결과 성공 콜백함수
                            if(document.getElementById('sheetul').childElementCount<=2){
                                e.target.parentElement.parentElement.remove();
                                $('.content_box').children().remove();
                                $('.content_box').html(result);
                            }else {
                                if(e.target.parentElement.parentElement.nextElementSibling.classList.value=='sheet hide'){
                                    document.getElementsByClassName('check').item(0).classList.remove('check')
                                    e.target.parentElement.parentElement.previousElementSibling.classList.add('check');
                                    e.target.parentElement.parentElement.previousElementSibling.firstElementChild.click();
                                    e.target.parentElement.parentElement.remove();
                                }else{
                                    document.getElementsByClassName('check').item(0).classList.remove('check')
                                    e.target.parentElement.parentElement.nextElementSibling.classList.add('check');
                                    e.target.parentElement.parentElement.nextElementSibling.firstElementChild.click();
                                    e.target.parentElement.parentElement.remove();
                                }

                            }

                        }}
                    )

        }
    })
    function removeSheet(sheetId){
        $.ajax({
            type : 'DELETE',           // 타입 (get, post, put 등등)
            url : '/main/deleteSheet',           // 요청할 서버url
            dataType : "json",
            contentType:"application/json",
            data : JSON.stringify({
                id:sheetId
            }),
            success : function(result) { // 결과 성공 콜백함수
                console.log(result)

            }}
        )
    }


    //플래너클릭
    document.addEventListener('click', function (e) {
        if (e.target.classList.value == 'planner_title') {
            try{
                document.getElementsByClassName("check").item(0).classList.remove('check');
            }catch(e){}
            e.target.parentElement.classList.add('check');

        }
    })
    function openPlanner(sheetId){
        console.log(sessionStorage.getItem('userId'))
        $.ajax({
            type : 'GET',           // 타입 (get, post, put 등등)
            url : '/main/'+sheetId,           // 요청할 서버url
            async:true,
            dataType : "HTML",
            success : function(result) { // 결과 성공 콜백함수
                $('.content_box').children().remove();
                $('.content_box').html(result);
            }}
        )
    }

    // 일정생성버튼눌렀을때
    let ckeck = false; //배경 흐리게 기본값은 false
    function openPlus(event){
        const plusbtn = document.getElementById('plusbtn');
        let create_div = document.getElementById('create_div');
        if (!ckeck) {
            create_div.style.display = 'block';
            plusbtn.classList.add('open')
            ckeck = true;
        } else if (event.target == plusbtn && ckeck == true) {
            plusbtn.classList.remove('open')
            create_div.style.display = 'none';
            ckeck = false;
        }
    }
    function closePlus(event){
        const dateInfo = document.getElementById('date_info');
        const contentInfo = document.getElementById('content_info');
        const plusbtn = document.getElementById('plusbtn');
        let create_div = document.getElementById('create_div');
        plusbtn.classList.remove('open')
        create_div.style.display = 'none';
        dateInfo.value='';
        contentInfo.value='';
        ckeck = false;
    }


    //일정생성버튼
    function addPlan(sheetId){
        const dateInfo = document.getElementById('date_info');
        const contentInfo = document.getElementById('content_info');
        const plusbtn = document.getElementById('plusbtn');
        let create_div = document.getElementById('create_div');
        var dateInfo_RegExp = /^[0-9]{1,10}$/; //날짜는 숫자 형식 유효성검사 정규식
        if(!dateInfo_RegExp.test(dateInfo.value)){
            alert('날짜는 숫자로 입력해주세요');
            dateInfo.focus();
            return false;
        }
        $.ajax({
            type : 'POST',           // 타입 (get, post, put 등등)
            url : '/main/addPlan',           // 요청할 서버url
            dataType : "json",
            contentType:"application/json",
            data : JSON.stringify({
                memberId: memberId.value,
                eventDate: dateInfo.value,
                eventTitle: contentInfo.value,
                sheetId:sheetId
            }),
            success : function(result) { // 결과 성공 콜백함수
                //반드시 href 뒤의 따옴표 안 영역의 id는 앞에 한 칸 띄워야 한다.
                // $("#maincontent").load(window.location.href + " #maincontent");
                openPlanner(sheetId)
                plusbtn.classList.remove('open')
                create_div.style.display = 'none';
                ckeck = false;
                dateInfo.value='';
                contentInfo.value='';

            }}
        )

    }


    //일정삭제버튼
    function delBoard(boardId,event){
        $.ajax({
            type : 'DELETE',           // 타입 (get, post, put 등등)
            url : '/main',           // 요청할 서버url
            dataType : "json",
            contentType:"application/json",
            data : JSON.stringify({
                id: boardId,
            }),
            success : function(result) { // 결과 성공 콜백함수
                console.log(result)
                if (event.target.classList.value == 'content_info_xbox') {
                    if (event.target.parentElement.parentElement.parentElement.parentElement.childElementCount<= 2) {
                        event.target.parentElement.parentElement.parentElement.parentElement.remove();
                    } else {
                        event.target.parentElement.parentElement.parentElement.remove();
                    }
                }
        }})
    }



    //title save
    let past_title;
    function titleFoIn(event){
        past_title = event.target.innerText;
        console.log()
    }
    function titleFoOut(event){
        let title_input = document.getElementById('title_input');
        let openedSheetId = document.getElementById('openedSheetId');
        console.log(past_title)
        console.log(event.target.innerText)
        if (past_title != event.target.innerText) {
            $.ajax({
                type : 'PUT',           // 타입 (get, post, put 등등)
                url : '/main',           // 요청할 서버url
                dataType : "json",
                contentType:"application/json",
                data : JSON.stringify({
                    id:openedSheetId.value,
                    memberId:memberId.value,
                    plannerTitle: event.target.innerText
                }),
                success : function(result) { // 결과 성공 콜백함수
                    console.log('성공')
                    const selectedPlanner = document.querySelector('#sheet'+openedSheetId.value+'>.planner_title');
                    selectedPlanner.innerText = result.data.plannerTitle;
                }
            })
        }
    }


    //title modify icon
    function modifyBtn(event){
        const title_input = document.getElementById('title_input');
        title_input.focus();
    }


    //스크롤시 플러스버튼 이동
    $(window).scroll(function() {
        if(scrollY>60){
            $('#plusbtn').addClass('down');
        } else {
            $('#plusbtn').removeClass('down');
        }
    });


