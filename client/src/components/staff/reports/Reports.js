import React, { useEffect, useState } from "react";
import ActionsCategory from "../../common-components/home/ActionsCategory";
import StudentsTable from "./StudentsTable";
import styled from "styled-components/macro";
import { StyledButton } from "../../helpers/Button.style";
import reactSelect from "react-select";
import { CSVLink } from "react-csv";
export const DownloadButton = styled(StyledButton)`
  margin-left: auto;
  display: block;
  margin-top: 15px;
`;

const GroupContainer = styled.div`
  display: flex;
  align-items: center;
  padding: 10px;
  gap: 10px;
  position: absolute;
  right: 20px;
  top: 55px;
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 10px;

  & > label {
    color: white;
  }
`;

const StyledSelect = styled(reactSelect)`
  /* width: 150px; */
  font-size: small;
  width: 200px;
`;

const headers = [
  { label: "Student Name", key: "name" },
  { label: "Grade", key: "average" },
  { label: "Group", key: "group" },
];

const StyledCSV = styled(CSVLink)`
  color: darkgreen;
  text-decoration: none;
`;

const Reports = ({ students, isLoading }) => {
  const [filteredStudents, setFilteredStudents] = useState([]);
  const [groups, setGroups] = useState([]);
  const [selectedGroup, setSelectedGroup] = useState(undefined);
  useEffect(() => {
    if (!students) {
      setGroups([]);
      return;
    }

    const computedGroups = [
      ...new Set(students.map((s) => (s.group ? s.group : "unassigned"))),
    ];
    setGroups(computedGroups);
  }, [students]);

  useEffect(() => {
    if (!students) return;
    if (selectedGroup)
      setFilteredStudents(
        students.filter((s) =>
          selectedGroup !== "unassigned" ? s.group === selectedGroup : !s.group
        )
      );
    else setFilteredStudents(students);
  }, [selectedGroup, students]);

  const onGroupChange = (group) => {
    setSelectedGroup(group?.value);
  };

  let groupOptions = groups.map((group) => {
    return {
      value: group,
      label: group,
    };
  });

  return (
    <ActionsCategory onlyHeader title="Reports">
      <GroupContainer>
        <label>Group</label>
        <StyledSelect
          options={groupOptions}
          value={{
            value: selectedGroup,
            label: selectedGroup,
          }}
          onChange={onGroupChange}
          isClearable={true}
        />
      </GroupContainer>
      <StudentsTable students={filteredStudents} isLoading={isLoading} />

      <DownloadButton>
        <StyledCSV
          headers={headers}
          data={filteredStudents}
          filename="students"
        >
          Export to CSV
        </StyledCSV>
      </DownloadButton>
    </ActionsCategory>
  );
};

Reports.propTypes = {};

export default Reports;
