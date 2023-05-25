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
            async:false,
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
                        dataType : "HTML",
                        async:false,
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
            async:false,
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
        $.ajax({
            type : 'GET',           // 타입 (get, post, put 등등)
            url : '/main/'+sheetId,           // 요청할 서버url
            async:false,
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
                async:false,
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


    //회원정보로이동
    function openMyinfo(){
        $.ajax({
            type : 'GET',           // 타입 (get, post, put 등등)
            url : '/myinfo',           // 요청할 서버url
            async:false,
            dataType : "HTML",
            success : function(result) { // 결과 성공 콜백함수
                $('.content_box').children().remove();
                $('.content_box').html(result);
            }}
        )
    }

    //회원정보수정버튼활성화
    let user_name = $('.user_name>b').text();
    function addClassOnSave(){
        $('#userInfo_userName').keyup(function(){
            if(user_name!=$('#userInfo_userName').val()){
                $('.saveBtn').addClass('able').attr("disabled",false);
            }else{
                $('.saveBtn').removeClass('able').attr("disabled",true);
            }
        })
    }

    //회원정보수정
    function modifyInfo(){
        const mo_username = $('#userInfo_userName');
        const nav_username = $('.user_name>b');

        $.ajax({
            type : 'PUT',           // 타입 (get, post, put 등등)
            url : '/member/name',           // 요청할 서버url
            dataType : "json",
            async:false,
            contentType:"application/json",
            data : JSON.stringify({
                id:memberId.value,
                userName:mo_username.val()
            }),
            success : function(result) { // 결과 성공 콜백함수
                console.log(result);
                console.log('성공')
                nav_username.text(mo_username.val());
                user_name = mo_username.val();
                alert('저장완료');
                $('.saveBtn').removeClass('able').attr("disabled",true);

            }
        })

    }

    //프롭필 변경버튼 눌렀을씨 모달띄우기
    function openProfileChange(){
        if($('#accessToken').val()==''){
            $('.profilePopupDiv').addClass('clicked')
            $('.right_info_div').addClass('open')
            document.addEventListener('click',function(e){
                if(e.target.classList.value=='right_info_div open'){
                    $('.right_info_div.open').removeClass('open')
                    $('.profilePopupDiv').removeClass('clicked')
                };
            })
        }else{
            alert('카카오계정은 변경이 불가능합니다')
        }

    }

    //비밀번호변경페이지로 이동
    function openpwchange(){
        $.ajax({
            type : 'GET',           // 타입 (get, post, put 등등)
            url : '/myinfo/pwchange',           // 요청할 서버url
            async:false,
            dataType : "HTML",
            success : function(result) { // 결과 성공 콜백함수
                $('.content_box').children().remove();
                $('.content_box').html(result);
            }}
        )
    }




    //유효성검사
    function chkPW(){
        const pw=document.getElementById('chPw_new_userpw').value;
        const num = pw.search(/[0-9]/g);
        const eng = pw.search(/[a-z]/ig);
        const spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

        if(pw.length < 10 || pw.length > 20){
            alert('비밀번호는 10자리 ~ 20자리 이내로 입력해주세요');
            return false;
        }else if(pw.search(/\s/) != -1){
            alert("비밀번호는 공백 없이 입력해주세요");
            return false;
        }else if( (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ){
            alert("비밀번호는 영문,숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요");
            return false;
        }else {

            return true;
        }
    }

    //비번변경
    function changepw(){
        const old_pw = document.getElementById('chPw_userpw');
        const new_pw = document.getElementById('chPw_new_userpw');
        const new_pw_re = document.getElementById('chPw_new_userpw_re');
        if(old_pw.value==''){
            alert('기존 비밀번호를 입력해주세요')
            old_pw.focus();
            return false;
        }
        if(new_pw.value==''){
            alert('새 비밀번호를 입력해주세요')
            new_pw.focus();
            return false;
        }
        if(new_pw_re.value==''){
            alert('새 비밀번호 확인을 입력해주세요')
            new_pw_re.focus();
            return false;
        }

        if(new_pw.value!=new_pw_re.value){
            alert('새 비밀번호가 서로 일치하지 않습니다.')
            new_pw_re.focus();
            return false;
        }
        if(old_pw.value==new_pw.value){
            alert('기존 비밀번호와 다르게 입력해주세요')
            new_pw.focus();
            return false;
        }

        if(!chkPW()){
            return false;
        }

        fetch('/member/chpassword', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "data":{
                    id:memberId.value,
                    oldPw:old_pw.value,
                    newPw:new_pw.value
                }
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.status == 'OK') {
                    alert('변경완료');
                    openMyinfo();
                }else{
                    if(data.description =='inconsistency') {
                        alert('기존 비밀번호가 틀렸습니다');
                        old_pw.focus();
                    }else{
                        alert('문제가 발생하였습니다')
                    }
                }
            });
    }

    //첨부파일선택창 열기
    function openUploadImg(){
        //$('#uploadInput').click(); ->Maxmum call stack size exceeded 에러
        $('#uploadInput').get(0).click();
    }
    //기본프로필로설정
    function setDefaultProfile(){
        $.ajax({
            type : 'GET',           // 타입 (get, post, put 등등)
            url : '/profileapi/default/'+memberId.value,           // 요청할 서버url
            async:false,
            success : function() { // 결과 성공 콜백함수
                $('#myProfileImage').removeClass('set');
                $('#defaultProfileImage').addClass('set');
                $('#profile_pic_img').attr('src','/image/index/defaultProfileImg.jpeg');
                $('#userInfo_profileImage').attr('src','/image/index/defaultProfileImg.jpeg')
            }}
        )
    }
    //나의프로필로설정
    function setMyProfile(){
        $.ajax({
            type : 'GET',           // 타입 (get, post, put 등등)
            url : '/profileapi/myprofile/'+memberId.value,           // 요청할 서버url
            async:false,
            success : function() { // 결과 성공 콜백함수
                $('#defaultProfileImage').removeClass('set');
                $('#myProfileImage').addClass('set');
                $('#profile_pic_img').attr('src',$('#myProfileImage>.profilePopupLi_img').attr('src'));
                $('#userInfo_profileImage').attr('src',$('#myProfileImage>.profilePopupLi_img').attr('src'));
            }}
        )
    }

    //나의프로필등록
    function uploadImg(){
        let form = $('#uploadForm')[0];
        let formData = new FormData(form);
        let fileName = $('#uploadInput').val()
        fileName = fileName.slice(fileName.indexOf(".")+1).toLowerCase();
        if(fileName != "jpg" && fileName != "png" && fileName != "jpeg" && fileName != "gif" && fileName != "bmp"){
            alert("이미지파일만 업로드 가능합니다.");
            return false;
        }

        //contentType과 processData 옵션 모두 false로 넣어줘야만 잘 동작한다.
        // 이게 없으면 오류가 발생하면서 서버에 제대로 안 날아감
        // - contentType : false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
        // - processData : false로 선언 시 formData를 string으로 변환하지 않음
        $.ajax({
            url : '/profileapi',
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            success: function(result) {
                $('#myProfileImage').removeClass('none');
                console.log(result.data.profileImageDirectory)
                $('#myProfileImage>.profilePopupLi_img').attr('src',result.data.profileImageDirectory);
                setMyProfile();
            },
            error: function(r) {
                console.log('error');
            }
        })
    }
