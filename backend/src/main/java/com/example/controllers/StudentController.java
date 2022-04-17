package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.PdfDTO;
import com.example.payload.responses.*;
import com.example.services.IDocumentGenerator;
import com.example.services.ISpecializationService;
import com.example.services.IStudentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/student/{student_id}")
public class StudentController {

    private final IStudentService studentService;
    private final ISpecializationService specializationService;
    private final IDocumentGenerator pdfGeneratorService;


    @GetMapping("/contracts")
    public List<ContractDTO> getStudentsContracts(@PathVariable("student_id") Long id){
        Optional<Student> student = studentService.findStudentById(id);
        if (student.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
        }
        return student.get().getContracts().stream().map(ContractDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/specializations")
    public List<SpecializationDTO> getStudentsSpecializations(@PathVariable("student_id") Long id){
        // TODO: get currently active specializations? check dates on contracts?
        Optional<Student> student = studentService.findStudentById(id);
        if (student.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
        }
        return  student.get().getContracts().stream().map(Contract::getSpecialization).map(SpecializationDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{specializationId}/courses")
    public List<CourseDTO> getCoursesForSpecialization(@PathVariable("student_id") Long id, @PathVariable("specializationId") Long specializationId){
        Optional<Student> student = studentService.findStudentById(id);
        if(student.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
        }
        Optional<Specialization> specialization = specializationService.findSpecializationById(specializationId);
        if(specialization.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
        }
        Optional<Contract> contract = student.get().getLatestContract(specialization.get());

        if(contract.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student has no contracts for this specialization.");
        }

        Integer semester = contract.get().getSemesterNumber();

        return specialization.get().getCourses().stream().filter(c -> Objects.equals(c.getSemesterNumber(), semester)).map(c -> new CourseDTO(c,c.getIsOptional(), null )).collect(Collectors.toList());
    }

    @GetMapping("/contracts/generate")
    public void generateContract(@PathVariable("student_id") Long id, HttpServletResponse response,
                                 @RequestParam Long specializationId, @RequestParam Integer semester) {

        Optional<Student> student = studentService.findStudentById(id);
        if(student.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
        }
        Optional<Specialization> specialization = specializationService.findSpecializationById(specializationId);
        if(specialization.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
        }
        Optional<Contract> contract = student.get().getLatestContract(specialization.get());

        if (semester != 1){
            if(contract.isEmpty() || contract.get().getSemesterNumber() != semester - 1){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contract for previous semester not found.");
            }
        }

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contract_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        PdfDTO dto = new PdfDTO(student.get(), specialization.get(), semester);
        try {
            pdfGeneratorService.export(response, dto);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while generating contract pdf.");
        }
    }


    @GetMapping("/{specializationId}/courses/optionals")
    public List<OptionalPreferenceDTO> getOptionalCourses(@PathVariable("student_id") Long id, @PathVariable Long specializationId){
        Optional<Student> student = studentService.findStudentById(id);
        if(student.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
        }

        Optional<Specialization> specialization = specializationService.findSpecializationById(specializationId);
        if(specialization.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
        }

        return student.get().getOptionalPreferences().stream().filter(o -> o.getCourse().getSpecialization().equals(specialization.get())).sorted(Comparator.comparing(OptionalPreference::getRank)).map(item -> new OptionalPreferenceDTO( new CourseDTO(item.getCourse(), true, null), item.getRank())).collect(Collectors.toList());

    }


    @GetMapping("/{specializationId}/grades")
    public List<GradeDTO> getGrades(@PathVariable Long specializationId, @PathVariable Long student_id){
        Optional<Student> student = studentService.findStudentById(student_id);
        if(student.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
        }

        Optional<Specialization> specialization = specializationService.findSpecializationById(specializationId);
        if(specialization.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
        }

        return student.get().getGrades().stream().filter(g -> g.getCourse().getSpecialization() == specialization.get()).map(g -> new GradeDTO(g, g.getCourse().getSemesterNumber())).collect(Collectors.toList());

    }



}
