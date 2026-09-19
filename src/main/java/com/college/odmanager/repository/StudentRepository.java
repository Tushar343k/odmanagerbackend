package com.college.odmanager.repository;

import com.college.odmanager.model.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    boolean existsByReg_noAndEvent_dateAndStart_time(
            String reg_no,
            LocalDate event_date,
            LocalTime start_time
    );


    // =====================================================
// PAGINATION + FILTERING
// =====================================================

    @Query("""
    SELECT s
    FROM Student s
    WHERE s.course = COALESCE(:course, s.course)
    AND s.branch = COALESCE(:branch, s.branch)
    AND s.year = COALESCE(:year, s.year)
    AND s.sec = COALESCE(:sec, s.sec)
    AND s.event_name = COALESCE(:eventName, s.event_name)
    AND s.event_date = COALESCE(:eventDate, s.event_date)
    AND s.start_time = COALESCE(:startTime, s.start_time)
    AND s.end_time = COALESCE(:endTime, s.end_time)
    ORDER BY s.event_date DESC, s.start_time DESC
    """)
    Page<Student> findStudentsWithFilters(

            @Param("course")
            String course,

            @Param("branch")
            String branch,

            @Param("year")
            Integer year,

            @Param("sec")
            String sec,

            @Param("eventName")
            String eventName,

            @Param("eventDate")
            LocalDate eventDate,

            @Param("startTime")
            LocalTime startTime,

            @Param("endTime")
            LocalTime endTime,

            Pageable pageable
    );


// =====================================================
// Download Excel
// =====================================================

    @Query("""
    SELECT s
    FROM Student s
    WHERE s.course = COALESCE(:course, s.course)
    AND s.branch = COALESCE(:branch, s.branch)
    AND s.year = COALESCE(:year, s.year)
    AND s.sec = COALESCE(:sec, s.sec)
    AND s.event_name = COALESCE(:eventName, s.event_name)
    AND s.event_date = COALESCE(:eventDate, s.event_date)
    AND s.start_time = COALESCE(:startTime, s.start_time)
    AND s.end_time = COALESCE(:endTime, s.end_time)
    ORDER BY s.event_date DESC, s.start_time DESC
    """)
    List<Student> findAllStudentsWithFilters(

            @Param("course")
            String course,

            @Param("branch")
            String branch,

            @Param("year")
            Integer year,

            @Param("sec")
            String sec,

            @Param("eventName")
            String eventName,

            @Param("eventDate")
            LocalDate eventDate,

            @Param("startTime")
            LocalTime startTime,

            @Param("endTime")
            LocalTime endTime
    );


    // =====================================================
    // COURSE DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.course
        FROM Student s
        ORDER BY s.course
        """)
    List<String> findDistinctCourses();


    // =====================================================
    // BRANCH DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.branch
        FROM Student s
        WHERE s.course = :course
        ORDER BY s.branch
        """)
    List<String> findDistinctBranches(
            @Param("course")
            String course
    );


    // =====================================================
    // YEAR DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.year
        FROM Student s
        WHERE s.course = :course
        AND s.branch = :branch
        ORDER BY s.year
        """)
    List<Integer> findDistinctYears(

            @Param("course")
            String course,

            @Param("branch")
            String branch
    );


    // =====================================================
    // SECTION DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.sec
        FROM Student s
        WHERE s.course = :course
        AND s.branch = :branch
        AND s.year = :year
        ORDER BY s.sec
        """)
    List<String> findDistinctSections(

            @Param("course")
            String course,

            @Param("branch")
            String branch,

            @Param("year")
            Integer year
    );


    // =====================================================
    // EVENT DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.event_name
        FROM Student s
        WHERE s.course = :course
        AND s.branch = :branch
        AND s.year = :year
        AND s.sec = :sec
        ORDER BY s.event_name
        """)
    List<String> findDistinctEvents(

            @Param("course")
            String course,

            @Param("branch")
            String branch,

            @Param("year")
            Integer year,

            @Param("sec")
            String sec
    );


    // =====================================================
    // EVENT DATE DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.event_date
        FROM Student s
        WHERE s.course = :course
        AND s.branch = :branch
        AND s.year = :year
        AND s.sec = :sec
        AND s.event_name = :eventName
        ORDER BY s.event_date DESC
        """)
    List<LocalDate> findDistinctEventDates(

            @Param("course")
            String course,

            @Param("branch")
            String branch,

            @Param("year")
            Integer year,

            @Param("sec")
            String sec,

            @Param("eventName")
            String eventName
    );


    // =====================================================
    // START TIME DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.start_time
        FROM Student s
        WHERE s.course = :course
        AND s.branch = :branch
        AND s.year = :year
        AND s.sec = :sec
        AND s.event_name = :eventName
        AND s.event_date = :eventDate
        ORDER BY s.start_time
        """)
    List<LocalTime> findDistinctStartTimes(

            @Param("course")
            String course,

            @Param("branch")
            String branch,

            @Param("year")
            Integer year,

            @Param("sec")
            String sec,

            @Param("eventName")
            String eventName,

            @Param("eventDate")
            LocalDate eventDate
    );


    // =====================================================
    // END TIME DROPDOWN
    // =====================================================

    @Query("""
        SELECT DISTINCT s.end_time
        FROM Student s
        WHERE s.course = :course
        AND s.branch = :branch
        AND s.year = :year
        AND s.sec = :sec
        AND s.event_name = :eventName
        AND s.event_date = :eventDate
        AND s.start_time = :startTime
        ORDER BY s.end_time
        """)
    List<LocalTime> findDistinctEndTimes(

            @Param("course")
            String course,

            @Param("branch")
            String branch,

            @Param("year")
            Integer year,

            @Param("sec")
            String sec,

            @Param("eventName")
            String eventName,

            @Param("eventDate")
            LocalDate eventDate,

            @Param("startTime")
            LocalTime startTime
    );

}
