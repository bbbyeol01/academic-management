//   페이지 클릭 이벤트 처리
//   clear 버튼 누르면 검색 조건 사라진 목록(/member/teacher) 출력
document.querySelector(".pagination").addEventListener("click", function (e) {
    e.stopPropagation();
    e.preventDefault();

    const target = e.target;

    if (target.tagName !== 'A') {
        return;
    }

    const formObj = document.querySelector("form");

    const num = target.getAttribute("data-num");
    const keyword = document.querySelector(".keyword");
    const type = document.querySelector(".type");

    // 버튼 누르면 ?page=num
    formObj.innerHTML += `<input type="hidden" name="page" value="${num}">`;

    // 검색 목록이면
    if(keyword.value !== ''){
        formObj.innerHTML += `<input type="hidden" name="type" value="${type.value}">`
        formObj.innerHTML += `<input type="hidden" name="keyword" value="${keyword.value}">`
    }

    formObj.submit();

}, false);
//  페이지 클릭 end


//  검색 조건 초기화
document.querySelector(".clearBtn").addEventListener("click", function (e){
    e.stopPropagation();
    e.preventDefault();

    self.location = '/member/teacher';

}, false)
//  검색 초기화 end


// nav 활성화
document.querySelector(".teacherManagement").classList.add("active");
document.querySelector(".memberManagement").classList.add("active");

// 등록 --------------------------------------------------------
// 등록 모달 띄우기
const teacherInsertBtn = document.querySelector(".teacherInsertBtn");
const insertModal = document.querySelector("#insertModal");


teacherInsertBtn.addEventListener("click", function (e) {
    e.preventDefault();
    e.stopPropagation();

    $("#insertModal").modal('show');

})


const insertId = document.querySelector("#insertId");
const insertPassword = document.querySelector("#insertPassword");
const insertName = document.querySelector("#insertName");
const insertForm = document.querySelector("#insertForm");

function insertValid(){

    insertId.value = insertId.value.trim();
    insertPassword.value = insertPassword.value.trim();
    insertName.value = insertName.value.trim();

    if(insertId.value === ""){
        alert("아이디가 입력되지 않았습니다.");
        return;
    }

    if(insertPassword.value === ""){
        alert("비밀번호가 입력되지 않았습니다.");
        return;
    }

    if(insertName.value === ""){
        alert("이름이 입력되지 않았습니다.");
        return;
    }

    insertForm.submit();

}

const registerBtn = document.querySelector(".registerBtn");

registerBtn.addEventListener("click", function (e){
    e.stopPropagation();
    e.preventDefault();

    insertValid();
});


// 등록 끝 --------------------------------------------------------





// 수정 --------------------------------------------------------

// const studentModifyBtn = document.querySelector(".studentModifyBtn");
// studentModifyBtn.addEventListener("click", function (e) {
//   e.stopPropagation();
//   e.preventDefault();
//
// })

const modifyId = document.querySelector("#modifyId");
// const modifyPassword = document.querySelector("#modifyPassword");
const modifyName = document.querySelector("#modifyName");


async function getTeacher(paramList){
    const response = await axios.get('/member/teacher/getTeacher', {params : paramList});
    return response.data;
}

function teacherModify(teacherId){

    const params = {
        id : teacherId
    }

    getTeacher(params).then(result => {
        console.log(result);

        modifyId.value = result.id;
        // modifyPassword.value =
        modifyName.value = result.name;
        if(result.uuid != null){
            modifyImage.innerHTML = '<img src="/view/' + result.uuid + '_' + result.fileName +'" class="rounded-circle" style="width: 100%; height:100%; object-fit: cover; cursor: pointer">';
        }else{
            modifyImage.innerHTML = `<img class="rounded-circle" src="/images/default_profile.jpg" alt="" style="width: 100%; height:100%; object-fit: cover; cursor: pointer">`;
        }


    }).catch(e => {

    })

    $("#modifyModal").modal('show');

}



function modifyValid(){

    modifyId.value = modifyId.value.trim();
    // modifyPassword.value = modifyPassword.value.trim();
    modifyName.value = modifyName.value.trim();

    if(modifyId.value === ""){
        alert("아이디가 입력되지 않았습니다.");
        return;
    }

    // if(modifyPassword.value === ""){
    //   alert("비밀번호가 입력되지 않았습니다.");
    //   return;
    // }

    if(modifyName.value === ""){
        alert("이름이 입력되지 않았습니다.");
        return;
    }

    modifyForm.submit();

}


const modifyForm = document.querySelector("#modifyForm");
const modifyBtn = document.querySelector(".modifyBtn");
modifyBtn.addEventListener("click", function (e) {
    e.stopPropagation();
    e.preventDefault();

    modifyValid();
})


// 수정 끝--------------------------------------------------------




// 삭제--------------------------------------------------------

function teacherDelete(teacherId){

    if(!confirm('삭제하시겠습니까?\n관련 수업이 존재하는 경우 삭제되지 않습니다.')){
        return;
    }

    const params = {
        id : teacherId
    }

    // 백에서 code를 보내거나 redirect하는 대신 백에는 데이터/처리 요청만 하고 성공 실패 여부에 따라 프론트에서 해결
    deleteTeacher(params).then(result => {

        console.log(result);

        if(result){
            // self.location = '/member/teacher?code=delete-success';
            alert('삭제되었습니다.');
            return;
        }

        alert('관련 수업이 존재하여 삭제되지 않았습니다.');
        location.reload();

    }).catch( e => {
        console.log(e);
        alert('에러가 발생했습니다.\n지속될 경우 관리자에게 문의해주세요.');
        location.reload();
    })

}


async function deleteTeacher(paramList){
    const response = await axios.post('/member/teacher/delete', null, {params: paramList});
    return response.data;
}


// 삭제 끝 --------------------------------------------------------



const url = new URL(window.location.href);
console.log(window.location.href);
const urlSearchParams = url.searchParams;

console.log(urlSearchParams.get("code"));
const code = urlSearchParams.get("code");

const popupModal = document.querySelector("#popupModal");

const popupContent = document.querySelector(".popupContent");

// function popup(){
//   $("#alertModal").modal('show');
// }


switch (code) {
    case 'insert-success' :
        popupContent.innerText = "등록되었습니다. ◝(ᵔᵕᵔ)◜";
        // 이거 진짜 왜 안되냐????????????????????????????왜여기만????????????????? 왜 popupModal만 안됨???????????
        // $("#popupModal").modal('show');
        alert('등록되었습니다. ◝(ᵔᵕᵔ)◜');
        // popup();
        history.replaceState({}, null, location.pathname);
        break;
    case 'insert-fail' :
        alert('이미 존재하는 회원입니다.');
        popupContent.innerText = '이미 존재하는 회원입니다.';
        // popup();
        history.replaceState({}, null, location.pathname);
        break;
    case 'modify-success' :
        alert('수정되었습니다.');
        popupContent.innerText = '수정되었습니다.';
        // popup();
        history.replaceState({}, null, location.pathname);
        break;
    case 'modify-fail' :
        alert('수정에 실패했습니다.');
        popupContent.innerText = '수정 실패했습니다.';
        // popup();
        history.replaceState({}, null, location.pathname);
        break;
    // case 'delete-fail' :
    //     alert('삭제에 실패했습니다.');
    //     popupContent.innerText = '삭제 실패했습니다.';
    //     // popup();
    //     history.replaceState({}, null, location.pathname);
    //     break;
    // case 'delete-success' :
    //     alert('삭제되었습니다.');
    //     popupContent.innerText = '삭제되었습니다.';
    //     // popup();
    //     history.replaceState({}, null, location.pathname);
    //     break;
}



//   이미지 처리
const inputImage = document.querySelector(".inputImage");
const uploadImage = document.querySelector("#uploadImage");
const insertFileName = document.querySelector("#insertFileName");
const insertFile = document.querySelector("#insertFile");
inputImage.addEventListener("click", function (){
    uploadImage.click();
})

uploadImage.addEventListener("change", function (){
    if (uploadImage.files && uploadImage.files[0]) {
        var reader = new FileReader();

        // 이미지를 미리보기에 표시
        reader.onload = function(e) {
            inputImage.innerHTML = '<img src="' + e.target.result + '" alt="이미지 미리보기" class="rounded-circle" style="width: 100%; height:100%; object-fit: cover; cursor: pointer">';
        };

        // 파일을 읽어옴
        reader.readAsDataURL(uploadImage.files[0]);
    }
})



const modifyImage = document.querySelector(".modifyImage");
const modifyFile = document.querySelector("#modifyFile");
modifyImage.addEventListener("click", function (){
    modifyFile.click();
})

modifyFile.addEventListener("change", function (){
    if (modifyFile.files && modifyFile.files[0]) {
        var reader = new FileReader();

        // 파일을 읽어서 이미지를 미리보기에 표시합니다.
        reader.onload = function(e) {
            modifyImage.innerHTML = '<img src="' + e.target.result + '" alt="이미지 미리보기" class="rounded-circle" style="width: 100%; height:100%; object-fit: cover; cursor: pointer">';
        };

        // 파일을 읽습니다.
        reader.readAsDataURL(modifyFile.files[0]);
    }
})

