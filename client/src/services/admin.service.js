import { USE_MOCK_SERVICE } from "../helpers/constants";
import http from "./http-common";

const API_URL = "admin";

const getAllUsers = () => {
  if (USE_MOCK_SERVICE) {
    const response = {
      data: [
        {
          id: 1,
          username: "eduardpauliuc",
          email: "pauliucedy@gmail.com",
          role: "ROLE_ADMINISTRATOR",
          firstName: "Eduard",
          lastName: "Pauliuc",
          birthDate: "2001-04-20",
        }, {
          id: 2,
          username: "teacheruser",
          email: "teacher@yahoo.com",
          role: "ROLE_TEACHER",
          firstName: "Alina",
          lastName: "Popescu",
          birthDate: "1990-02-12",
        }, {
          id: 3,
          username: "chiefuser",
          email: "chief@outlook.com",
          role: "ROLE_CHIEF",
          firstName: "Tudor",
          lastName: "Ilea",
          birthDate: "1980-01-25",
        }, {
          id: 4,
          username: "mihaib",
          email: "mihai@mymail.ro",
          role: "ROLE_STUDENT",
          firstName: "Mihai Teodor",
          lastName: "Bob",
          birthDate: "2002-12-04",
        },
      ],
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 2000);
    });
  }

  return http.get(`/${API_URL}/accounts`);
};

const createUser = (username, password, email, role) => {
  if (USE_MOCK_SERVICE) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve();
      }, 2000);
    });
  }

  return http.post(`/${API_URL}/accounts`, {
      username,
      password,
      email,
      role
    }
  );
};

const deleteUser = (userId) => {
  return http.delete(`/${API_URL}/accounts/${userId}`);
};

const AdminService = {
  getAllUsers,
  createUser,
  deleteUser,
};

export default AdminService;
