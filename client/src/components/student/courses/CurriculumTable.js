import React from "react";
import PropTypes from "prop-types";

import DataTable from "react-data-table-component";
import { Icon } from "@iconify/react";

import styled from "styled-components/macro";

const StyledIcon = styled(Icon)`
    font-size: large;
`;

const optionalIcon = <StyledIcon icon="bi:check-circle-fill" color="#f24e1e" />;
const notOptionalIcon = <StyledIcon icon="bi:circle" color="#f24e1e" />;
const CurriculumTable = ({ courses, isLoading }) => {
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
    {
      name: "Optional",
      selector: (row) => (row.optional ? optionalIcon : notOptionalIcon),
      center: true,
    },
  ];

  return (
    <DataTable
      columns={columns}
      data={courses}
      progressPending={isLoading}
      striped
      //   dense
    />
  );
};

CurriculumTable.propTypes = {};

export default CurriculumTable;
