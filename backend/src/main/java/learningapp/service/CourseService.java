package learningapp.service;

import learningapp.model.Course;
import learningapp.repository.CourseRepository;
import learningapp.service.interfaces.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    private final CourseRepository repository;

    @Autowired
    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Course> findAllCourses() {
        return repository.findAll();
    }

    @Override
    public Optional<Course> findCourseById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Course createCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        existingCourse.setNativeLanguage(course.getNativeLanguage());
        existingCourse.setForeignLanguages(course.getForeignLanguages());

        return repository.save(existingCourse);
    }

    @Override
    public Optional<Course> deleteCourseById(Long id) {
        return repository.findById(id)
                .map(course -> {
                    repository.delete(course);
                    return course;
                });
    }
}
