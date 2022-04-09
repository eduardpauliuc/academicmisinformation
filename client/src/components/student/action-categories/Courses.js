import React from "react";
import PropTypes from "prop-types";
import Action from "../../common-components/Action";
import { Icon } from "@iconify/react";
import ActionsCategory from "../../common-components/home/ActionsCategory";

const Courses = (props) => {
  const viewCurriculumClicked = () => {};

  const manageOptionalsClicked = () => {};

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
    ></ActionsCategory>
  );
};

Courses.propTypes = {};

export default Courses;
