import http from "./http-common";
import { USE_MOCK_SERVICE } from "../helpers/constants";

const API_URL = "chief/";

const getOptionals = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          id: 1,
          name: "Fundamentals of Programming",
          teacherName: "Aurel Pop",
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
          teacherName: "Aurel Pop",
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
          teacherName: "Constantin Vlaicu",
          specializationName: "Mathematics",
          credits: 6,
          description: "",
          semesterNumber: 2,
          isOptional: null,
          status: "PENDING",
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

  return http.get(API_URL + `optionals`);
};

const reviewOptional = (optionalReview) => {
  if (USE_MOCK_SERVICE) {
    console.log("Review ", optionalReview);
  }

  return new Promise((resolve) => {
    setTimeout(() => {
      resolve();
    }, 1000);
  });
};

const getTeacherDisciplines = (teacherId) => {};

const getTeacherRankings = () => {};

const getAllTeachers = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        { id: 1, name: "Teacher Name 1" },
        { id: 2, name: "Teacher Name 2" },
      ],
    };

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + `teachers`);
};

const ChiefService = {
  getOptionals,
  reviewOptional,
  getTeacherDisciplines,
  getTeacherRankings,
  getAllTeachers,
};

export default ChiefService;
