package com.college.odmanager.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_regno_eventdate_starttime",
                        columnNames = {"reg_no", "event_date", "start_time"}
                )
        }
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String studentName;

    private String reg_no;

    private String course;

    private String branch;

    private Integer year;

    private Integer sem;

    private String sec;

    private String event_name;

    private LocalDate event_date;

    private LocalTime start_time;

    private LocalTime end_time;
}