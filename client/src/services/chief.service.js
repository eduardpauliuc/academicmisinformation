import http from "./http-common";
import { USE_MOCK_SERVICE } from "../helpers/constants";
import authService from "./auth.service";

const API_URL = "chief";
const currentUser = () => authService.getCurrentUser();

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

  return http().get(`${API_URL}/${currentUser().id}/optionals`);
};

const reviewOptional = ({ reviewMessage, status, optionalId }) => {
  if (USE_MOCK_SERVICE) {
    console.log("Review optional with id", optionalId);

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve();
      }, 1000);
    });
  }

  return http().post(`${API_URL}/${currentUser().id}/optionals/${optionalId}`, {
    status,
    reviewMessage,
  });
};

const getTeacherDisciplines = (teacherId) => {
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

  return http().get(
    `${API_URL}/${currentUser().id}/teachers/disciplines/${teacherId}`
  );
};

const getTeacherRankings = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        { ranking: 1, teacherName: "Teacher Name 1" },
        { ranking: 2, teacherName: "Teacher Name 2" },
      ],
    };

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http().get(`${API_URL}/${currentUser().id}/teachers/rankings`);
};

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

  return http().get(`${API_URL}/${currentUser().id}/teachers`);
};

const ChiefService = {
  getOptionals,
  reviewOptional,
  getTeacherDisciplines,
  getTeacherRankings,
  getAllTeachers,
};

export default ChiefService;
