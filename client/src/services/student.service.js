import http from "./http-common";
import { USE_MOCK_SERVICE } from "../helpers/constants";

// const USE_MOCK_SERVICE = true;

const API_URL = "student/";

const getStudentSpecializations = (studentID) => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          specializationID: 1,
          facultyID: 1,
          name: "Computer Science",
          degreeType: "masters",
          studyLanguage: "english",
          numberOfSemesters: 6,
        },
        // {
        //   specializationID: 2,
        //   facultyID: 1,
        //   name: "Mathematics",
        //   degreeType: "bachelors",
        //   studyLanguage: "romanian",
        //   numberOfSemesters: 8,
        // },
      ],
    };

    return new Promise((resolve) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + `${studentID}/specializations`);
};

const getStudentContracts = (studentID) => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          specializationID: 1,
          specializationName: "Computer Science",
          startDate: "2022-01-18",
          endDate: "2022-06-30",
          semester: 1,
        },
        // {
        //   specializationID: 2,
        //   specializationName: "Mathematics",
        //   startDate: "2022-01-18",
        //   endDate: "2022-05-25",
        //   semester: 3,
        // },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL + `${studentID}/contracts`);
};

const generateContract = (studentID, specializationID, semester) => {
  return http.get(
    API_URL + `${studentID}/contracts`,
    specializationID,
    semester
  );
};

const uploadContract = (studentID, specializationID, semester, contract) => {
  return http.post(API_URL + `${studentID}/contracts`, {
    specializationID,
    semester,
    contract,
  });
};

const StudentService = {
  getStudentSpecializations,
  getStudentContracts,
  generateContract,
  uploadContract,
};

export default StudentService;
