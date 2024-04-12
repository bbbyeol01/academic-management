
// 페이지 ------------------------------------------------------------
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

    self.location = '/member/student';

}, false)
//  검색 초기화 end
// 페이지 끝 ------------------------------------------------------------



// 등록------------------------------------------------------------
const lessonSelect = document.querySelector(".lessonSelect");


const registerBtn = document.querySelector("#registerBtn");
const insertForm = document.querySelector("#insertForm");

const insertName = document.querySelector("#insertName");
const insertPhone = document.querySelector("#insertPhone");
const insertBirthday = document.querySelector("#insertBirthday");
const insertEmail = document.querySelector("#insertEmail");


// 등록 모달 띄우기
// const insertModal = document.querySelector(".insertModal");
document.querySelector(".studentInsertBtn").addEventListener("click", function (){

    getOngoingLesson().then( result => {
        console.log(result);
        // console.log('result curriculum' + result[0].curriculum.name);

        let htmls = '';

        htmls += `<option value="" selected>수업 선택</option>`;

        for(const lesson of result){
            htmls += `<option value="${lesson.idx}">${lesson.curriculum.name} ${lesson.number}회차</option>`;
        }

        lessonSelect.innerHTML = htmls;
    }).catch( e => {

    });

    $("#insertModal").modal('show');
})



registerBtn.addEventListener("click", function (e){
    e.stopPropagation();
    e.preventDefault();

    insertValid();
});



async function getOngoingLesson(){
    const response = await axios.get('/lesson/getOngoing', null);
    console.log(response);

    return response.data;

}
// 등록 끝------------------------------------------------------------







// 수정------------------------------------------------------------
const modifyIdx = document.querySelector("#modifyIdx");
const modifyName = document.querySelector("#modifyName");
const modifyPhone = document.querySelector("#modifyPhone");
const modifyBirthday = document.querySelector("#modifyBirthday");
const modifyEmail = document.querySelector("#modifyEmail");
const modifyEtc = document.querySelector("#modifyEtc");


// 수정 모달 띄우기
function studentModify(studentIdx){

    const params = {
        studentIdx : studentIdx
    }

    getStudent(params).then(result => {

        console.log(result);

        // modifyLessonIdx.value = result.lessonIdx;
        modifyIdx.value = result.idx;
        modifyName.value = result.name;
        modifyPhone.value = result.phone;
        modifyEmail.value = result.email;
        modifyBirthday.value = result.birthday;
        modifyEtc.value = result.etc;

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

    console.log('modifyValid!');
    console.log(modifyIdx.value);

    modifyName.value = modifyName.value.trim();
    modifyPhone.value = modifyPhone.value.trim();
    modifyBirthday.value = modifyBirthday.value.trim();
    modifyEmail.value = modifyEmail.value.trim();

    if(modifyName.value === ""){
        alert("이름이 입력되지 않았습니다.");
        return;
    }

    if(modifyPhone.value === ""){
        alert("연락처가 입력되지 않았습니다.");
        return;
    }

    if(modifyBirthday.value === ""){
        alert("생년월일이 입력되지 않았습니다.");
        return;
    }

    modifyForm.submit();

}


const modifyForm = document.querySelector("#modifyForm");
const modifyBtn = document.querySelector("#modifyBtn");
modifyBtn.addEventListener("click", function (e){
    e.stopPropagation();
    e.preventDefault();

    modifyValid();




})



async function getStudent(paramList){
    const response = await axios.get('/member/student/getStudent', {params : paramList});
    console.log(response);

    return response.data;
}


// 수정 끝------------------------------------------------------------





// async function modifyStudent(paramList){
//   await axios.post('/member/student/modify', null, {params : paramList});
// }

// async function deleteStudent(paramList){
//   await axios.post('/member/student/delete', null, {params : paramList});
// }




const deleteStudentIdx = document.querySelector("#deleteStudentIdx");
const deleteForm = document.querySelector("#deleteForm");
function studentDelete(studentIdx){

    if(!confirm('삭제하시겠습니까?')){
        return;
    }

    if(!confirm('관련 데이터가 모두 삭제됩니다.\n정말로 삭제하시겠습니까?')){
        return;
    }

    deleteForm.submit();


    // const param = {
    //   studentIdx : studentIdx
    // }
    // deleteStudent(param);




}


function insertValid(){

    insertName.value = insertName.value.trim();
    insertPhone.value = insertPhone.value.trim();
    insertBirthday.value = insertBirthday.value.trim();
    insertEmail.value = insertEmail.value.trim();

    if(lessonSelect.value === ""){
        alert("수업이 선택되지 않았습니다.");
        return;
    }

    if(insertName.value === ""){
        alert("이름이 입력되지 않았습니다.");
        return;
    }

    if(insertPhone.value === ""){
        alert("연락처가 입력되지 않았습니다.");
        return;
    }

    if(insertBirthday.value === ""){
        alert("생년월일이 입력되지 않았습니다.");
        return;
    }

    insertForm.submit();

}




const url = new URL(window.location.href);
console.log(window.location.href);
const urlSearchParams = url.searchParams;

console.log(urlSearchParams.get("code"));
const code = urlSearchParams.get("code");

switch (code){
    case 'insert-success' :
        alert('등록되었습니다. ◝(ᵔᵕᵔ)◜');
        history.replaceState({}, null, location.pathname);
        break;
    case 'insert-fail' :
        alert('이미 존재하는 학생입니다.');
        history.replaceState({}, null, location.pathname);
        break;
    case 'modify-success' :
        alert('수정되었습니다.');
        history.replaceState({}, null, location.pathname);
        break;
    case 'modify-fail' :
        alert('수정에 실패했습니다.');
        history.replaceState({}, null, location.pathname);
        break;
    case 'delete-fail' :
        alert('삭제에 실패했습니다.');
        history.replaceState({}, null, location.pathname);
        break;
    case 'delete-success' :
        alert('삭제되었습니다.');
        history.replaceState({}, null, location.pathname);
        break;

}


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

        // 파일을 읽어서 이미지를 미리보기에 표시합니다.
        reader.onload = function(e) {
            inputImage.innerHTML = '<img src="' + e.target.result + '" alt="이미지 미리보기" class="rounded-circle" style="width: 100%; height:100%; object-fit: cover; cursor: pointer">';
        };

        // 파일을 읽습니다.
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

        // 이미지를 미리보기에 표시
        reader.onload = function(e) {
            modifyImage.innerHTML = '<img src="' + e.target.result + '" alt="이미지 미리보기" class="rounded-circle" style="width: 100%; height:100%; object-fit: cover; cursor: pointer">';
        };

        //  파일을 읽어옴
        reader.readAsDataURL(modifyFile.files[0]);
    }
})


function goToStudentInfo(idx){
    location.href = "/lesson/studentDetail?idx=" + idx;
}

function goToLessonInfo(idx){
    location.href = "/lesson/detail?idx=" + idx;
}




// nav 활성화
// document.querySelector(".teacherManagement").classList.add("active");
// document.querySelector(".memberManagement").classList.add("active");
