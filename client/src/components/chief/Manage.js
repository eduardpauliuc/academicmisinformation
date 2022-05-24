import { Icon } from "@iconify/react";
import React, { useEffect, useState } from "react";
import DataTable from "react-data-table-component";
import { toast } from "react-toastify";
import ChiefService from "../../services/chief.service";
import Action from "../common-components/Action";
import ActionsCategory from "../common-components/home/ActionsCategory";
import styled from "styled-components/macro";

const ButtonsContainer = styled.div`
  width: 100%;
  display: flex;
  flex: row;
  gap: 10px;

  & * {
    font-size: x-large;
  }

  & > :hover {
    cursor: pointer;
  }
`;

const Manage = ({}) => {
  const [isLoading, setIsLoading] = useState(true);
  const [isReviewing, setIsReviewing] = useState(false);
  const [optionals, setOptionals] = useState([]);

  useEffect(() => {
    ChiefService.getOptionals()
      .then((data) => {
        console.log("Got optionals: ", data);
        setOptionals(
          data.data.filter((optional) => optional.status !== "REJECTED")
        );
      })
      .catch((err) => {
        console.log(err);
        toast.error("Getting optionals error:", err.error.message);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  const reviewOptional = (optional, accepted) => {
    let message = "";
    if (!accepted) {
      message = window.prompt("Rejection reason", "");
      if (message == null) {
        console.log("CANCELED");
        return;
      }
    }
    setIsReviewing(true);

    const reviewOptional = {
      reviewMessage: message,
      status: accepted,
    };

    ChiefService.reviewOptional(reviewOptional)
      .then(() => {
        setOptionals((previous) => {
          var otherOptionals = previous.filter((o) => o.id !== optional.id);
          if (!accepted) {
            return otherOptionals;
          }
          return [...otherOptionals, { ...optional, status: "APPROVED" }];
        });
      })
      .catch((err) => {
        console.log("Error reviewing!", err);
        toast.error(err.error.message);
      })
      .finally(() => setIsReviewing(false));
  };

  const actionButtons = (optional) => {
    return (
      <ButtonsContainer>
        <Icon
          icon="akar-icons:circle-check-fill"
          color={isReviewing ? "gray" : "#f24e1e"}
          style={isReviewing && { pointerEvents: "none" }}
          onClick={() => reviewOptional(optional, true)}
        />
        <Icon
          icon="akar-icons:circle-x"
          color={isReviewing ? "gray" : "#f24e1e"}
          style={isReviewing && { pointerEvents: "none" }}
          onClick={() => reviewOptional(optional, false)}
        />
      </ButtonsContainer>
    );
  };

  const columns = [
    {
      name: "Optional",
      selector: (row) => row.name,
      grow: 4,
    },
    {
      name: "Teacher",
      selector: (row) => row.teacherName,
      grow: 4,
    },
    {
      name: "Specialization",
      selector: (row) => row.specializationName,
      grow: 3,
    },
    {
      name: "Semester",
      selector: (row) => row.semesterNumber,
    },
    {
      name: "Credits",
      selector: (row) => row.credits,
    },
    {
      name: "Students",
      selector: (row) => row.maximumStudentsNumber,
    },
    {
      name: "Review",
      selector: (row) => row.status === "PENDING" && actionButtons(row),
    },
  ];
  return (
    <ActionsCategory onlyHeader title="Manage optionals">
      <DataTable
        columns={columns}
        data={optionals}
        progressPending={isLoading}
        striped
      />
    </ActionsCategory>
  );
};

export default Manage;
