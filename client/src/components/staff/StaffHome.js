import React, {useEffect, useState} from "react";
import { useSelector } from "react-redux";
import Navbar from "../Navbar";


import * as HomeStyles from "../common-components/home/HomePage.styles";
import styled from "styled-components/macro";
import Select from "react-select";
import Contracts from "../student/contracts/Contracts";
import GeneratePopup from "../student/contracts/GeneratePopup";
import Reports from "./reports/Reports";
import Grades from "../student/grades/Grades";
import StudentService from "../../services/student.service";
import StaffService from "../../services/staff.service";
import Courses from "../student/courses/Courses";
import {StyledButton} from "../helpers/Button.style";

const SpecializedContainer = styled.div`
  background-color: #58bef7;
  display: flex;
  max-width: 1000px;
  width: 100%;
  flex-direction: column;
  gap: 30px;
  padding: 20px;
  margin: 0px -20px 0px -25px;
  border-radius: 20px;
`;

const SelectContainer = styled.div`
    display: flex;
    gap: 10px;
`;

const StyledSelect = styled(Select)`
  width: 150px;
  font-size: small;
`;

const StaffHome = () => {
    const { user } = useSelector((state) => state.auth);

    const [groups, setGroups] = useState([]);
    const [selectedGroup, setSelectedGroup] = useState(undefined);

    const [academicYears, setAcademicYears] = useState([]);
    const [selectedAcademicYear, setSelectedAcademicYear] = useState(undefined);

    useEffect(() => {
        StaffService.getGroups(user.id).then((response) => {
            setGroups(response.data);

            if (response.data.length) setSelectedGroup(response.data[0]);
        });
    }, [user]);

    const onGroupChange = (group) => {
        setSelectedGroup(group.value);
    };

    let groupOptions = groups.map((group) => {
        return {
            value: group,
            label: group.name,
        };
    });

    useEffect(() => {
        StaffService.getAcademicYears(user.id).then((response) => {
            setAcademicYears(response.data);

            if (response.data.length) setSelectedAcademicYear(response.data[0]);
        });
    }, [user]);

    const onAcademicYearChange = (year) => {
        setSelectedAcademicYear(year.value);
    };

    let academicYearOptions = academicYears.map((year) => {
        return {
            value: year,
            label: year.name,
        };
    });

  return (
    <>
        <Navbar user={user}/>
        <HomeStyles.HomeContainer>
            <SpecializedContainer>
                <SelectContainer>
                    <StyledSelect
                        options= {groupOptions}
                        onChange= {onGroupChange}
                        value= {{ value: selectedGroup, label: selectedGroup?.name }}
                    />
                    <StyledSelect
                        options= {academicYearOptions}
                        onChange= {onAcademicYearChange}
                        value= {{ value: selectedAcademicYear, label: selectedAcademicYear?.name }}
                    />
                </SelectContainer>
                <Reports groupID={selectedGroup?.id} yearID={selectedAcademicYear?.id} />
            </SpecializedContainer>
        </HomeStyles.HomeContainer>
    </>
    )
};

// todo: download button for each table
// todo: manage - assign optionals and create groups

export default StaffHome;
