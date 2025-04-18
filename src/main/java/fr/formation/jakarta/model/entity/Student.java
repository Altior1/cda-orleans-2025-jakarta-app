package fr.formation.jakarta.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Course> courses;

    public Student() {
        this.courses = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Student setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Student setCourses(List<Course> courses) {
        this.courses = courses;
        return this;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        if (courses.contains(course)) {
            course.removeStudent(this);
            courses.remove(course);
        }
    }


}
