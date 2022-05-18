import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import StudentService from "../../../services/student.service";
import { toast } from "react-toastify";

import styled from "styled-components/macro";
import { Icon } from "@iconify/react";

const ButtonsContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

const StyledIcon = styled(Icon)`
  font-size: large;
`;

const ManageTable = ({ specializationID }) => {
  const [optionals, setOptionals] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const [errorProduced, setErrorProduced] = useState(false);

  useEffect(() => {
    if (!specializationID) return;
    setIsLoading(true);

    if (errorProduced) setErrorProduced(false);
    StudentService.getOptionalsOrder(specializationID)
      .then((response) => {
        setOptionals(response.data);
      })
      .catch((e) => {
        toast.error(e.message);
        console.log(e);
      })
      .finally(() => setIsLoading(false));
  }, [specializationID, errorProduced]);

  const saveOptionals = (optionals_list) => {
    setOptionals(optionals_list);
    console.log(optionals_list);
    const request = optionals_list
      .sort((a, b) => a.rank - b.rank)
      .map((o) => ({ optionalId: o.course.id, index: o.rank }));
    console.log(request);

    StudentService.setOptionalsOrder(specializationID, request)
      .then(() => {
      })
      .catch((e) => {
        toast.error(e.message);
        setErrorProduced(true);
      });
  };

  const handleMoveDown = (id, index) => {
    if (index === optionals.length) return;

    let optionals_list = [...optionals];
    optionals_list.find((o) => o.rank === index + 1).rank -= 1;
    optionals_list.find((o) => o.course.id === id).rank += 1;

    saveOptionals(optionals_list);
  };
  const handleMoveUp = (id, index) => {
    if (index === 1) return;

    let optionals_list = [...optionals];
    optionals_list.find((o) => o.rank === index - 1).rank += 1;
    optionals_list.find((o) => o.course.id === id).rank -= 1;

    saveOptionals(optionals_list);
  };

  const buttons = (id, index) => (
    <ButtonsContainer>
      <StyledIcon
        icon="bxs:chevron-up"
        color="#f24e1e"
        onClick={() => handleMoveUp(id, index)}
      />
      <StyledIcon
        icon="bxs:chevron-down"
        color="#f24e1e"
        onClick={() => handleMoveDown(id, index)}
      />
    </ButtonsContainer>
  );

  const columns = [
    {
      name: "Index",
      selector: (row) => row.rank,
    },
    {
      name: "Name",
      selector: (row) => row.course.name,
      grow: 4,
    },
    {
      name: "Credits",
      selector: (row) => row.course.credits,
    },
    {
      name: "Reorder",
      cell: (row) => buttons(row.course.id, row.rank),
      button: true,
    },
  ];

  return (
    <DataTable
      columns={columns}
      data={optionals}
      progressPending={isLoading}
      striped
      defaultSortFieldId={1}
    />
  );
};

ManageTable.propTypes = {};

export default ManageTable;
