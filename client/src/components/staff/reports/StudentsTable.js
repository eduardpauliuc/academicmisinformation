import DataTable from "react-data-table-component";
import React from "react";
import {DownloadButton} from "./Reports";

const StudentsTable = ({ students, isLoading }) => {
    const columns = [
        {
            name: "StudentID",
            selector: (row) => row.studentID,
            grow: 4,
        },
        {
            name: "Average Grade",
            selector: (row) => row.averageGrade,
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
        <DownloadButton>Download</DownloadButton>
    </>
    );
};

StudentsTable.propTypes = {};

export default StudentsTable;
