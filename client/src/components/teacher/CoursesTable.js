import React from "react";
import PropTypes from "prop-types";
import DataTable from "react-data-table-component";

const CoursesTable = ({ courses, isLoading }) => {
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
    },
    {
      name: "Credits",
      selector: (row) => row.credits,
    },
    {
      name: "Optional",
      selector: (row) => row.status ?? (row.isOptional && "ACCEPTED"),
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
