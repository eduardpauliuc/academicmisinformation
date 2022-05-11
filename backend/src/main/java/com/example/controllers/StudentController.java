package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.PdfDTO;
import com.example.payload.requests.StudentOptionalsRankingDTO;
import com.example.payload.requests.UploadContractRequest;
import com.example.payload.responses.*;
import com.example.services.*;
import lombok.AllArgsConstructor;
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


    @GetMapping("/contracts")
    @PreAuthorize("hasRole('STUDENT')")
    public List<ContractDTO> getStudentsContracts(@PathVariable("studentId") Long id) {
        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        return student.getContracts()
                .stream()
                .map(ContractDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/specializations")
    @PreAuthorize("hasRole('STUDENT')")
    public List<SpecializationDTO> getStudentsSpecializations(@PathVariable("studentId") Long id) {
        // TODO: use getActiveContracts from studentService
        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        return student.getContracts()
                .stream()
                .map(Contract::getSpecialization)
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{specializationId}/courses")
    @PreAuthorize("hasRole('STUDENT')")
    public List<CourseDTO> getCoursesForSpecialization(@PathVariable("studentId") Long id,
                                                       @PathVariable("specializationId") Long specializationId) {
        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        Specialization specialization = this.specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.")
        );

        Contract contract = student.getLatestContract(specialization).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Student has no contracts for this specialization."
                )
        );

        Integer semester = contract.getSemesterNumber();

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

        Student student = this.studentService.findStudentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        Specialization specialization = this.specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.")
        );

        Optional<Contract> contract = student.getLatestContract(specialization);

        if (semester != 1) {
            if (contract.isEmpty() || contract.get().getSemesterNumber() != semester - 1) {
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
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while generating contract pdf.");
        }
    }


    @GetMapping("/{specializationId}/courses/optionals")
    @PreAuthorize("hasRole('STUDENT')")
    public List<OptionalPreferenceDTO> getOptionalCourses(@PathVariable("studentId") Long id, @PathVariable Long specializationId) {
        // TODO: for current semester?
        Student student = studentService.findStudentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        Specialization specialization = specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.")
        );

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
        Student student = studentService.findStudentById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        Specialization specialization = specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.")
        );

        return student.getGrades().stream()
                .filter(grade -> grade.getCourse().getSpecialization() == specialization)
                .map(grade -> new GradeResponseDTO(grade, grade.getCourse().getSemesterNumber()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/contracts/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('STUDENT')")
    public void uploadContract(@PathVariable Long studentId, @ModelAttribute UploadContractRequest uploadContractRequest) {
        MultipartFile file = uploadContractRequest.getFile();

        Student student = studentService.findStudentById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        Specialization specialization = specializationService.findSpecializationById(
                uploadContractRequest.getSpecializationId()
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.")
        );

        String name = student.getAccount().getLastName() +
                "-" + student.getAccount().getFirstName() +
                "_" + specialization.getName().replace(' ', '-') + "_"
                + uploadContractRequest.getSemester() + ".pdf";

        try {
            contractUploadService.saveFile(name, file);
        } catch (IOException exception) {
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
        Student student = studentService.findStudentById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        Specialization specialization = specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.")
        );

        // check if duplicate indexes exist
        List<Integer> indexes = studentOptionalsRanking.stream()
                .map(StudentOptionalsRankingDTO::getIndex)
                .collect(Collectors.toList());
        if (indexes.size() != new HashSet<>(indexes).size()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate optional indexes found.");
        }

        studentOptionalsRanking.forEach(ranking -> {
            Course course = courseService.findCourseById(ranking.getOptionalId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Course not found.")
            );

            if (!course.getIsOptional() || !Objects.equals(course.getSpecialization().getId(), specialization.getId())) {
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
