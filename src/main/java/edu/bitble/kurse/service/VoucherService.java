package edu.bitble.kurse.service;

import edu.bitble.kurse.model.dto.Voucher;
import edu.bitble.kurse.model.mapping.VoucherMapper;
import edu.bitble.kurse.model.persistence.VoucherEntity;
import edu.bitble.kurse.model.request.VoucherRequest;
import edu.bitble.kurse.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository repository;

    public List<Voucher> getAll() {
			return repository.findAll()
                .stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .toList();
		}

		public Voucher get(Integer id) {
			return repository.findById(id)
                .map(VoucherMapper.INSTANCE::toDto)
                .orElseThrow();
		}

		public List<Voucher> search(String query) {
			return repository.findAllByNameContainsIgnoreCase(query)
                .stream()
                .map(VoucherMapper.INSTANCE::toDto)
                .toList();
		}

		public Voucher create(VoucherRequest req) {
			// TODO: precondition?

			// TODO: processing logic

			var persistedVoucher = repository.save(
				VoucherMapper.INSTANCE.toPersistence(req)
			);

			return VoucherMapper.INSTANCE.toDto(persistedVoucher);
		}

		public Voucher update(Integer id, VoucherRequest req) {
			var persistedVoucher = getEntity(id);

			// mapping rule

			var updatedEntity = repository.save(
				VoucherMapper.INSTANCE.toPersistence(req)
			);
			return VoucherMapper.INSTANCE.toDto(updatedEntity);
		}

		public Voucher delete(Integer id) {
			var persistedVoucher = getEntity(id);
			repository.delete(persistedVoucher);
			return VoucherMapper.INSTANCE.toDto(persistedVoucher);
		}

		private VoucherEntity getEntity(Integer id) {
			return repository.findById(id).orElseThrow();
		}

}