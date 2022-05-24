import React, { useEffect, useState } from "react";
import Action from "../../common-components/Action";
import {Icon} from "@iconify/react/dist/iconify";
import ActionsCategory from "../../common-components/home/ActionsCategory";
import StaffService from "../../../services/staff.service";
import StudentsTable from "./StudentsTable";
import StudentService from "../../../services/student.service";
import StudentsByGroupTable from "./StudentsByGroupTable";
import StudentsByYearTable from "./StudentsByYearTable";
import styled from "styled-components/macro";
import {StyledButton} from "../../helpers/Button.style";

export const DownloadButton = styled(StyledButton)`
  margin-left: auto;
  display: block;
`;

const Reports = ({ groupID, yearID}) => {
    const [reports, setReports] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const [studentsVisible, setStudentsVisible] = useState(false);
    const [studentsByGroupsVisible, setStudentsByGroupsVisible] = useState(false);
    const [studentsByYearsVisible, setStudentsByYearsVisible] = useState(false);

    const allStudentsClicked = () => {
        setStudentsVisible(!studentsVisible);
        setStudentsByGroupsVisible(false);
        setStudentsByYearsVisible(false);
    };

    const groupsClicked = () => {
        setStudentsByGroupsVisible(!studentsByGroupsVisible);
        setStudentsVisible(false);
        setStudentsByYearsVisible(false);
    };

    const academicYearClicked = () => {
        setStudentsByYearsVisible(!studentsByYearsVisible);
        setStudentsVisible(false);
        setStudentsByGroupsVisible(false);
    }

    useEffect(() => {
        if (!groupID) return;

        StaffService.getStudentsByGroup(groupID)
            .then((response) => {
                setReports(response.data);
            })
            .catch((error) => {});
    }, [groupID]);

    useEffect(() => {
        if (!yearID) return;

        StaffService.getStudentsByYear(yearID)
            .then((response) => {
                setReports(response.data);
            })
            .catch((error) => {});
    }, [yearID]);

    useEffect(() => {
        StaffService.getStudents()
            .then((response) => {
                setReports(response.data);
            })
            .finally(() => setIsLoading(false));
    });

    const getGroupsAction = (
        <Action
            name="Students by"
            subname="Group"
            icon={<Icon icon="carbon:data-view" color="#bdf841"/>}
            clickHandler={groupsClicked}
            key={1}
        />
    )

    const getYearsAction = (
        <Action
            name="Students by"
            subname="Academic Year"
            icon={<Icon icon="carbon:data-view" color="#bdf841"/>}
            clickHandler={academicYearClicked}
            key={2}
        />
    )

    const getAllStudentsAction = (
        <Action
            name="All"
            subname="Students"
            icon={<Icon icon="carbon:data-view" color="#bdf841"/>}
            clickHandler={allStudentsClicked}
            key={3}
        />
    );

    return (
        <ActionsCategory title="Reports" actions={[getGroupsAction, getYearsAction, getAllStudentsAction]}>
            {studentsByGroupsVisible && <StudentsByGroupTable groupID={groupID} /> }
            {studentsByYearsVisible && <StudentsByYearTable yearID={yearID} />}
            {studentsVisible && <StudentsTable students={reports} isLoading={isLoading} />}
        </ActionsCategory>
    );
}

Reports.propTypes = {};

export default Reports;