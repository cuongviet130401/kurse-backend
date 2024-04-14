package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.CourseRatingRecord;
import edu.bitble.kurse.model.mapping.CourseRatingRecordMapper;
import edu.bitble.kurse.model.persistence.CourseRatingRecordEntity;
import edu.bitble.kurse.model.request.CourseRatingRecordRequest;
import edu.bitble.kurse.repository.CourseRatingRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseRatingRecordService {

    private final CourseRatingRecordRepository repository;

    public List<CourseRatingRecord> getAll() {
			return repository.findAll()
                .stream()
                .map(CourseRatingRecordMapper.INSTANCE::toDto)
                .toList();
		}

		public CourseRatingRecord get(Integer id) {
			return repository.findById(id)
                .map(CourseRatingRecordMapper.INSTANCE::toDto)
                .orElseThrow();
		}

		public List<CourseRatingRecord> search(String query) {
			return repository.findAllByNameContainsIgnoreCase(query)
                .stream()
                .map(CourseRatingRecordMapper.INSTANCE::toDto)
                .toList();
		}

		public CourseRatingRecord create(CourseRatingRecordRequest req) {
			// TODO: precondition?

			// TODO: processing logic

			var persistedCourseRatingRecord = repository.save(
				CourseRatingRecordMapper.INSTANCE.toPersistence(req)
			);

			return CourseRatingRecordMapper.INSTANCE.toDto(persistedCourseRatingRecord);
		}

		public CourseRatingRecord update(Integer id, CourseRatingRecordRequest req) {
			var persistedCourseRatingRecord = getEntity(id);

			// mapping rule

			var updatedEntity = repository.save(
				CourseRatingRecordMapper.INSTANCE.toPersistence(req)
			);
			return CourseRatingRecordMapper.INSTANCE.toDto(updatedEntity);
		}

		public CourseRatingRecord delete(Integer id) {
			var persistedCourseRatingRecord = getEntity(id);
			repository.delete(persistedCourseRatingRecord);
			return CourseRatingRecordMapper.INSTANCE.toDto(persistedCourseRatingRecord);
		}

		private CourseRatingRecordEntity getEntity(Integer id) {
			return repository.findById(id).orElseThrow();
		}

}