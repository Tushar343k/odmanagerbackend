package com.college.odmanager.controller;

import com.college.odmanager.model.Student;
import com.college.odmanager.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;


    // =====================================================
    // GET ALL STUDENTS
    // =====================================================

    @GetMapping
    public List<Student> getStudents() {

        return service.getStudents();
    }


    // =====================================================
    // GET STUDENT BY ID
    // =====================================================

    @GetMapping("/{id}")
    public Student getStudentById(
            @PathVariable int id) {

        return service.getStudentById(id);
    }


    // =====================================================
    // ADD MULTIPLE STUDENTS
    // Used for Excel Upload
    // =====================================================

    @PostMapping
    public List<Student> addStudents(
            @RequestBody List<Student> students) {

        return service.addStudents(students);
    }


    // =====================================================
    // UPDATE STUDENT
    // =====================================================

    @PutMapping
    public Student updateStudent(
            @RequestBody Student student) {

        return service.updateStudent(student);
    }

    // =====================================================
    // DELETE ALL STUDENT
    // =====================================================

    @DeleteMapping("/delete-all")
    public void deleteAllStudents() {

        service.deleteAllStudents();
    }


    // =====================================================
    // DELETE STUDENT
    // =====================================================

    @DeleteMapping("/{id}")
    public void deleteStudent(
            @PathVariable int id) {

        service.deleteStudent(id);
    }

    

    // =====================================================
    // PAGINATION + FILTERING
    // =====================================================

    @GetMapping("/page")
    public Page<Student> getStudentsWithFilters(

            @RequestParam(required = false)
            String course,

            @RequestParam(required = false)
            String branch,

            @RequestParam(required = false)
            Integer year,

            @RequestParam(required = false)
            String sec,

            @RequestParam(required = false)
            String eventName,

            @RequestParam(required = false)
            String eventDate,

            @RequestParam(required = false)
            String startTime,

            @RequestParam(required = false)
            String endTime,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "50")
            int size) {


        // ---------------------------------------------
        // Convert String date to LocalDate
        // ---------------------------------------------

        LocalDate date = null;

        if (eventDate != null &&
                !eventDate.isEmpty()) {

            date = LocalDate.parse(eventDate);
        }


        // ---------------------------------------------
        // Convert String start time to LocalTime
        // ---------------------------------------------

        LocalTime start = null;

        if (startTime != null &&
                !startTime.isEmpty()) {

            start = LocalTime.parse(startTime);
        }


        // ---------------------------------------------
        // Convert String end time to LocalTime
        // ---------------------------------------------

        LocalTime end = null;

        if (endTime != null &&
                !endTime.isEmpty()) {

            end = LocalTime.parse(endTime);
        }


        // ---------------------------------------------
        // Call Service
        // ---------------------------------------------

        return service.getStudentsWithFilters(

                course,
                branch,
                year,
                sec,
                eventName,
                date,
                start,
                end,
                page,
                size
        );
    }


    // =====================================================
    // GET DISTINCT COURSES
    // =====================================================

    @GetMapping("/courses")
    public List<String> getCourses() {

        return service.getCourses();
    }


    // =====================================================
    // GET DISTINCT BRANCHES
    // =====================================================

    @GetMapping("/branches")
    public List<String> getBranches(

            @RequestParam String course) {

        return service.getBranches(course);
    }


    // =====================================================
    // GET DISTINCT YEARS
    // =====================================================

    @GetMapping("/years")
    public List<Integer> getYears(

            @RequestParam String course,

            @RequestParam String branch) {

        return service.getYears(
                course,
                branch
        );
    }


    // =====================================================
    // GET DISTINCT SECTIONS
    // =====================================================

    @GetMapping("/sections")
    public List<String> getSections(

            @RequestParam String course,

            @RequestParam String branch,

            @RequestParam Integer year) {

        return service.getSections(
                course,
                branch,
                year
        );
    }


    // =====================================================
    // GET DISTINCT EVENTS
    // =====================================================

    @GetMapping("/events")
    public List<String> getEvents(

            @RequestParam String course,

            @RequestParam String branch,

            @RequestParam Integer year,

            @RequestParam String sec) {

        return service.getEvents(
                course,
                branch,
                year,
                sec
        );
    }


    // =====================================================
    // GET DISTINCT EVENT DATES
    // =====================================================

    @GetMapping("/event-dates")
    public List<LocalDate> getEventDates(

            @RequestParam String course,

            @RequestParam String branch,

            @RequestParam Integer year,

            @RequestParam String sec,

            @RequestParam String eventName) {

        return service.getEventDates(
                course,
                branch,
                year,
                sec,
                eventName
        );
    }


    // =====================================================
    // GET DISTINCT START TIMES
    // =====================================================

    @GetMapping("/start-times")
    public List<LocalTime> getStartTimes(

            @RequestParam String course,

            @RequestParam String branch,

            @RequestParam Integer year,

            @RequestParam String sec,

            @RequestParam String eventName,

            @RequestParam String eventDate) {


        LocalDate date =
                LocalDate.parse(eventDate);


        return service.getStartTimes(

                course,
                branch,
                year,
                sec,
                eventName,
                date
        );
    }


    // =====================================================
    // GET DISTINCT END TIMES
    // =====================================================

    @GetMapping("/end-times")
    public List<LocalTime> getEndTimes(

            @RequestParam String course,

            @RequestParam String branch,

            @RequestParam Integer year,

            @RequestParam String sec,

            @RequestParam String eventName,

            @RequestParam String eventDate,

            @RequestParam String startTime) {


        LocalDate date =
                LocalDate.parse(eventDate);


        LocalTime start =
                LocalTime.parse(startTime);


        return service.getEndTimes(

                course,
                branch,
                year,
                sec,
                eventName,
                date,
                start
        );
    }
    //Download Excel
    @GetMapping("/download")
    public List<Student> downloadStudents(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String sec,
            @RequestParam(required = false) String eventName,
            @RequestParam(required = false) String eventDate,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {

        LocalDate date = null;
        LocalTime start = null;
        LocalTime end = null;

        if (eventDate != null && !eventDate.isEmpty()) {
            date = LocalDate.parse(eventDate);
        }

        if (startTime != null && !startTime.isEmpty()) {
            start = LocalTime.parse(startTime);
        }

        if (endTime != null && !endTime.isEmpty()) {
            end = LocalTime.parse(endTime);
        }

        return service.getAllStudentsWithFilters(
                course,
                branch,
                year,
                sec,
                eventName,
                date,
                start,
                end
        );
    }

}
