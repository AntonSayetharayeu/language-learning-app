package learningapp.service.interfaces;

import learningapp.model.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {

    List<Course> findAllCourses();

    Optional<Course> findCourseById(Long id);

    Course createCourse(Course course);

    Course updateCourse(Long id, Course course);

    Optional<Course> deleteCourseById(Long id);
}
