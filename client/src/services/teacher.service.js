import http from "./http-common";
import { USE_MOCK_SERVICE } from "../helpers/constants";
import authService from "./auth.service";

// const USE_MOCK_SERVICE = true;

const API_URL = "teacher";

const currentUser = () => authService.getCurrentUser();

const getTeacherCourses = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          id: 1,
          name: "Fundamentals of Programming",
          specializationName: "Computer Science",
          credits: 6,
          description: "Short introduction into programming concepts",
          semesterNumber: 1,
          isOptional: false,
          status: null,
          maximumStudentsNumber: 200,
        },
        {
          id: 2,
          name: "Professional Development",
          specializationName: "Computer Science",
          credits: 3,
          description: "",
          semesterNumber: 2,
          isOptional: true,
          status: null,
          maximumStudentsNumber: 100,
        },
        {
          id: 3,
          name: "Algebra in Comptuer Science",
          specializationName: "Mathematics",
          credits: 6,
          description: "",
          semesterNumber: 2,
          isOptional: null,
          status: "REJECTED",
          maximumStudentsNumber: 200,
        },
      ],
    };

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http().get(`${API_URL}/courses`);
};

const addGrade = (grade, courseId, studentId) => {
  return http().post(`${API_URL}/grade`, {
    grade,
    studentId,
    courseId,
  });
};

const addOptional = (
  specializationId,
  name,
  credits,
  description,
  semesterNumber,
  maximumStudentsNumber
) => {
  const teacherId = currentUser().id;

  return http().post(`${API_URL}/optional`, {
    teacherId,
    specializationId,
    name,
    credits,
    description,
    semesterNumber,
    maximumStudentsNumber,
  });
};

const getStudents = (courseId) => {
  return http().get(`${API_URL}/students/${courseId}`);
};

const TeacherService = {
  getTeacherCourses,
  addGrade,
  addOptional,
  getStudents,
};

export default TeacherService;
