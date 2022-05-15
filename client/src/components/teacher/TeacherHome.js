import React from "react";
import { useSelector } from "react-redux";
import Navbar from "../Navbar";

import * as HomeStyles from "../common-components/home/HomePage.styles";
import Courses from "./Courses";

const TeacherHome = () => {
  const { user } = useSelector((state) => state.auth);

  return (
    <>
      <Navbar user={user} />
      <HomeStyles.HomeContainer>
        <Courses />
      </HomeStyles.HomeContainer>
    </>
  );
};

export default TeacherHome;
