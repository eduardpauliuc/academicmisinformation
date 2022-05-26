import DataTable from "react-data-table-component";
import React from "react";
import { DownloadButton } from "./Reports";

const StudentsTable = ({ students, isLoading }) => {
  const columns = [
    {
      name: "Student name",
      selector: (row) => row.name,
      grow: 4,
    },
    {
      name: "Average Grade",
      selector: (row) => row.average,
    },
    {
      name: "Group",
      selector: (row) => row.group,
    },
  ];

  return (
    <>
      <DataTable
        columns={columns}
        data={students}
        progressPending={isLoading}
        striped
        //   dense
      />
    </>
  );
};

StudentsTable.propTypes = {};

export default StudentsTable;
