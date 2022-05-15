import React, { useState } from "react";
import { useSelector } from "react-redux";
import Navbar from "../Navbar";

import Courses from "../teacher/Courses";
import ProposePopup from "../teacher/ProposePopup";
import AddGradePopup from "../teacher/AddGradePopup";

import * as HomeStyles from "../common-components/home/HomePage.styles";
import Manage from "./Manage";
import Teachers from "./Teachers";

const ChiefHome = () => {
  const { user } = useSelector((state) => state.auth);

  const [proposePopupVisible, setProposePopupVisible] = useState(false);
  const [gradePopupVisible, setGradePopupVisible] = useState(false);

  const [courses, setCourses] = useState([]);

  return (
    <>
      <Navbar user={user} />
      <HomeStyles.HomeContainer>
        <Courses
          courses={courses}
          setCourses={setCourses}
          setProposePopupVisible={setProposePopupVisible}
          setGradePopupVisible={setGradePopupVisible}
        />

        {proposePopupVisible && (
          <ProposePopup closePopup={() => setProposePopupVisible(false)} />
        )}

        {gradePopupVisible && (
          <AddGradePopup
            closePopup={() => setGradePopupVisible(false)}
            courses={courses}
          />
        )}

        <Manage />
        <Teachers />
      </HomeStyles.HomeContainer>
    </>
  );
};
export default ChiefHome;
