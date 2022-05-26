import http, { BASE_URL } from "./http-common";

import { USE_MOCK_SERVICE } from "../helpers/constants";

// const USE_MOCK_SERVICE = true;

const API_URL = BASE_URL + "common/profile/";

const updateProfile = async (request) => {
  console.log(request);
  if (USE_MOCK_SERVICE) {
    const response = {
      data: request,
    };

    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(response);
      }, 2000);
    });
  }

  // Request must have ID. Might contain firstName, lastName, birthDate or/and newPassword
  return http().post(API_URL, {
    ...request,
  });
};

const ProfileService = {
  updateProfile,
};

export default ProfileService;
