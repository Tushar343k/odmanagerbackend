package com.college.odmanager.service;

import com.college.odmanager.model.Student;
import com.college.odmanager.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;


    // =========================
    // GET ALL STUDENTS
    // =========================

    public List<Student> getStudents() {

        return repository.findAll();
    }


    // =========================
    // GET STUDENT BY ID
    // =========================

    public Student getStudentById(int id) {

        return repository.findById(id).orElse(null);
    }


    // =========================
    // ADD SINGLE STUDENT
    // =========================

    public Student addStudent(Student student) {

        return repository.save(student);
    }


    // =========================
    // ADD MULTIPLE STUDENTS
    // Used for Excel Upload
    // =========================

    public List<Student> addStudents(List<Student> students) {

        return repository.saveAll(students);
    }


    // =========================
    // UPDATE STUDENT
    // =========================

    public Student updateStudent(Student student) {

        return repository.save(student);
    }


    // =========================
    // DELETE STUDENT
    // =========================

    public void deleteStudent(int id) {

        repository.deleteById(id);
    }


    // =====================================================
    // PAGINATION + FILTERING
    // =====================================================

    public Page<Student> getStudentsWithFilters(
            String course,
            String branch,
            Integer year,
            String sec,
            String eventName,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime,
            int page,
            int size) {


        // Create pagination object
        Pageable pageable = PageRequest.of(page, size);


        // Send filters + pagination to repository
        return repository.findStudentsWithFilters(
                course,
                branch,
                year,
                sec,
                eventName,
                eventDate,
                startTime,
                endTime,
                pageable
        );
    }


    // =====================================================
    // DROPDOWN - COURSE
    // =====================================================

    public List<String> getCourses() {

        return repository.findDistinctCourses();
    }


    // =====================================================
    // DROPDOWN - BRANCH
    // =====================================================

    public List<String> getBranches(String course) {

        return repository.findDistinctBranches(course);
    }


    // =====================================================
    // DROPDOWN - YEAR
    // =====================================================

    public List<Integer> getYears(
            String course,
            String branch) {

        return repository.findDistinctYears(
                course,
                branch
        );
    }


    // =====================================================
    // DROPDOWN - SECTION
    // =====================================================

    public List<String> getSections(
            String course,
            String branch,
            Integer year) {

        return repository.findDistinctSections(
                course,
                branch,
                year
        );
    }


    // =====================================================
    // DROPDOWN - EVENT
    // =====================================================

    public List<String> getEvents(
            String course,
            String branch,
            Integer year,
            String sec) {

        return repository.findDistinctEvents(
                course,
                branch,
                year,
                sec
        );
    }


    // =====================================================
    // DROPDOWN - EVENT DATE
    // =====================================================

    public List<LocalDate> getEventDates(
            String course,
            String branch,
            Integer year,
            String sec,
            String eventName) {

        return repository.findDistinctEventDates(
                course,
                branch,
                year,
                sec,
                eventName
        );
    }


    // =====================================================
    // DROPDOWN - START TIME
    // =====================================================

    public List<LocalTime> getStartTimes(
            String course,
            String branch,
            Integer year,
            String sec,
            String eventName,
            LocalDate eventDate) {

        return repository.findDistinctStartTimes(
                course,
                branch,
                year,
                sec,
                eventName,
                eventDate
        );
    }


    // =====================================================
    // DROPDOWN - END TIME
    // =====================================================

    public List<LocalTime> getEndTimes(
            String course,
            String branch,
            Integer year,
            String sec,
            String eventName,
            LocalDate eventDate,
            LocalTime startTime) {

        return repository.findDistinctEndTimes(
                course,
                branch,
                year,
                sec,
                eventName,
                eventDate,
                startTime
        );
    }
    public List<Student> getAllStudentsWithFilters(
            String course, String branch, Integer year, String sec,
            String eventName, LocalDate eventDate,
            LocalTime startTime, LocalTime endTime) {

        return repository.findAllStudentsWithFilters(
                course, branch, year, sec,
                eventName, eventDate, startTime, endTime
        );
    }
}