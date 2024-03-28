package com.ezen.management.repository;

import com.ezen.management.domain.Lesson;
import com.ezen.management.domain.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @EntityGraph(attributePaths = "lesson")
    @Query("select s from Student s where s.lesson = :lesson and s.name = :name")
    Optional<Student> getByLessonAndName(Lesson lesson, String name);
}