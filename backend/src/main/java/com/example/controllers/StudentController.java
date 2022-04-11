package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.PdfDTO;
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
@RequestMapping("/api/student/{id}")
public class StudentController {

    private final IStudentService studentService;
    private final ISpecializationService specializationService;
    private final IDocumentGenerator pdfGeneratorService;


    @GetMapping("/contracts")
    public List<Contract> getStudentsContracts(@PathVariable("id") Long id){
        Optional<Student> student = studentService.findStudentById(id);
        return student.map(Student::getContracts).orElse(null);
//        ArrayList<Contract> contracts = new ArrayList<>();
//        contracts.add(new Contract());
//        return contracts;
    }

    @GetMapping("/specializations")
    public List<Specialization> getStudentsSpecializations(@PathVariable("id") Long id){
        Optional<Student> student = studentService.findStudentById(id);
        if (student.isEmpty()){
            return null;
        }
        return  student.get().getContracts().stream().map(Contract::getSpecialization).collect(Collectors.toList());
    }

    @GetMapping("/{specializationId}/courses")
    public List<Course> getCoursesForSpecialization(@PathVariable("id") Long id, @PathVariable("specializationId") Long specializationId){
        Optional<Student> student = studentService.findStudentById(id);
        if (student.isEmpty()){
            return null;
        }
        return student.get().getContracts().stream().
                map(Contract::getSpecialization).
                filter(s -> Objects.equals(s.getId(), specializationId))
                .findFirst().map(Specialization::getCourses)
                .orElse(null);
    }

    @GetMapping("/generate")
    public void generateContract(@PathVariable("id") Long id, HttpServletResponse response,
                                 @RequestParam Long specializationId, @RequestParam Integer semester) {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=contract_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Optional<Student> student = studentService.findStudentById(id);
        if (student.isPresent()){
            Optional<Specialization> specialization = specializationService.findSpecializationById(id);
            if (specialization.isPresent()){
                PdfDTO dto = new PdfDTO(specialization.get(), semester);
                try {
                    pdfGeneratorService.export(response, dto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found");
        } else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
    }

}
