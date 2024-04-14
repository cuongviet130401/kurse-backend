package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.Course;
import edu.bitble.kurse.model.mapping.CourseMapper;
import edu.bitble.kurse.model.persistence.CourseEntity;
import edu.bitble.kurse.model.request.CourseRequest;
import edu.bitble.kurse.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<Course> getAll() {
			return repository.findAll()
                .stream()
                .map(CourseMapper.INSTANCE::toDto)
                .toList();
		}

		public Course get(Integer id) {
			return repository.findById(id)
                .map(CourseMapper.INSTANCE::toDto)
                .orElseThrow();
		}

		public List<Course> search(String query) {
			return repository.findAllByNameContainsIgnoreCase(query)
                .stream()
                .map(CourseMapper.INSTANCE::toDto)
                .toList();
		}

		public Course create(CourseRequest req) {
			// TODO: precondition?

			// TODO: processing logic

			var persistedCourse = repository.save(
				CourseMapper.INSTANCE.toPersistence(req)
			);

			return CourseMapper.INSTANCE.toDto(persistedCourse);
		}

		public Course update(Integer id, CourseRequest req) {
			var persistedCourse = getEntity(id);

			// mapping rule

			var updatedEntity = repository.save(
				CourseMapper.INSTANCE.toPersistence(req)
			);
			return CourseMapper.INSTANCE.toDto(updatedEntity);
		}

		public Course delete(Integer id) {
			var persistedCourse = getEntity(id);
			repository.delete(persistedCourse);
			return CourseMapper.INSTANCE.toDto(persistedCourse);
		}

		private CourseEntity getEntity(Integer id) {
			return repository.findById(id).orElseThrow();
		}

}