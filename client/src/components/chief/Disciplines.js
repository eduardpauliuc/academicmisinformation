import React, { useEffect, useState } from "react";

import styled from "styled-components/macro";
import ChiefService from "../../services/chief.service";
import { toast } from "react-toastify";
import Select from "react-select";
import DataTable from "react-data-table-component";
import { Label } from "../helpers/PopupForm.styles";

const StyledSelect = styled(Select)`
  width: 300px;
  font-size: small;
`;

const Container = styled.div`
  display: flex;
  gap: 20px;
  flex-direction: column;
`;

const Disciplines = () => {
  const [teachers, setTeachers] = useState([]);
  const [selectedTeacher, setSelectedTeacher] = useState(undefined);
  const [isLoading, setIsLoading] = useState(true);

  const [isDisciplinesLoading, setIsDisciplinesLoading] = useState(false);

  const [disciplines, setDisciplines] = useState([]);

  useEffect(() => {
    ChiefService.getAllTeachers()
      .then((data) => {
        setTeachers(data.data);
      })
      .catch((err) => {
        console.log(err);
        toast.error("Getting optionals error:", err.error.message);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  useEffect(() => {
    if (selectedTeacher) {
      console.log("Getting discplines for", selectedTeacher);
      setIsDisciplinesLoading(true);

      ChiefService.getTeacherDisciplines(selectedTeacher.id)
        .then((data) => {
          setDisciplines(data.data);
        })
        .catch((err) => {
          console.log(err);
          toast.error("Getting disciplines error:", err.error.message);
        })
        .finally(() => {
          setIsDisciplinesLoading(false);
        });
    }
  }, [selectedTeacher]);

  let teacherOptions = teachers.map((teacher) => {
    return {
      value: teacher,
      label: teacher.name,
    };
  });

  const onTeacherChange = (teacher) => {
    setSelectedTeacher(teacher);
  };

  const columns = [
    {
      name: "Name",
      selector: (row) => row.name,
      grow: 4,
    },
    {
      name: "Credits",
      selector: (row) => row.credits,
    },
  ];

  return (
    <Container>
      <div>
        <Label>Select a teacher:</Label>
        <StyledSelect
          options={teacherOptions}
          onChange={onTeacherChange}
          value={
            isLoading
              ? { label: "Loading..." }
              : { value: selectedTeacher, label: selectedTeacher?.name }
          }
          // value={{ value: selectedTeacher, label: selectedTeacher?.name }}
          disabled={isLoading}
        />
      </div>
      <DataTable
        columns={columns}
        data={disciplines}
        striped
        progressPending={isDisciplinesLoading}
      />
    </Container>
  );
};

export default Disciplines;
