import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import Action from "../../common-components/Action";
import { Icon } from "@iconify/react";
import ActionsCategory from "../../common-components/home/ActionsCategory";
import CurriculumTable from "./CurriculumTable";
import StudentService from "../../../services/student.service";
import ManageTable from "./ManageTable";

const Courses = ({ specializationID }) => {
  const [coursesVisible, setCoursesVisible] = useState(false);
  const [manageVisible, setManageVisible] = useState(false);
  const [curriculum, setCurriculum] = useState([]);

  const viewCurriculumClicked = () => {
    setCoursesVisible(!coursesVisible);
    setManageVisible(false);
  };

  const manageOptionalsClicked = () => {
    setManageVisible(!manageVisible);
    setCoursesVisible(false);
  };

  useEffect(() => {
    if (!specializationID) return;

    StudentService.getCurriculum(specializationID)
      .then((response) => {
        setCurriculum(response.data);
      })
      .catch((error) => {});
  }, [specializationID]);

  const viewCurriculumAction = (
    <Action
      name="View"
      subname="curriculum"
      icon={<Icon icon="carbon:data-view" color="#bdf841" />}
      clickHandler={viewCurriculumClicked}
      key={1}
    />
  );

  const manageOptionalAction = (
    <Action
      name="Manage"
      subname="optional courses"
      icon={
        <Icon
          icon="fluent:text-bullet-list-square-edit-20-regular"
          color="#bdf841"
        />
      }
      clickHandler={manageOptionalsClicked}
      key={2}
    />
  );

  return (
    <ActionsCategory
      title="Courses"
      actions={[viewCurriculumAction, manageOptionalAction]}
    >
      {coursesVisible && <CurriculumTable courses={curriculum} />}
      {manageVisible && <ManageTable specializationID={specializationID} />}
    </ActionsCategory>
  );
};

Courses.propTypes = {};

export default Courses;
