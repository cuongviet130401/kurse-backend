package edu.bitble.kurse.controller;

import edu.bitble.kurse.common.ControllerUtils;
import edu.bitble.kurse.model.request.StudentRequest;
import edu.bitble.kurse.service.StudentService;
import edu.bitble.kurse.common.SecurityHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static edu.bitble.kurse.common.ControllerUtils.controllerWrapper;

@RestController
@RequestMapping("/v1/students")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final StudentService service;
    private final SecurityHandler securityHandler;

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentDetails(@PathVariable Integer id) {
        return ControllerUtils.controllerWrapper(() -> service.getStudentDetails(id));
    }

    @PostMapping
    public ResponseEntity<?> createStudentDetails(@RequestParam("accid") Integer acccountId) {
        return ControllerUtils.controllerWrapper(() -> service.createStudentDetails(acccountId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @Valid @RequestBody StudentRequest req) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return null;
    }

}
