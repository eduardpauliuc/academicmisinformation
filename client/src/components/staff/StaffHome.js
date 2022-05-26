import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import Navbar from "../Navbar";

import * as HomeStyles from "../common-components/home/HomePage.styles";
import styled from "styled-components/macro";
import Select from "react-select";
import Reports from "./reports/Reports";
import { getFaculties } from "../../services/faculties.service";
import { toast } from "react-toastify";
import StaffService from "../../services/staff.service";
import Actions from "./Actions";

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
  /* width: 150px; */
  font-size: small;
  min-width: 300px;

  &.short {
    min-width: 80px;
  }
`;

const StaffHome = () => {
  const { user } = useSelector((state) => state.auth);

  const [faculties, setFaculties] = useState([]);
  const [specializations, setSpecializations] = useState([]);
  const [selectedSpec, setSelectedSpec] = useState(undefined);
  const [selectedSemester, setSelectedSemester] = useState(undefined);
  const [students, setStudents] = useState([]);

  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    getFaculties()
      .then((response) => {
        setFaculties(response.data);
      })
      .catch((error) => {
        toast.error("Getting faculties:" + error.message);
      });
  }, []);

  useEffect(() => {
    if (!selectedSpec || !selectedSemester) return;

    setIsLoading(true);
    console.log("Getting students");

    StaffService.getStudents(selectedSpec.id, selectedSemester)
      .then((data) => {
        console.log(data);
        setStudents(
          data.data.map((s) => {
            return {
              ...s,
              average: s.average === -1 ? undefined : s.average.toFixed(2),
            };
          })
        );
      })
      .catch((error) => {
        toast.error(error.message);
      })
      .finally(() => setIsLoading(false));
  }, [selectedSemester, selectedSpec]);

  const facultyOptions = faculties.map((faculty) => {
    return {
      value: faculty.facultyId,
      label: faculty.name,
    };
  });

  const specializationOptions = specializations.map((spec) => {
    return {
      value: spec,
      label: `${spec.name} - ${spec.degreeType}`,
    };
  });

  const semesterOptions = () => {
    if (!selectedSpec) return [];

    const numberOfSemesters = selectedSpec.numberOfSemesters;
    return Array.from({ length: numberOfSemesters }, (_, i) => i + 1).map(
      (x) => {
        return { value: x, label: x };
      }
    );
  };

  const facultyChanged = (option) => {
    setSpecializations(
      faculties.find((faculty) => faculty.facultyId === option.value)
        .specializations
    );
    setSelectedSpec(undefined);
    setSelectedSemester(undefined);
  };

  const specChanged = (option) => {
    setSelectedSpec(option.value);
    setSelectedSemester(undefined);
  };

  return (
    <>
      <Navbar user={user} />
      <HomeStyles.HomeContainer>
        <SpecializedContainer>
          <SelectContainer>
            <StyledSelect options={facultyOptions} onChange={facultyChanged} />
            <StyledSelect
              options={specializationOptions}
              value={{
                value: selectedSpec,
                label: selectedSpec?.name,
              }}
              onChange={specChanged}
            />
            <StyledSelect
              className="short"
              options={semesterOptions()}
              value={{
                value: selectedSemester,
                label: selectedSemester,
              }}
              onChange={(optional) => setSelectedSemester(optional.value)}
            />
          </SelectContainer>
          {selectedSemester ? (
            <>
              <Reports students={students} isLoading={isLoading} />

              <Actions
                specialization={selectedSpec}
                semester={selectedSemester}
              />
            </>
          ) : (
            <h2 style={{ color: "white" }}>
              Select faculty, specialization and semester!
            </h2>
          )}
        </SpecializedContainer>
      </HomeStyles.HomeContainer>
    </>
  );
};

// todo: download button for each table
// todo: manage - assign optionals and create groups

export default StaffHome;
