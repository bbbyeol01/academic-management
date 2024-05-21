function selectType(select) {
    const selectedType = select.value;
    // 선택된 select 요소의 부모 요소를 찾아서 해당 부모에 속한 objectiveInputs와 subjectiveInputs를 제어합니다.
    const parent = select.parentElement.parentElement.parentElement.parentElement;
    // 객관식 입력 필드 보이기/숨기기
    const objectiveInputs = parent.querySelector('.objectiveInputs');
    if (selectedType === '객관식') {
        objectiveInputs.style.display = 'block';
    } else {
        objectiveInputs.style.display = 'none';
    }
    // 주관식 입력 필드 보이기/숨기기
    const subjectiveInputs = parent.querySelector('.subjectiveInputs');
    if (selectedType === '주관식') {
        subjectiveInputs.style.display = 'block';
    } else {
        subjectiveInputs.style.display = 'none';
    }
}