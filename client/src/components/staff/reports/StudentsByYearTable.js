import React, {useEffect, useState} from "react";
import StaffService from "../../../services/staff.service";
import {toast} from "react-toastify";
import DataTable from "react-data-table-component";
import {DownloadButton} from "./Reports";

const StudentsByYearTable = ({ yearID }) => {
    const [students, setStudents] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const [errorProduced, setErrorProduced] = useState(false);

    useEffect(() => {
        if (!yearID) return;
        setIsLoading(true);

        if (errorProduced) setErrorProduced(false);
        StaffService.getStudentsByYear(yearID)
            .then((response) => {
                setStudents(response.data);
            })
            .catch((e) => {
                toast.error(e.message);
                console.log(e);
            })
            .finally(() => setIsLoading(false));
    }, [yearID, errorProduced]);

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

StudentsByYearTable.propTypes = {};

export default StudentsByYearTable;
