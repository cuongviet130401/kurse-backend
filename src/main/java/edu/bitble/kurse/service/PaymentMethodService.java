package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.PaymentMethod;
import edu.bitble.kurse.model.mapping.PaymentMethodMapper;
import edu.bitble.kurse.model.persistence.PaymentMethodEntity;
import edu.bitble.kurse.model.request.PaymentMethodRequest;
import edu.bitble.kurse.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository repository;

    public List<PaymentMethod> getAll() {
			return repository.findAll()
                .stream()
                .map(PaymentMethodMapper.INSTANCE::toDto)
                .toList();
		}

		public PaymentMethod get(Integer id) {
			return repository.findById(id)
                .map(PaymentMethodMapper.INSTANCE::toDto)
                .orElseThrow();
		}

		public PaymentMethod create(PaymentMethodRequest req) {
			// TODO: precondition?

			// TODO: processing logic

			var persistedPaymentMethod = repository.save(
				PaymentMethodMapper.INSTANCE.toPersistence(req)
			);

			return PaymentMethodMapper.INSTANCE.toDto(persistedPaymentMethod);
		}

		public PaymentMethod update(Integer id, PaymentMethodRequest req) {
			var persistedPaymentMethod = getEntity(id);

			// mapping rule

			var updatedEntity = repository.save(
				PaymentMethodMapper.INSTANCE.toPersistence(req)
			);
			return PaymentMethodMapper.INSTANCE.toDto(updatedEntity);
		}

		public PaymentMethod delete(Integer id) {
			var persistedPaymentMethod = getEntity(id);
			repository.delete(persistedPaymentMethod);
			return PaymentMethodMapper.INSTANCE.toDto(persistedPaymentMethod);
		}

		private PaymentMethodEntity getEntity(Integer id) {
			return repository.findById(id).orElseThrow();
		}

}