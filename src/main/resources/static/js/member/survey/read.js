document.addEventListener("DOMContentLoaded", function() {

    // 수정 버튼에 대한 이벤트 리스너 추가
    const modifyBtn = document.querySelector(".modifyBtn");
    if (modifyBtn) {
        // 수정 버튼에 대한 이벤트 리스너 추가
        modifyBtn.addEventListener("click", function() {
            const round = document.querySelector('h2 span').textContent.trim();
            window.location.href = 'modify?round=' + round;
        });
    }

    // 등록 버튼이 있는지 확인
    const registerBtn = document.querySelector(".registerBtn");
    if (registerBtn) {
        // 등록 버튼에 이벤트 리스너 추가
        registerBtn.addEventListener("click", function(e) {
            e.preventDefault();
            e.stopPropagation();

            // 현재 URL에서 round 값을 가져오기
            const urlParams = new URLSearchParams(window.location.search);
            const round = urlParams.get('round');

            // URL에 파라미터 추가하여 페이지 이동
            window.location.href = 'register?round=' + round;
        });
    }

    const firstBtn = document.querySelector(".firstBtn");
    const secondBtn = document.querySelector(".secondBtn");
    const thirdBtn = document.querySelector(".thirdBtn");

    if (firstBtn) {
        firstBtn.addEventListener("click", function() {
            // 버튼의 클래스를 변경하여 클릭된 상태를 표시
            firstBtn.classList.add("clicked");
            secondBtn.classList.remove("clicked");
            thirdBtn.classList.remove("clicked");

            const round = 1;
            window.location.href = '/member/survey/read?round=' + round;
        });

        secondBtn.addEventListener("click", function() {
            // 버튼의 클래스를 변경하여 클릭된 상태를 표시
            secondBtn.classList.add("clicked");
            firstBtn.classList.remove("clicked");
            thirdBtn.classList.remove("clicked");

            const round = 2;
            window.location.href = '/member/survey/read?round=' + round;
        });

        thirdBtn.addEventListener("click", function() {
            // 버튼의 클래스를 변경하여 클릭된 상태를 표시
            thirdBtn.classList.add("clicked");
            firstBtn.classList.remove("clicked");
            secondBtn.classList.remove("clicked");

            const round = 3;
            window.location.href = '/member/survey/read?round=' + round;
        });
    }
});