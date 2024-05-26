package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.Payment;
import edu.bitble.kurse.model.mapping.PaymentMapper;
import edu.bitble.kurse.model.persistence.PaymentEntity;
import edu.bitble.kurse.model.request.PaymentRequest;
import edu.bitble.kurse.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    public List<Payment> getAll() {
			return repository.findAll()
                .stream()
                .map(PaymentMapper.INSTANCE::toDto)
                .toList();
		}

		public Payment get(Integer id) {
			return repository.findById(id)
                .map(PaymentMapper.INSTANCE::toDto)
                .orElseThrow();
		}

		public Payment create(PaymentRequest req) {
			// TODO: precondition?

			// TODO: processing logic

			var persistedPayment = repository.save(
				PaymentMapper.INSTANCE.toPersistence(req)
			);

			return PaymentMapper.INSTANCE.toDto(persistedPayment);
		}

		public Payment update(Integer id, PaymentRequest req) {
			var persistedPayment = getEntity(id);

			// mapping rule

			var updatedEntity = repository.save(
				PaymentMapper.INSTANCE.toPersistence(req)
			);
			return PaymentMapper.INSTANCE.toDto(updatedEntity);
		}

		public Payment delete(Integer id) {
			var persistedPayment = getEntity(id);
			repository.delete(persistedPayment);
			return PaymentMapper.INSTANCE.toDto(persistedPayment);
		}

		private PaymentEntity getEntity(Integer id) {
			return repository.findById(id).orElseThrow();
		}

}