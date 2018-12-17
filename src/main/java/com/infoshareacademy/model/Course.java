package com.infoshareacademy.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COURSES")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course_name", length = 20)
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teachers;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Course{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", students=").append(students
            .stream()
            .map(Student::getId)
            .collect(toList())
        );
        sb.append(", teachers=")
                .append(teachers.stream()
                        .map(Teacher::getPesel)
                        .collect(toList()));
        sb.append('}');
        return sb.toString();
    }
}
