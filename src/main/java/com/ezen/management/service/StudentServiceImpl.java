package com.ezen.management.service;

import com.ezen.management.AlreadyExistException;
import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Student;
import com.ezen.management.domain.SubjectHold;
import com.ezen.management.domain.SubjectTest;
import com.ezen.management.dto.PageRequestDTO;
import com.ezen.management.dto.PageResponseDTO;
import com.ezen.management.dto.StudentDTO;
import com.ezen.management.repository.LessonRepository;
import com.ezen.management.repository.StudentRepository;
import com.ezen.management.repository.SubjectHoldRepository;
import com.ezen.management.repository.SubjectTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    @Value("${com.ezen.management.upload.path}")
    private String uploadPath;

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final SubjectHoldRepository subjectHoldRepository;
    private final SubjectTestRepository subjectTestRepository;

    @Override
    public Student findByLessonIdxAndName(Long lessonIdx, String name) {
        Optional<Lesson> result = lessonRepository.findById(lessonIdx);
        Lesson lesson = result.orElseThrow();

        Optional<Student> byLessonAndName = studentRepository.getByLessonAndName(lesson, name);

//        null 처리 해야함
        return byLessonAndName.get();

    }

    @Override
    public Student findById(Long studentIdx) {
        Optional<Student> byId = studentRepository.findById(studentIdx);
        Student student = byId.get();

        log.info("student : {}", student);


//        null 처리 해야함!
        return byId.get();
    }

    @Override
    public PageResponseDTO<Student> searchStudent(Long lessonIdx, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("regDate");

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Page<Student> studentPage = studentRepository.searchStudent(lessonIdx, types, keyword, pageable);

        List<Student> dtoList = studentPage.getContent();


//        return new PageImpl<Student>(dtoList, pageable, studentPage.getTotalElements());

        return PageResponseDTO.<Student>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)studentPage.getTotalElements())
                .build();

    }

    @Override
    public void insertStudent(StudentDTO studentDTO) {

        Optional<Lesson> byId = lessonRepository.findById(studentDTO.getLessonIdx());
        Lesson lesson = byId.get();


        Optional<Student> byLessonAndName = studentRepository.getByLessonAndName(lesson, studentDTO.getName());

        if(byLessonAndName.isPresent()){
           throw new AlreadyExistException("이미 존재하는 학생입니다.");
        }

        Student student = Student.builder()
                .lesson(lesson)
                .name(studentDTO.getName())
                .birthday(studentDTO.getBirthday())
                .email(studentDTO.getEmail())
                .phone(studentDTO.getPhone())
                .build();

        if(studentDTO.getFileName() != null && !studentDTO.getFileName().isEmpty()){
            student.changeFileName(studentDTO.getUuid(), studentDTO.getFileName());
        }

        if(studentDTO.getEtc() != null && !studentDTO.getEtc().isEmpty()){
            student.changeEtc(studentDTO.getEtc());
        }


        Student studentSave = studentRepository.save(student);
        lesson.headCountUp();
        lessonRepository.save(lesson);

        //과목평가 state = "X"로 insert
        List<String> subjectHoldList = subjectHoldRepository.getSubjectHoldByLesson_idx(lesson.getIdx());
        for(String subject : subjectHoldList){
            SubjectTest subjectTest = SubjectTest.builder()
                    .student(studentSave)
                    .subject(subject)
                    .state("X")
                    .build();
            log.info("서비스 과목명 : " + subject);

            subjectTestRepository.save(subjectTest);
        }
    }

    @Override
    public void modifyStudent(StudentDTO studentDTO) throws IOException {

        Optional<Student> byId = studentRepository.findById(studentDTO.getIdx());
        Student student = byId.orElseThrow();

        if(student.getUuid() != null){
            Resource resource = new FileSystemResource(uploadPath + File.separator + student.getUuid() + '_' + student.getFileName());

            try{
                resource.getFile().delete();
            }catch (Exception e){
                throw new IOException();
            }
        }

        log.info("student : {} ", student);

        student.changeName(studentDTO.getName());
        student.changeBirthday(studentDTO.getBirthday());
        student.changeEmail(studentDTO.getEmail());
        student.changePhone(studentDTO.getPhone());

        if(studentDTO.getUuid() != null){
            student.changeFileName(studentDTO.getUuid(), studentDTO.getFileName());
        }

        studentRepository.save(student);

    }

    @Override
    public void deleteStudent(StudentDTO studentDTO) throws IOException {
        Optional<Student> studentById = studentRepository.findById(studentDTO.getIdx());
        Student student = null;

        Optional<Lesson> lessonById = lessonRepository.findById(studentDTO.getLessonIdx());
        Lesson lesson = lessonById.orElseThrow();


        if(studentById.isPresent()){
            student = studentById.get();

        }

        if(student == null){
            throw new NoSuchElementException();
        }

//        파일 삭제
        if(student.getUuid() != null){
            Resource resource = new FileSystemResource(uploadPath + File.separator + student.getUuid() + '_' + student.getFileName());

            try{
                resource.getFile().delete();
            }catch (Exception e){
                throw new IOException();
            }
        }

        studentRepository.delete(student);
        lesson.headCountDown();
        lessonRepository.save(lesson);

    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }


}
