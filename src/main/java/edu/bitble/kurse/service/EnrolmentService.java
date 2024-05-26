package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.Enrolment;
import edu.bitble.kurse.model.mapping.EnrolmentMapper;
import edu.bitble.kurse.model.persistence.EnrolmentEntity;
import edu.bitble.kurse.model.request.EnrolmentRequest;
import edu.bitble.kurse.repository.EnrolmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrolmentService {

    private final EnrolmentRepository repository;

    public List<Enrolment> getAll() {
			return repository.findAll()
                .stream()
                .map(EnrolmentMapper.INSTANCE::toDto)
                .toList();
		}

		public Enrolment get(Integer id) {
			return repository.findById(id)
                .map(EnrolmentMapper.INSTANCE::toDto)
                .orElseThrow();
		}

		public Enrolment create(EnrolmentRequest req) {
			// TODO: precondition?

			// TODO: processing logic

			var persistedEnrolment = repository.save(
				EnrolmentMapper.INSTANCE.toPersistence(req)
			);

			return EnrolmentMapper.INSTANCE.toDto(persistedEnrolment);
		}

		public Enrolment update(Integer id, EnrolmentRequest req) {
			var persistedEnrolment = getEntity(id);

			// mapping rule

			var updatedEntity = repository.save(
				EnrolmentMapper.INSTANCE.toPersistence(req)
			);
			return EnrolmentMapper.INSTANCE.toDto(updatedEntity);
		}

		public Enrolment delete(Integer id) {
			var persistedEnrolment = getEntity(id);
			repository.delete(persistedEnrolment);
			return EnrolmentMapper.INSTANCE.toDto(persistedEnrolment);
		}

		private EnrolmentEntity getEntity(Integer id) {
			return repository.findById(id).orElseThrow();
		}

}