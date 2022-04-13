package com.example.controllers;

import com.example.models.Course;
import com.example.models.OptionalPreference;
import com.example.models.Student;
import com.example.payload.requests.StudentGradeDTO;
import com.example.services.ICourseService;
import com.example.services.ISpecializationService;
import com.example.services.IStaffMemberService;
import com.example.services.IStudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{specializationId}/{semester}/students")
    public List<StudentGradeDTO> getStudents(@PathVariable("id") Long staffMemberId,
                                             @PathVariable("specializationId") Long specializationId,
                                             @PathVariable("semester") Integer semester){
        var specializationOptional = specializationService.findSpecializationById(specializationId);

        // if the current staff member does not exist, exit the function here
        var staffMemberOptional = staffMemberService.findStaffMemberById(staffMemberId);
        if (staffMemberOptional.isEmpty())
            return new LinkedList<>();

        // if the current specialization does not exist, exit the function here
        var staffMember = staffMemberOptional.get();
        if (specializationOptional.isEmpty())
            return new LinkedList<>();

        // if the current staff member isn't a staff member at the faculty of the given optional, exit the function here
        var specialization = specializationOptional.get();
        if (!specialization.getFaculty().equals(staffMember.getFaculty()))
            return new LinkedList<>();

        // this is an invalid semester and may cause tricky issues if not checked for
        if (semester <= 0)
            return new LinkedList<>();

        // for each student, add to the current student id and its average, considering
        // it is different from -1
        var students = studentService.sortStudentsByAverage(specialization, semester);
        var studentGradeDTOs = new LinkedList<StudentGradeDTO>();
        students.forEach(
                student ->
                    studentGradeDTOs.add(new StudentGradeDTO(student.getId(), student.findAverageForSemester(specialization, semester)))
        );
        return studentGradeDTOs;
    }

    // we should return something when something goes wrong, but I am not sure what yet
    @PutMapping("/{specializationId}/{semester}/assignment/groups")
    public void assignStudentsToGroups(@PathVariable("id") Long staffMemberId,
                                          @PathVariable("specializationId") Long specializationId,
                                          @PathVariable("semester") Integer semester){
        // same checks as in the first function
        var specializationOptional = specializationService.findSpecializationById(specializationId);

        var staffMemberOptional = staffMemberService.findStaffMemberById(staffMemberId);
        if (staffMemberOptional.isEmpty())
            return;

        var staffMember = staffMemberOptional.get();
        if (specializationOptional.isEmpty())
            return;

        var specialization = specializationOptional.get();
        if (!specialization.getFaculty().equals(staffMember.getFaculty()))
            return;

        if (semester <= 0)
            return;

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
        final int studentsPerGroup = 28;
        final int semestersPerYear = 2;
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
                });
    }

    @PutMapping("/{specializationId}/{semester}/assignment/groups")
    public void assignStudentsToOptionals(@PathVariable("id") Long staffMemberId,
                                          @PathVariable("specializationId") Long specializationId,
                                          @PathVariable("semester") Integer semester){
        // same checks as before
        // we could make it a function,
        // but we want it to return it something in case it crashes for the frontend so IDK
        var specializationOptional = specializationService.findSpecializationById(specializationId);

        var staffMemberOptional = staffMemberService.findStaffMemberById(staffMemberId);
        if (staffMemberOptional.isEmpty())
            return;

        var staffMember = staffMemberOptional.get();
        if (specializationOptional.isEmpty())
            return;

        var specialization = specializationOptional.get();
        if (!specialization.getFaculty().equals(staffMember.getFaculty()))
            return;

        if (semester <= 0)
            return;

        List<Student> students;
        // if we're in our first semester, there's no average to look into, so we
        // just decide alphabetically
        // realistically, there would be a better way to decide for optionals lol
        if (semester == 1)
            students = studentService.sortStudentsByName(specialization, semester);
        else
            // if we're not in our first semester, we'll look through the average of the previous semester
            // and let it decide
            // we'll assume each of our students has got an average for each of its courses
            students = studentService.sortStudentsByAverage(specialization, semester - 1);


        // each optional is mapped to how many empty places they have left
        // initially, each optional is mapped to its maximum number of students
        // use a concurrent map maybe?
        // also, collectors to map should work
        Map<Course, Integer> optionalsMap = new HashMap<>();
        courseService.getAllCourses()
                .stream().filter(course -> course.getIsOptional() && semester.equals(course.getSemesterNumber()))
                .forEach(course -> optionalsMap.put(course, course.getMaximumStudentsNumber()));

        for (Student student : students){
            // optionalsConfirmed holds the number of optionals our student is currently assigned to
            int optionalsConfirmed = 0;
            // sort the preferences by their rank
            var preferences = student.getOptionalPreferences().stream()
                    .sorted(Comparator.comparingInt(OptionalPreference::getRank))
                    .collect(Collectors.toList());
            int currentIndex = 0;
            // while we haven't signed our student to a number of optionals,
            // and we aren't at the end of the list
            while (optionalsConfirmed < 2 && currentIndex < preferences.size()){
                var currentOptional = preferences.get(currentIndex).getCourse();
                var placesLeft = optionalsMap.get(currentOptional);
                if (placesLeft > 0){
                    optionalsMap.replace(currentOptional, placesLeft - 1);
                    // TODO: add the optional to grades
                    // not sure at the moment how to, though
                    // consult me beforehand
                    optionalsConfirmed += 1;
                }
                else{
                    currentIndex += 1;
                }
            }
        }
        // TODO: delete all preferences after for our specialization and semester

    }
}
