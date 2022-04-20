import React from "react";
import PropTypes from "prop-types";

import DataTable from "react-data-table-component";

const GradesTable = ({ grades, isLoading }) => {
  const columns = [
    {
      name: "Course",
      selector: (row) => row.courseName,
      grow: 4,
    },
    {
      name: "Grade",
      selector: (row) => row.grade,
    },
  ];

  return (
    <DataTable
      columns={columns}
      data={grades}
      progressPending={isLoading}
      striped
      //   dense
    />
  );
};

GradesTable.propTypes = {};

export default GradesTable;
