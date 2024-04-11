package com.ezen.management.service;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Student;
import com.ezen.management.domain.SurveyAnswer;
import com.ezen.management.dto.StudentDTO;
import com.ezen.management.dto.SurveyAnswerDTO;
import com.ezen.management.dto.SurveyResultDTO;
import com.ezen.management.repository.LessonRepository;
import com.ezen.management.repository.StudentRepository;
import com.ezen.management.repository.SurveyAnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
//@Transactional //일단 보류해놓자
public class SurveyAnswerServiceImpl implements SurveyAnswerService{

    private final SurveyAnswerRepository surveyAnswerRepository;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final TrainingService trainingService;


    @Override
    public int insert(SurveyAnswerDTO surveyAnswerDTO, StudentDTO studentDTO) {

        // SurveyAnswerDTO에서 lesson_idx를 가져와서 Lesson 엔티티를 조회
        Optional<Lesson> lessonOptional = lessonRepository.findById(studentDTO.getLessonIdx());

        // Lesson 엔티티가 존재하는 경우에만 처리
        if (lessonOptional.isPresent()) {
            Lesson lesson = lessonOptional.get();

            surveyAnswerDTO.setLesson(lesson);

            // SurveyAnswerDTO를 SurveyAnswer 엔티티로 변환
            SurveyAnswer surveyAnswer = surveyAnswerDtoToEntity(surveyAnswerDTO);

            // SurveyAnswer를 저장
            surveyAnswerRepository.save(surveyAnswer);

            // Student 엔티티를 가져옴
            Optional<Student> studentOptional = studentRepository.findById(studentDTO.getIdx());

            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();

                student.insertSurvey();

                // 해당 round에 따라서 Survey 정보를 업데이트 -> studentEntity 활용해야 함
                studentRepository.save(student);

            } else {
                // Student not found
            }

            return 1;
        } else {
            // Lesson이 존재하지 않는 경우에 대한 처리를 추가

            return 0;
        }
    }

    @Override
    public List<SurveyAnswerDTO> findByLessonIdx(Long lessonIdx) {

        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findByLessonIdx(lessonIdx);
        List<SurveyAnswerDTO> surveyAnswerDTOList = new ArrayList<>();

        for (SurveyAnswer surveyAnswer : surveyAnswerList) {
            SurveyAnswerDTO surveyAnswerDTO = surveyAnswerEntityToDTO(surveyAnswer);
            surveyAnswerDTOList.add(surveyAnswerDTO);
        }

        return surveyAnswerDTOList;
    }

    @Override
    public List<SurveyResultDTO> calculateSumOfAnswers(int round, Long lessonIndex) {
        List<Object[]> objects = surveyAnswerRepository.calculateSumOfAnswers(round, lessonIndex);

        log.info("여기는 서비스 임플" + objects);

        List<SurveyResultDTO> surveyResultDTOList = new ArrayList<>();

        // SurveyResultDTO로 변환하여 결과를 저장
        for (Object[] obj : objects) {
            SurveyResultDTO surveyResultDTO = new SurveyResultDTO();

            if (obj != null && obj.length >= 8) {
                surveyResultDTO.setAn(obj[0] != null ? String.valueOf(obj[0]) : "default"); // 첫 번째 컬럼은 an 값
                surveyResultDTO.setSumOf1(obj[1] != null ? Integer.parseInt(String.valueOf(obj[1])) : 0); // 두 번째 컬럼은 sum_of_1 값
                surveyResultDTO.setSumOf2(obj[2] != null ? Integer.parseInt(String.valueOf(obj[2])) : 0); // 세 번째 컬럼은 sum_of_2 값
                surveyResultDTO.setSumOf3(obj[3] != null ? Integer.parseInt(String.valueOf(obj[3])) : 0); // 네 번째 컬럼은 sum_of_3 값
                surveyResultDTO.setSumOf4(obj[4] != null ? Integer.parseInt(String.valueOf(obj[4])) : 0); // 다섯 번째 컬럼은 sum_of_4 값
                surveyResultDTO.setSumOf5(obj[5] != null ? Integer.parseInt(String.valueOf(obj[5])) : 0); // 여섯 번째 컬럼은 sum_of_5 값
                surveyResultDTO.setSumOf6(obj[6] != null ? Integer.parseInt(String.valueOf(obj[6])) : 0); // 일곱 번째 컬럼은 sum_of_6 값
                surveyResultDTO.setSumOf7(obj[7] != null ? Integer.parseInt(String.valueOf(obj[7])) : 0); // 여덟 번째 컬럼은 sum_of_7 값
            } else {
                // obj가 null이거나 length가 8보다 작을 경우 기본값을 설정
                // obj의 길이가 8보다 작으면 ArrayIndexOutOfBoundsException 발생
                surveyResultDTO.setAn("default");
                surveyResultDTO.setSumOf1(0);
                surveyResultDTO.setSumOf2(0);
                surveyResultDTO.setSumOf3(0);
                surveyResultDTO.setSumOf4(0);
                surveyResultDTO.setSumOf5(0);
                surveyResultDTO.setSumOf6(0);
                surveyResultDTO.setSumOf7(0);
            }

            surveyResultDTOList.add(surveyResultDTO);
        }

        log.info("리스트 찍어 보자" + surveyResultDTOList);

        return surveyResultDTOList;
    }

    @Override
    public List<SurveyAnswerDTO> findByLessonIdxAndRound(Long lessonIdx, int round) {

        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findByLessonIdxAndRound(lessonIdx,round);

        List<SurveyAnswerDTO> surveyAnswerDTOList = new ArrayList<>();

        for (SurveyAnswer surveyAnswer : surveyAnswerList) {
            SurveyAnswerDTO surveyAnswerDTO = surveyAnswerEntityToDTO(surveyAnswer);
            surveyAnswerDTOList.add(surveyAnswerDTO);
        }

        log.info("여기는 서비스의 find" + surveyAnswerDTOList);

        return surveyAnswerDTOList;
    }

}
