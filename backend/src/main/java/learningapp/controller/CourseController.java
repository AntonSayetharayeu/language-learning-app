package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.CourseRequestDTO;
import learningapp.dto.responce.CourseResponseDTO;
import learningapp.mapper.CourseMapper;
import learningapp.service.interfaces.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final ICourseService courseService;

    private final CourseMapper courseMapper;

    @Autowired
    public CourseController(ICourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAllCourses()
                .stream()
                .map(courseMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseByID(@PathVariable Long id) {
        return courseService.findCourseById(id)
                .map(course -> ResponseEntity.ok(courseMapper.toResponseDTO(course)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> addNewCourse(@Valid @RequestBody CourseRequestDTO dto) {
        return ResponseEntity.ok(
                courseMapper.toResponseDTO(
                        courseService.createCourse(courseMapper.toEntity(dto))));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO dto) {
        return ResponseEntity.ok(
                courseMapper.toResponseDTO(
                        courseService.updateCourse(id, courseMapper.toEntity(dto))));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<CourseResponseDTO> removeCourse(@PathVariable Long id) {
        return courseService.deleteCourseById(id)
                .map(course -> ResponseEntity.ok(courseMapper.toResponseDTO(course)))
                .orElse(ResponseEntity.notFound().build());
    }
}
