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
        {
          specializationID: 2,
          facultyID: 1,
          name: "Mathematics",
          degreeType: "bachelors",
          studyLanguage: "romanian",
          numberOfSemesters: 8,
        },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 2000);
    });
  }

  return http.get(API_URL + `${studentID}`);
};

const StudentService = {
  getStudentSpecializations,
};

export default StudentService;
