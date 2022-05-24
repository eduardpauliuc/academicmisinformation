import {USE_MOCK_SERVICE} from "../helpers/constants";
import http from "./http-common";

const API_URL = "staff/";

const getGroups = () => {
    if (USE_MOCK_SERVICE) {
        const response = {
            data: [
                { id: 1, name: "925" },
                { id: 2, name: "926" },
            ],
        };

        return new Promise((resolve) => {
            setTimeout(() => {
                resolve(response);
            }, 100);
        });
    }

    return http.get(API_URL + `groups`);
};

const getAcademicYears = () => {
    if (USE_MOCK_SERVICE) {
        const response = {
            data: [
                { id: 1, name: "1st" },
                { id: 2, name: "2nd" },
                { id: 3, name: "3rd" },
            ],
        };

        return new Promise((resolve) => {
            setTimeout(() => {
                resolve(response);
            }, 100);
        });
    }

    return http.get(API_URL + `years`);
};


const getStudents = () => {
    if (USE_MOCK_SERVICE) {
        const response = {
            data: [
                { studentID: "stud1", averageGrade: 10 },
                { studentID: "stud2", averageGrade: 9 },
                { studentID: "stud3", averageGrade: 9.50 },
                { studentID: "stud4", averageGrade: 8.50 },
                { studentID: "stud5", averageGrade: undefined },
            ],
        };

        return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(response);
            }, 100);
        });
    }

    return http.get(API_URL + "students");
};

const getStudentsByGroup = (groupID) => {
    if (USE_MOCK_SERVICE) {
        const response =
            groupID === 1
                ? {
                    data: [
                        { studentID: "stud1", averageGrade: 10 },
                        { studentID: "stud2", averageGrade: 9 },
                        { studentID: "stud3", averageGrade: 9.50 },
                    ],
                }
                : {
                    data: [
                        { studentID: "stud4", averageGrade: 8.50 },
                        { studentID: "stud5", averageGrade: undefined },
                    ],
                };

        return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(response);
            }, 100);
        });
    }

    return http.get(API_URL + groupID + "/students");
};

const getStudentsByYear = (yearID) => {
    if (USE_MOCK_SERVICE) {
        const response =
            yearID === 2
                ? {
                    data: [
                        {studentID: "stud1", averageGrade: 10},
                        {studentID: "stud2", averageGrade: 9},
                        {studentID: "stud3", averageGrade: 9.50},
                        {studentID: "stud4", averageGrade: 8.50},
                        {studentID: "stud5", averageGrade: undefined},
                    ],
                }
                : [];

        return new Promise((resolve, reject) => {
            setTimeout(() => {
                resolve(response);
            }, 100);
        });
    }

    return http.get(API_URL + yearID + "/students");
};

const StaffService = {
    getGroups,
    getStudentsByGroup,
    getAcademicYears,
    getStudentsByYear,
    getStudents
};

export default StaffService;