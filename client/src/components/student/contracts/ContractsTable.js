import React from "react";
import PropTypes from "prop-types";

import DataTable from "react-data-table-component";

const ContractsTable = ({ contracts, isLoading }) => {
  const columns = [
    {
      name: "Specialization",
      selector: (row) => row.specializationName,
      grow: 2,
    },
    {
      name: "Start date",
      selector: (row) => row.startDate,
    },
    {
      name: "End date",
      selector: (row) => row.endDate,
    },
    {
      name: "Semester",
      selector: (row) => row.semester,
    },
  ];

  return (
    <DataTable
      columns={columns}
      data={contracts}
      progressPending={isLoading}
      striped
      dense
    />
  );
};

ContractsTable.propTypes = {};

export default ContractsTable;
