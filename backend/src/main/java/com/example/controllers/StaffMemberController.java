package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.StudentGradeDTO;
import com.example.services.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/staff/{id}")
public class StaffMemberController {

    private final IStudentService studentService;
    private final IStaffMemberService staffMemberService;
    private final ISpecializationService specializationService;
    private final ICourseService courseService;
    private final IContractService contractService;
    private final IGradeService gradeService;
    private final IOptionalPreferenceService optionalPreferenceService;
    private final IOptionalProposalService optionalProposalService;
    private final IStatusService statusService;

    @Autowired
    private Logger logger;

    @GetMapping("/{specializationId}/{semester}/students")
    @PreAuthorize("hasRole('STAFF')")
    public List<StudentGradeDTO> getStudents(@PathVariable("id") Long staffMemberId,
                                             @PathVariable("specializationId") Long specializationId,
                                             @PathVariable("semester") Integer semester) {
        logger.info("Getting the students' averages studying at the specialization with the id " + specializationId + " in the semester " + semester);
//        Specialization specialization = validateData(specializationId, staffMemberId, semester);
        Specialization specialization = specializationService.findSpecializationById(specializationId).orElse(null);

        var studentGradeDTOs = new LinkedList<StudentGradeDTO>();
        if (semester == 1) {
            var students = studentService.sortStudentsByName(specialization, semester);
            students.forEach(student -> studentGradeDTOs.add(
                    StudentGradeDTO.builder()
                            .studentId(student.getId())
                            .average(-1.0)
                            .name(student.getAccount().getFirstName() + " " + student.getAccount().getLastName())
                            .group(student.getContracts()
                                    .stream()
                                    .filter(contract -> Objects.equals(contract.getSpecialization().getId(), specializationId) && Objects.equals(contract.getSemesterNumber(), semester))
                                    .findFirst()
                                    .orElseThrow(
                                            () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contract not found")
                                    )
                                    .getGroupCode()
                            )
                            .build(
                            )));
            return studentGradeDTOs;
        }

        // for each student, add to the current student id and its average, considering
        // it is different from -1
        Integer requestedSemester = semester - 1;
        // TODO: what the fuck
        var students = studentService.sortStudentsByAverage(specialization, semester, requestedSemester);

        students.forEach(student -> studentGradeDTOs.add(
                        StudentGradeDTO.builder()
                                .studentId(student.getId())
                                .average(student.findAverageForSemester(specialization, requestedSemester))
                                .name(student.getAccount().getFirstName() + " " + student.getAccount().getLastName())
                                .group(student.getContracts()
                                        .stream()
                                        .filter(contract -> Objects.equals(contract.getSpecialization().getId(), specializationId) && Objects.equals(contract.getSemesterNumber(), semester))
                                        .findFirst()
                                        .orElseThrow(
                                                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Contract not found")
                                        )
                                        .getGroupCode()
                                )
                                .build()
                )
        );
        logger.info("Student averages successfully retrieved!");
        return studentGradeDTOs;
    }

    // we should return something when something goes wrong, but I am not sure what yet
    @PostMapping("/{specializationId}/{semester}/assignment/groups")
    @PreAuthorize("hasRole('STAFF')")
    @Transactional
    public void assignStudentsToGroups(@PathVariable("id") Long staffMemberId,
                                       @PathVariable("specializationId") Long specializationId,
                                       @PathVariable("semester") Integer semester) {
        logger.info("Assigning students studying at the specialization with the id " + specializationId +
                " in the semester " + semester + " to their respective groups");
//        Specialization specialization = validateData(specializationId, staffMemberId, semester);
        Specialization specialization = specializationService.findSpecializationById(specializationId).orElse(null);

        var students = studentService.sortStudentsByName(specialization, semester);
        students = students.stream().
                filter(student -> student.getLatestContract(specialization).get().getGroupCode() == null)
                .collect(Collectors.toList());
        // he doesn't like the absence of isPresent here, but logically we cannot get here with an empty latestContract
        // because of the above call of getStudentsFromSpecializationAndSemester
        // perhaps we can modify it so IntelliJ doesn't complain
        // get does not crash because of the following:
        // if there's no contract for our specialization, getSemester would return -1, which isn't our semester
        // because we checked our semester to be positive => our student wouldn't pass the filter

        // 28 students per group, 2 semesters per year bound to change
        final int studentsPerGroup = 2;
        final int semestersPerYear = 2;
        logger.info("Assigning " + studentsPerGroup + " students per group");
        var studentCounter = new AtomicInteger(0);
        students
                .forEach(student -> {
                    var groupCount = studentCounter.get() / studentsPerGroup + 1;
                    var year = (semester + 1) / semestersPerYear;
                    var groupString = specialization.getLetterIdentifier() + year + groupCount;
                    studentCounter.incrementAndGet();
                    // he doesn't like the absence of isPresent here as well, but I do not want to redo
                    // an is Present because we did it above
                    var latestContract = student.getLatestContract(specialization).get();
                    latestContract.setGroupCode(groupString);
                    logger.info("Assigned to student with id " + student.getId() + " the group " + groupString);
                });
        logger.info("Group assignment successfully done!");
    }

    @PostMapping("/{specializationId}/{semester}/assignment/optionals")
    @PreAuthorize("hasRole('STAFF')")
    public void assignStudentsToOptionals(@PathVariable("id") Long staffMemberId,
                                          @PathVariable("specializationId") Long specializationId,
                                          @PathVariable("semester") Integer semester) {
        logger.info("Assigning students studying at the specialization with the id " + specializationId +
                " in the semester " + semester + " to their chosen optionals");
//        Specialization specialization = validateData(specializationId, staffMemberId, semester);
        Specialization specialization = specializationService.findSpecializationById(specializationId).orElse(null);

        List<Student> students;
        // if we're in our first semester, there's no average to look into, so we
        // just decide alphabetically
        // realistically, there would be a better way to decide for optionals lol
        logger.info("Sorting the students");
        if (semester == 1)
            students = studentService.sortStudentsByName(specialization, semester);
        else
            // if we're not in our first semester, we'll look through the average of the previous semester
            // and let it decide
            // we'll assume each of our students has got an average for each of its courses
            students = studentService.sortStudentsByAverage(specialization, semester, semester - 1);

        // each optional is mapped to how many empty places they have left
        // initially, each optional is mapped to its maximum number of students
        // use a concurrent map maybe?
        // also, collectors to map should work
        Map<Course, Integer> optionalsMap = new HashMap<>();
        courseService.getAllCourses()
                .stream().filter(course -> course.getIsOptional() && semester.equals(course.getSemesterNumber()) && course.getSpecialization().equals(specialization))
                .forEach(course -> optionalsMap.put(course, course.getMaximumStudentsNumber()));

        for (Student student : students) {
            // optionalsConfirmed holds the number of optionals our student is currently assigned to
            int optionalsConfirmed = 0;
            // sort the preferences by their rank
            var preferences = student.getOptionalPreferences().stream()
                    .filter(optionalPreference -> optionalPreference.getCourse().getSpecialization().equals(specialization))
                    .sorted(Comparator.comparingInt(OptionalPreference::getRank))
                    .collect(Collectors.toList());
            int currentIndex = 0;
            // while we haven't signed our student to a number of optionals,
            // and we aren't at the end of the list
            while (optionalsConfirmed < 1 && currentIndex < preferences.size()) {
                var currentOptional = preferences.get(currentIndex).getCourse();
                var placesLeft = optionalsMap.get(currentOptional);
                if (placesLeft > 0) {
                    optionalsMap.replace(currentOptional, placesLeft - 1);
                    gradeService.saveGrade(new Grade(null, null, student, currentOptional));
                    // not sure at the moment how to, though
                    // consult me beforehand
                    optionalsConfirmed += 1;
                    logger.info("Assigned the student with the id " + student.getId() + " to the optional with the id " + currentOptional.getId());
                }
                currentIndex += 1;
            }
            optionalPreferenceService.removePreferencesForStudent(student, specialization);
        }

        int MINIMUM_STUDENTS_PER_OPTIONAL = 3;
        List<Course> toRejectOptionals = optionalsMap.entrySet().stream()
                .filter(courseAvailablePlacesPair -> Objects.equals(courseAvailablePlacesPair.getKey().getMaximumStudentsNumber(), courseAvailablePlacesPair.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        toRejectOptionals.forEach((toRejectOptional) -> {
            courseService.deleteCourseById(toRejectOptional.getId());
            OptionalProposal optionalProposal = new OptionalProposal(
              null,
              toRejectOptional.getSpecialization(),
              toRejectOptional.getTeachers().size() > 0 ? toRejectOptional.getTeachers().get(0) : null,
                    statusService.getRejectedStatus(),
                    "not enough students",
                    toRejectOptional.getMaximumStudentsNumber(),
                    toRejectOptional.getCredits(),
                    toRejectOptional.getDescription(),
                    toRejectOptional.getSemesterNumber(),
                    toRejectOptional.getName()
            );

            optionalProposalService.saveOptionalProposal(optionalProposal);
        });

        logger.info("Optional assignment successfully done!");
    }

    private Specialization validateData(Long specializationId, Long staffMemberId, Integer semester) {

        // if the current specialization does not exist, exit the function here
        var specialization = specializationService.findSpecializationById(specializationId).orElseThrow(
                () -> {
                    logger.warn("Specialization not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        // if the current staff member does not exist, exit the function here
        var staffMember = staffMemberService.findStaffMemberById(staffMemberId).orElseThrow(
                () -> {
                    logger.warn("Staff member not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Staff Member not found.");
                }
        );

        // if the current staff member isn't a staff member at the faculty of the given optional, exit the function here
        if (!specialization.getFaculty().equals(staffMember.getFaculty())) {
            logger.warn("Specialization isn't at the current faculty!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization isn't at the current faculty.");
        }

        // this is an invalid semester and may cause tricky issues if not checked for
        if (semester <= 0) {
            logger.warn("Semester " + semester + " isn't a valid semester!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid semester");
        }

        return specialization;
    }
}
