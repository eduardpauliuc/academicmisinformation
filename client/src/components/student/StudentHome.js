import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import Select from "react-select";
import StudentService from "../../services/student.service";

import * as HomeStyles from "../common-components/home/HomePage.styles";
import Navbar from "../Navbar";
import Contracts from "./contracts/Contracts";
import Courses from "./courses/Courses";
import Grades from "./grades/Grades";

import styled from "styled-components/macro";
import GeneratePopup from "./contracts/GeneratePopup";
import UploadPopup from "./contracts/UploadPopup";

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

const StyledSelect = styled(Select)`
  width: 300px;
  font-size: small;
`;

const StudentHome = () => {
  const { user } = useSelector((state) => state.auth);

  const [specializations, setSpecializations] = useState([]);
  const [selectedSpec, setSelectedSpec] = useState(undefined);

  const [generatePopupVisible, setGeneratePopupVisible] = useState(false);
  const [uploadPopupVisible, setUploadPopupVisible] = useState(false);

  useEffect(() => {
    StudentService.getStudentSpecializations(user.id).then((response) => {
      setSpecializations(response.data);

      if (response.data.length) setSelectedSpec(response.data[0]);
    });
  }, [user]);

  const onSpecializationChange = (spec) => {
    setSelectedSpec(spec.value);
  };

  let specializationOptions = specializations.map((spec) => {
    return {
      value: spec,
      label: spec.name,
    };
  });

  return (
    <>
      <Navbar user={user} />
      <HomeStyles.HomeContainer>
        <Contracts
          user={user}
          setGenerateVisible={setGeneratePopupVisible}
          setUploadVisible={setUploadPopupVisible}
        />

        <SpecializedContainer>
          <StyledSelect
            options={specializationOptions}
            onChange={onSpecializationChange}
            value={{ value: selectedSpec, label: selectedSpec?.name }}
          />

          <Courses specializationID={selectedSpec?.id} />
          <Grades specializationID={selectedSpec?.id} />
        </SpecializedContainer>
      </HomeStyles.HomeContainer>

      {generatePopupVisible && (
        <GeneratePopup
          closePopup={() => setGeneratePopupVisible(false)}
          user={user}
        />
      )}
      {uploadPopupVisible && (
        <UploadPopup
          closePopup={() => setUploadPopupVisible(false)}
          user={user}
        />
      )}
    </>
  );
};

export default StudentHome;
