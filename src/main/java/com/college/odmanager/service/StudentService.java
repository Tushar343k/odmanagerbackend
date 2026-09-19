package com.college.odmanager.service;

import com.college.odmanager.dto.UploadResponse;
import com.college.odmanager.model.Student;
import com.college.odmanager.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public UploadResponse addStudents(List<Student> students) {

        Set<String> uploadedRecords = new HashSet<>();

        List<Student> newStudents = students.stream()

                // Remove duplicate records from same Excel file
                .filter(student -> {

                    String key =
                            student.getReg_no() + "|" +
                                    student.getEvent_date() + "|" +
                                    student.getStart_time();

                    return uploadedRecords.add(key);
                })

                // Skip records already present in database
                .filter(student ->
                        !repository.existsByRegNoAndEventDateAndStartTime(
                                student.getReg_no(),
                                student.getEvent_date(),
                                student.getStart_time()
                        )
                )

                .toList();

        repository.saveAll(newStudents);

        int inserted = newStudents.size();

        int skipped = students.size() - inserted;

        return new UploadResponse(inserted, skipped);
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


    // =========================
    // DELETE ALL STUDENTS
    // =========================

    public void deleteAllStudents() {

        repository.deleteAll();
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


    // =====================================================
    // DOWNLOAD EXCEL
    // =====================================================

    public List<Student> getAllStudentsWithFilters(
            String course,
            String branch,
            Integer year,
            String sec,
            String eventName,
            LocalDate eventDate,
            LocalTime startTime,
            LocalTime endTime) {

        return repository.findAllStudentsWithFilters(
                course,
                branch,
                year,
                sec,
                eventName,
                eventDate,
                startTime,
                endTime
        );
    }
}

