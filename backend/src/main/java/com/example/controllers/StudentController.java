package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.PdfDTO;
import com.example.payload.requests.StudentOptionalsRankingDTO;
import com.example.payload.requests.UploadContractRequest;
import com.example.payload.responses.*;
import com.example.services.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/student/{studentId}")
public class StudentController {

    private final IStudentService studentService;
    private final ISpecializationService specializationService;
    private final IDocumentGenerator pdfGeneratorService;
    private final IDocumentUploadService contractUploadService;
    private final IContractService contractService;
    private final ICourseService courseService;
    private final IOptionalPreferenceService optionalPreferenceService;

    @Autowired
    private Logger logger;


    @GetMapping("/contracts")
    @PreAuthorize("hasRole('STUDENT')")
    public List<ContractDTO> getStudentsContracts(@PathVariable("studentId") Long id) {
        logger.info("Getting the contracts for the student with the id " + id);
        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );
        logger.info("Contracts successfully obtained!");
        return student.getContracts()
                .stream()
                .map(ContractDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/specializations")
    @PreAuthorize("hasRole('STUDENT')")
    public List<SpecializationDTO> getStudentsSpecializations(@PathVariable("studentId") Long id) {
        // TODO: use getActiveContracts from studentService
        logger.info("Getting specializations for the student with the id " + id);
        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );
        logger.info("Specializations successfully obtained!");
        return student.getContracts()
                .stream()
//
                .map(Contract::getSpecialization)
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{specializationId}/courses")
    @PreAuthorize("hasRole('STUDENT')")
    public List<CourseDTO> getCoursesForSpecialization(@PathVariable("studentId") Long id,
                                                       @PathVariable("specializationId") Long specializationId) {
        logger.info("Getting courses of the student with the id " + id + " at the specialization " + specializationId);
        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );

        Specialization specialization = this.specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> {
                    logger.warn("Specialization not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        Contract contract = student.getLatestContract(specialization).orElseThrow(
                () -> {
                    logger.warn("Student has no contracts for this specialization!");
                    return new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR, "Student has no contracts for this specialization.");
                }
        );

        Integer semester = contract.getSemesterNumber();
        logger.info("Courses successfully returned!");
        return specialization.getCourses()
                .stream()
                .filter(course -> Objects.equals(course.getSemesterNumber(), semester))
                .map(CourseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/contracts/generate")
    @PreAuthorize("hasRole('STUDENT')")
    public void generateContract(@PathVariable("studentId") Long id, HttpServletResponse response,
                                 @RequestParam Long specializationId, @RequestParam Integer semester) {
        logger.info("Generating contract for the student with id " + id + " for specialization " + specializationId
                + " and for the semester " + semester);
        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );

        Specialization specialization = this.specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> {
                    logger.warn("Specialization not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        Optional<Contract> contract = student.getLatestContract(specialization);

        if (semester != 1) {
            if (contract.isEmpty() || contract.get().getSemesterNumber() != semester - 1) {
                logger.warn("Contract for previous semester not found!");
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Contract for previous semester not found."
                );
            }
        }

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contract_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        PdfDTO pdfDTO = new PdfDTO(student, specialization, semester);

        try {
            pdfGeneratorService.export(response, pdfDTO);
            logger.info("Contract PDF successfully generated!");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("Error while generating contract PDF");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while generating contract pdf.");
        }
    }


    @GetMapping("/{specializationId}/courses/optionals")
    @PreAuthorize("hasRole('STUDENT')")
    public List<OptionalPreferenceDTO> getOptionalCourses(@PathVariable("studentId") Long id, @PathVariable Long specializationId) {
        // TODO: for current semester?
        logger.info("Getting optional courses for the student with id " + id + " at the specialization " + specializationId);
        Student student = studentService.findStudentById(id).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );

        Specialization specialization = specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> {
                    logger.warn("Specialization not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        logger.info("Optional preferences successfully returned!");
        return student.getOptionalPreferences().stream()
                .filter(currentStudent -> currentStudent.getCourse().getSpecialization().equals(specialization))
                .sorted(Comparator.comparing(OptionalPreference::getRank))
                .map(currentStudent -> new OptionalPreferenceDTO(
                        new CourseDTO(currentStudent.getCourse()), currentStudent.getRank())
                )
                .collect(Collectors.toList());
    }


    @GetMapping("/{specializationId}/grades")
    @PreAuthorize("hasRole('STUDENT')")
    public List<GradeResponseDTO> getGrades(
            @PathVariable("studentId") Long studentId,
            @PathVariable("specializationId") Long specializationId
    ) {
        logger.info("Getting the grades for the student with the id " + studentId + " at the specialization " + specializationId);
        Student student = studentService.findStudentById(studentId).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );

        Specialization specialization = specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> {
                    logger.warn("Specialization not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        logger.info("Grades successfully retrieved!");
        return student.getGrades().stream()
                .filter(grade -> grade.getCourse().getSpecialization() == specialization)
                .map(grade -> new GradeResponseDTO(grade, grade.getCourse().getSemesterNumber()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/contracts/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('STUDENT')")
    public void uploadContract(@PathVariable Long studentId, @ModelAttribute UploadContractRequest uploadContractRequest) {
        MultipartFile file = uploadContractRequest.getFile();
        logger.info("The student with id " + studentId + " is uploading a contract");
        Student student = studentService.findStudentById(studentId).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );

        Specialization specialization = specializationService.findSpecializationById(
                uploadContractRequest.getSpecializationId()
        ).orElseThrow(
                () -> {
                    logger.warn("Specialization not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        String name = student.getAccount().getLastName() +
                "-" + student.getAccount().getFirstName() +
                "_" + specialization.getName().replace(' ', '-') + "_"
                + uploadContractRequest.getSemester() + ".pdf";

        try {
            contractUploadService.saveFile(name, file);
            logger.info("PDF successfully uploaded!");
        } catch (IOException exception) {
            logger.info("An error happened while uploading the PDF!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }

        contractService.saveContract(new Contract(
                null,
                specialization,
                student,
                java.sql.Date.valueOf("2020-02-20"),
                java.sql.Date.valueOf("2021-02-20"),
                1,
                "A17")
        );
    }

    @PostMapping("/{specializationId}/courses/optionals/order")
    @PreAuthorize("hasRole('STUDENT')")
    public void orderOptionals(
            @PathVariable("specializationId") Long specializationId,
            @PathVariable("studentId") Long id,
            @RequestBody List<StudentOptionalsRankingDTO> studentOptionalsRanking
    ) {
        logger.info("Getting the optionals in their preference order for student with id " + id + " and specialization "
                + specializationId);
        Student student = studentService.findStudentById(id).orElseThrow(
                () -> {
                    logger.warn("Student not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                }
        );

        Specialization specialization = specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> {
                    logger.warn("Specialization not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        // check if duplicate indexes exist
        List<Integer> indexes = studentOptionalsRanking.stream()
                .map(StudentOptionalsRankingDTO::getIndex)
                .collect(Collectors.toList());
        if (indexes.size() != new HashSet<>(indexes).size()) {
            logger.info("Duplicate optional indexes found!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate optional indexes found.");
        }

        studentOptionalsRanking.forEach(ranking -> {
            Course course = courseService.findCourseById(ranking.getOptionalId()).orElseThrow(
                    () -> {
                        logger.warn("Course not found!");
                        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Course not found.");
                    }
            );

            if (!course.getIsOptional() || !Objects.equals(course.getSpecialization().getId(), specialization.getId())) {
                logger.warn("Invalid request body! Mandatory courses or courses from wrong specialization found!");
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Invalid request body! Mandatory courses or courses from wrong specialization found."
                );
            }

            OptionalPreference optionalPreference = new OptionalPreference(
                    new OptionalPreferenceId(student.getId(), course.getId()),
                    student,
                    course,
                    ranking.getIndex()
            );

            optionalPreferenceService.saveOptionalPreference(optionalPreference);
        });
    }
}
