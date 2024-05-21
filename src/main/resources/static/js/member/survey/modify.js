document.addEventListener("DOMContentLoaded", function() {
    // 페이지가 로드될 때 각 답변보기 영역의 표시 여부 설정
    const selects = document.querySelectorAll('.form-select');
    selects.forEach(function(select) {
        selectType(select);
    });

    // 답변유형이 변경될 때마다 해당 영역을 숨기거나 표시
    selects.forEach(function(select) {
        select.addEventListener('change', function() {
            selectType(this);
        });
    });
});

function selectType(select) {
    let selectedType = select.value;
    // 선택된 select 요소의 부모 요소를 찾아서 해당 부모에 속한 objectiveInputs와 subjectiveInputs를 제어합니다.
    let parent = select.parentElement.parentElement.parentElement.parentElement;
    // 객관식 입력 필드 보이기/숨기기
    let objectiveInputs = parent.querySelector('.objectiveInputs');
    if (selectedType === '객관식') {
        objectiveInputs.style.display = 'block';
        // 객관식인 경우, 모든 item을 입력해야 함을 사용자에게 알려줌
        validateObjectiveInputs(parent);
    } else {
        objectiveInputs.style.display = 'none';
    }
    // 주관식 입력 필드 보이기/숨기기
    let subjectiveInputs = parent.querySelector('.subjectiveInputs');
    if (selectedType === '주관식') {
        subjectiveInputs.style.display = 'block';
        // 주관식인 경우, item을 입력할 필요가 없으므로 validation을 제거함
        clearValidation(parent);
    } else {
        subjectiveInputs.style.display = 'none';
    }
}

// 객관식 입력 필드가 모두 채워져 있는지 검증하는 함수
function validateObjectiveInputs(parent) {
    let objectiveInputs = parent.querySelector('.objectiveInputs');
    let inputs = objectiveInputs.querySelectorAll('input[type="text"]');
    for (let i = 0; i < inputs.length; i++) {
        if (inputs[i].value.trim() === '') {
            alert('객관식의 경우 모든 보기를 입력해야 합니다.');
            return false;
        }
    }
    return true;
}

// 입력 필드의 validation 메시지를 제거하는 함수
function clearValidation(parent) {
    let errorMessages = parent.querySelectorAll('.validation-message');
    errorMessages.forEach(function(errorMessage) {
        errorMessage.textContent = '';
    });
}