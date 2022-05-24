import DataTable from "react-data-table-component";
import React, {useEffect, useState} from "react";
import StudentService from "../../../services/student.service";
import {toast} from "react-toastify";
import StaffService from "../../../services/staff.service";
import {DownloadButton} from "./Reports";

const StudentsByGroupTable = ({ groupID }) => {
    const [students, setStudents] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const [errorProduced, setErrorProduced] = useState(false);

    useEffect(() => {
        if (!groupID) return;
        setIsLoading(true);

        if (errorProduced) setErrorProduced(false);
        StaffService.getStudentsByGroup(groupID)
            .then((response) => {
                setStudents(response.data);
            })
            .catch((e) => {
                toast.error(e.message);
                console.log(e);
            })
            .finally(() => setIsLoading(false));
    }, [groupID, errorProduced]);

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

StudentsByGroupTable.propTypes = {};

export default StudentsByGroupTable;
