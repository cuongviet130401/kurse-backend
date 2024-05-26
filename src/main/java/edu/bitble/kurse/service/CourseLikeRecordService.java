package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.CourseLikeRecord;
import edu.bitble.kurse.model.mapping.CourseLikeRecordMapper;
import edu.bitble.kurse.model.persistence.CourseLikeRecordEntity;
import edu.bitble.kurse.model.request.CourseLikeRecordRequest;
import edu.bitble.kurse.repository.CourseLikeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseLikeRecordService {

    private final CourseLikeRecordRepository repository;

    public List<CourseLikeRecord> getAll() {
			return repository.findAll()
                .stream()
                .map(CourseLikeRecordMapper.INSTANCE::toDto)
                .toList();
		}

		public CourseLikeRecord get(Integer id) {
			return repository.findById(id)
                .map(CourseLikeRecordMapper.INSTANCE::toDto)
                .orElseThrow();
		}

		public CourseLikeRecord create(CourseLikeRecordRequest req) {
			// TODO: precondition?

			// TODO: processing logic

			var persistedCourseLikeRecord = repository.save(
				CourseLikeRecordMapper.INSTANCE.toPersistence(req)
			);

			return CourseLikeRecordMapper.INSTANCE.toDto(persistedCourseLikeRecord);
		}

		public CourseLikeRecord update(Integer id, CourseLikeRecordRequest req) {
			var persistedCourseLikeRecord = getEntity(id);

			// mapping rule

			var updatedEntity = repository.save(
				CourseLikeRecordMapper.INSTANCE.toPersistence(req)
			);
			return CourseLikeRecordMapper.INSTANCE.toDto(updatedEntity);
		}

		public CourseLikeRecord delete(Integer id) {
			var persistedCourseLikeRecord = getEntity(id);
			repository.delete(persistedCourseLikeRecord);
			return CourseLikeRecordMapper.INSTANCE.toDto(persistedCourseLikeRecord);
		}

		private CourseLikeRecordEntity getEntity(Integer id) {
			return repository.findById(id).orElseThrow();
		}

}