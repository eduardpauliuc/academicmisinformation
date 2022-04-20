import http from "./http-common";
import { USE_MOCK_SERVICE } from "../helpers/constants";

// const USE_MOCK_SERVICE = true;

const API_URL = "student/";

const getStudentSpecializations = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          id: 1,
          facultyId: 1,
          name: "Computer Science",
          degreeType: "MASTERS",
          numberOfSemesters: 6,
        },
        {
          id: 2,
          facultyId: 1,
          name: "Mathematics",
          degreeType: "MASTERS",
          numberOfSemesters: 6,
        },
      ],
    };

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + `specializations`);
};

const getStudentContracts = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          studentId: 1,
          specialization: {
            id: 1,
            facultyId: 1,
            name: "Computer Science",
            degreeType: "MASTERS",
            numberOfSemesters: 6,
          },
          startDate: "2022-02-16",
          endDate: "2022-07-01",
          semester: 2,
        },
        {
          studentId: 1,
          specialization: {
            id: 1,
            facultyId: 1,
            name: "Computer Science",
            degreeType: "MASTERS",
            numberOfSemesters: 6,
          },
          startDate: "2021-10-01",
          endDate: "2022-02-15",
          semester: 1,
        },
        {
          studentId: 1,
          specialization: {
            id: 2,
            facultyId: 1,
            name: "Mathematics",
            degreeType: "MASTERS",
            numberOfSemesters: 6,
          },
          startDate: "2021-10-01",
          endDate: "2022-02-15",
          semester: 1,
        },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + `contracts`);
};

const generateContract = (studentID, specializationID, semester) => {
  return http.get(
    API_URL + `${studentID}/contracts`,
    specializationID,
    semester
  );
};

const uploadContract = (studentID, specializationID, semester, contract) => {
  return http.post(API_URL + `/contracts`, {
    specializationID,
    semester,
    contract,
  });
};

const getCurriculum = (specializationID) => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          id: 1,
          name: "Fundamentals of Programming",
          credits: 6,
          isOptional: false,
        },
        {
          id: 2,
          name: "Algebra",
          credits: 6,
          isOptional: false,
        },
        {
          id: 3,
          name: "Computer Systems Architecture",
          credits: 6,
          isOptional: false,
        },
        {
          id: 4,
          name: "Dynamic Systems",
          credits: 6,
          isOptional: false,
        },
        {
          id: 5,
          name: "English",
          credits: 3,
          isOptional: true,
        },
        {
          id: 6,
          name: "Sports",
          credits: 3,
          isOptional: true,
        },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + specializationID + "/courses");
};

const getOptionalsOrder = (specializationID) => {
  if (USE_MOCK_SERVICE) {
    const response =
      specializationID === 1
        ? {
            data: [
              {
                id: 1,
                name: "Fundamentals of Programming",
                credits: 6,
                index: 1,
              },
              {
                id: 2,
                name: "Algebra",
                credits: 6,
                index: 2,
              },
              {
                id: 3,
                name: "Computer Systems Architecture",
                credits: 6,
                index: 3,
              },
              {
                id: 4,
                name: "Dynamic Systems",
                credits: 6,
                index: 6,
              },
              {
                id: 5,
                name: "English",
                credits: 3,
                index: 5,
              },
              {
                id: 6,
                name: "Sports",
                credits: 3,
                index: 4,
              },
            ],
          }
        : [];

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + specializationID + "/optionals");
};

const setOpionalsOrder = (specializationID, idslist) => {
  if (USE_MOCK_SERVICE) {
    console.log(idslist);

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve();
      }, 1000);
    });
  }

  return http.post(API_URL + specializationID + "/optionals/order", {
    idslist,
  });
};

const getGrades = (specializationID) => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        { courseName: "Fundamentals of Programming", grade: 10 },
        { courseName: "Algebra", grade: 9 },
        { courseName: "Analysis", grade: undefined },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + specializationID + "/grades");
};

const StudentService = {
  getStudentSpecializations,
  getStudentContracts,
  generateContract,
  uploadContract,
  getCurriculum,
  getOptionalsOrder,
  setOpionalsOrder,
  getGrades,
};

export default StudentService;
