import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import ActionsCategory from "../../common-components/home/ActionsCategory";
import Action from "../../common-components/Action";
import { Icon } from "@iconify/react";
import StudentService from "../../../services/student.service";
import GradesTable from "./GradesTable";

const Grades = ({ specializationID }) => {
  const [grades, setGrades] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [gradesVisible, setGradesVisible] = useState(true);

  const manageViewClicked = () => {
    setGradesVisible(!gradesVisible);
  };

  useEffect(() => {
    if (!specializationID) return;

    StudentService.getGrades(specializationID)
      .then((response) => {
        setGrades(response.data);
      })
      .finally(() => setIsLoading(false));
  }, [specializationID]);

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
    <ActionsCategory title="Grades" actions={[manageOptionalAction]}>
      {gradesVisible && <GradesTable grades={grades} isLoading={isLoading} />}
    </ActionsCategory>
  );
};

Grades.propTypes = {};

export default Grades;
