document.addEventListener('DOMContentLoaded', function() {

    /* 반복문을 통해 각 요소에 대한 그래프 생성 */
    surveyDTOList.forEach(function(surveyDTO, index) {
        var canvasId = 'bar-chart-' + (index + 1);
        var canvas = document.getElementById(canvasId);

        if (canvas) {
            var ctx = canvas.getContext('2d');

            if (surveyDTO.type === '객관식') {
                /* 객관식인 경우에만 차트 생성 */
                var surveyResultDTO = surveyResultDTOList[index];

                /* surveyResultDTO 객체에서 아이템들의 값을 배열로 추출 */
                var data = [
                    surveyResultDTO.sumOf1,
                    surveyResultDTO.sumOf2,
                    surveyResultDTO.sumOf3,
                    surveyResultDTO.sumOf4,
                    surveyResultDTO.sumOf5,
                    surveyResultDTO.sumOf6,
                    surveyResultDTO.sumOf7
                ];

                // 데이터 배열의 합계 계산
                var sum = data.reduce(function(a, b) {
                    return a + b;
                }, 0);

                // 합계 출력
                console.log('y축의 합계: ' + sum);

                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: [surveyDTO.item1,surveyDTO.item2,surveyDTO.item3,surveyDTO.item4,surveyDTO.item5,surveyDTO.item6,surveyDTO.item7],
                        datasets: [{
                            label: `응답자수`,
                            data: data,
                            backgroundColor: 'rgb(0,156,255)',
                            borderColor: 'rgb(47,90,236)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        maintainAspectRatio: false, // 가로 세로 비율을 자유롭게 조정
                        responsive: true, // 차트가 반응형으로 크기 조정되도록 설정
                        scales: {
                            y: {
                                beginAtZero: true // y축 시작값을 0으로 설정
                            }
                        }
                    }
                });

            }
        }
    });
});

var lessonIdx = document.getElementById('lessonIdx').textContent;
lessonIdx = JSON.parse(lessonIdx);

document.querySelector('.firstBtn').addEventListener('click', function() {
    var  round = 1;
    window.location.href = '/member/lesson/survey/result?lessonIdx=' + lessonIdx + '&round=' + round;
});

document.querySelector('.secondBtn').addEventListener('click', function() {
    var  round = 2;
    window.location.href = '/member/lesson/survey/result?lessonIdx=' + lessonIdx + '&round=' + round;
});

document.querySelector('.thirdBtn').addEventListener('click', function() {
    var  round = 3;
    window.location.href = '/member/lesson/survey/result?lessonIdx=' + lessonIdx + '&round=' + round;
});

const printBtn = document.querySelector("#printBtn");

printBtn.addEventListener("click", function () {

    window.print();
});