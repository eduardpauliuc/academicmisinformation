import React, { useEffect, useState } from "react";
// import PropTypes from "prop-types";

import ActionsCategory from "../common-components/home/ActionsCategory";
import Action from "../common-components/Action";
import { Icon } from "@iconify/react";
import TeacherService from "../../services/teacher.service";
import { toast } from "react-toastify";
import CoursesTable from "./CoursesTable";

const Courses = ({
  setProposePopupVisible,
  setGradePopupVisible,
  courses,
  setCourses,
}) => {
  const [coursesLoading, setCoursesLoading] = useState(true);

  useEffect(() => {
    TeacherService.getTeacherCourses()
      .then((response) => {
        setCourses(response.data);
      })
      .catch((e) => {
        toast.error(e.message);
      })
      .finally(() => setCoursesLoading(false));
  }, []);

  const optionalCreateClicked = () => {
    setProposePopupVisible(true);
  };

  const gradeClicked = () => {
    setGradePopupVisible(true);
  };

  const optionalCreateAction = (
    <Action
      name="Optional"
      subname="create proposal"
      icon={<Icon icon="gridicons:create" color="#bdf841" />}
      clickHandler={optionalCreateClicked}
      key={1}
    />
  );

  const gradeAction = (
    <Action
      name="Grade"
      subname="students"
      icon={
        <Icon
          icon="fluent:calligraphy-pen-checkmark-20-regular"
          color="#bdf841"
        />
      }
      clickHandler={gradeClicked}
      key={2}
    />
  );

  return (
    <ActionsCategory
      title="Courses"
      actions={[optionalCreateAction, gradeAction]}
    >
      <CoursesTable isLoading={coursesLoading} courses={courses} />
    </ActionsCategory>
  );
};

Courses.propTypes = {};

export default Courses;
