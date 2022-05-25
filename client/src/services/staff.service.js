import { USE_MOCK_SERVICE } from "../helpers/constants";
import http from "./http-common";

const API_URL = "staff/";

const getStudents = (specializationId, semester) => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        { studentID: "stud1", averageGrade: 10, group: "925" },
        { studentID: "stud2", averageGrade: 9, group: "926" },
        { studentID: "stud3", averageGrade: 9.5, group: "925" },
        { studentID: "stud4", averageGrade: 8.5, group: undefined },
        { studentID: "stud5", averageGrade: undefined, group: undefined },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  // TODO ADD ID HERE
  return http.get(API_URL + `${specializationId}/${semester}/students`);
};

const assignToOpionals = (specializationId, semester) => {
  // TODO ADD ID HERE

  return http.post(
    API_URL + `${specializationId}/${semester}/students/assignment/optionals`
  );
};

const assignToGroups = (specializationId, semester) => {
  // TODO ADD ID HERE
  return http.post(
    API_URL + `${specializationId}/${semester}/students/assignment/groups`
  );
};

const StaffService = {
  getStudents,
  assignToGroups,
  assignToOpionals,
};

export default StaffService;
