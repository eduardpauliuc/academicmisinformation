import http from "./http-common";
import { USE_MOCK_SERVICE } from "../helpers/constants";

// const USE_MOCK_SERVICE = true;

const API_URL = "faculty/";

export const getFaculties = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          facultyID: 1,
          name: "Faculty of Mathematics and Computer Science",
          specializations: [
            {
              specializationID: 1,
              name: "Computer Science",
              degree_type: "bachelors",
              numberOfSemesters: 6,
            },
            {
              specializationID: 2,
              name: "Mathematics",
              degree_type: "bachelors",
              numberOfSemesters: 6,
            },
            {
              specializationID: 3,
              name: "Mathematics and Computer Science",
              degree_type: "bachelors",
              numberOfSemesters: 6,
            },
            {
              specializationID: 4,
              name: "Software Engineering",
              degree_type: "masters",
              numberOfSemesters: 4,
            },
          ],
        },
        {
          facultyID: 2,
          name: "Faculty of Law",
          specializations: [
            {
              specializationID: 1,
              name: "Law",
              degree_type: "bachelors",
              numberOfSemesters: 8,
            },
            {
              specializationID: 2,
              name: "Judge",
              degree_type: "masters",
              numberOfSemesters: 6,
            },
          ],
        },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 1000);
    });
  }

  return http.get(API_URL);
};

