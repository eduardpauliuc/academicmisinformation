import React from "react";
import PropTypes from "prop-types";
import ActionsCategory from "../../common-components/home/ActionsCategory";
import Action from "../../common-components/Action";
import { Icon } from "@iconify/react";

const Grades = (props) => {
  const manageViewClicked = () => {};

  const manageOptionalAction = (
    <Action
      name="View"
      subname="grades"
      icon={<Icon icon="ant-design:fund-view-outlined" color="#bdf841" />}
      clickHandler={manageViewClicked}
      key={1}
    />
  );

  return (
    <ActionsCategory
      title="Grades"
      actions={[manageOptionalAction]}
    ></ActionsCategory>
  );
};

Grades.propTypes = {};

export default Grades;
