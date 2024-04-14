package edu.bitble.kurse.controller;

import edu.bitble.kurse.common.SecurityHandler;
import edu.bitble.kurse.model.request.VoucherRequest;
import edu.bitble.kurse.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static edu.bitble.kurse.common.ControllerUtils.controllerWrapper;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/vouchers")
@RequiredArgsConstructor
@Validated
public class VoucherController {

    private final VoucherService service;
    private final SecurityHandler securityHandler;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return controllerWrapper(service::getAll);
    }

    @GetMapping("search")
    public ResponseEntity<?> search(@RequestParam String q) {
        return controllerWrapper(() -> service.search(q));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return controllerWrapper(() -> service.get(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VoucherRequest req) {
        return controllerWrapper(() -> service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @Valid @RequestBody VoucherRequest req) {
        return controllerWrapper(() -> service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return controllerWrapper(() -> service.delete(id));
    }

}