//----------------------------------------------------유형----------------------------------------------------
//유형 추가
async function categoryInsert(name){
    const response = await axios.post(`/training/category/insert`, name)
    return response.data
}

//유형 수정
async function categoryUpdate(updateCategory){
    await axios.put(`/training/category/${updateCategory.idx}`, updateCategory)
}

//유형 삭제
async function categoryDelete(idx){
    await axios.delete(`/training/category/${idx.idx}`)
}

//----------------------------------------------------과목----------------------------------------------------
//과목 추가
async function subjectInsert(name, method){
    const response = await axios.post(`/training/subject/insert`, name, method)
    return response.data
}

//과목 수정
async function subjectUpdate(updateSubject){
    await axios.put(`/training/subject/${updateSubject.idx}`, updateSubject)
}

//과목 삭제
async function subjectDelete(idx){
    await axios.delete(`/training/subject/${idx.idx}`)
}

//----------------------------------------------------과정----------------------------------------------------
//과정 추가
// async function curriculumInsert(insert){
//     const response = await axios.post(`/training/curriculum/insert`,null,{param : {name:insert.name, category:insert.category, time:insert.time, day:insert.day}})
//     return response.data
// }

//과정 수정
async function curriculumUpdate(updateCurriculum){
    await axios.put(`/training/curriculum/${updateCurriculum.idx}`, updateCurriculum)
}

//과정 삭제
async function curriculumDelete(idx){
    await axios.delete(`/training/curriculum/${idx.idx}`)
}

//----------------------------------------------------수업----------------------------------------------------

//수업 삭제
async function lessonDelete(idx){
    await axios.delete(`/training/lesson/${idx}`)
}

//---------------------------------------------------캘린더----------------------------------------------------

//해당 날짜에 시작하는 수업이나 종료하는 수업 있는지 확인
async function findLessonDate(today){
    const result = await axios.post(`/findLessonDate`, today)
    return result.data
}

async function calendarStartDay(today){
    const result = await axios.post(`/calendarStartDay/${today}`, today)
    return result.data
}

async function calendarEndDay(today){
    const result = await axios.post(`/calendarEndDay/${today}`, today)
    return result.data
}

