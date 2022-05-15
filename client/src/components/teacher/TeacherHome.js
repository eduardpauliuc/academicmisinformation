import React, { useState } from "react";
import { useSelector } from "react-redux";
import Navbar from "../Navbar";

import * as HomeStyles from "../common-components/home/HomePage.styles";
import Courses from "./Courses";
import ProposePopup from "./ProposePopup";
import AddGradePopup from "./AddGradePopup";

const TeacherHome = () => {
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
      </HomeStyles.HomeContainer>
    </>
  );
};

export default TeacherHome;
