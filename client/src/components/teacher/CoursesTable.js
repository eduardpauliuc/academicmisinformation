import React from "react";
import PropTypes from "prop-types";
import DataTable from "react-data-table-component";
import { Icon } from "@iconify/react";
import styled from "styled-components/macro";
import {toast} from "react-toastify";

const StyledIcon = styled(Icon)`
  font-size: x-large;
`;

const CoursesTable = ({ courses, isLoading }) => {
  const getOptionalField = (row) => {
    console.log(row);
    if (row.status === "REJECTED")
      return (
        <StyledIcon
          icon="ant-design:close-outlined"
          color="#f24e1e"
          style={{ cursor: "pointer" }}
          onClick={() => toast.info("Rejection reason: " + row.message)}
        />
      );

    if (row.status === "PENDING")
      return <StyledIcon icon="eos-icons:loading" color="#ffc700" />;

    if (row.optional)
      return <StyledIcon icon="akar-icons:check" color="green" />;

    return row.status ?? (row.optional && "ACCEPTED");
  };

  const columns = [
    {
      name: "Course",
      selector: (row) => row.name,
      grow: 4,
    },
    {
      name: "Specialization",
      selector: (row) => row.specializationName,
      grow: 3,
    },
    {
      name: "Semester",
      selector: (row) => row.semesterNumber,
      center: true,
    },
    {
      name: "Credits",
      selector: (row) => row.credits,
      center: true,
    },
    {
      name: "Optional",
      selector: (row) => getOptionalField(row),
      center: true,
    },
  ];

  return (
    <DataTable
      columns={columns}
      data={courses}
      progressPending={isLoading}
      striped
      keyField="name"
    />
  );
};
CoursesTable.propTypes = {
  courses: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string,
      specializationName: PropTypes.string,
      credits: PropTypes.number,
      semesterNumber: PropTypes.number,
      status: PropTypes.oneOf([null, "REJECTED", "PENDING"]),
      isOptional: PropTypes.oneOf([null, false, true]),
    })
  ),
  isLoading: PropTypes.bool,
};

export default CoursesTable;
