import React, { useCallback, useState } from "react";
import PropTypes from "prop-types";

import DataTable from "react-data-table-component";

import styled from "styled-components";
import Role, { readableRole } from "../../helpers/role";
import { Icon } from "@iconify/react";

const UsersTable = ({ accounts, isLoading, deleteAccount }) => {
  const columns = [
    {
      name: "Username",
      selector: (row) => row.username,
    },
    {
      name: "Email",
      selector: (row) => row.email,
      grow: 2,
    },
    {
      name: "Role",
      selector: (row) => readableRole(row.role),
    },
    {
      name: "Firstname",
      selector: (row) => row.firstName,
    },
    {
      name: "Lastname",
      selector: (row) => row.lastName,
    },
    {
      name: "Birthdate",
      selector: (row) => row.birthDate,
      hide: "lg",
    },
    {
      name: "Delete",
      cell: (row) => (
        <Icon
          icon="fluent:delete-20-filled"
          color="#f24e1e"
          style={{ fontSize: "large" }}
          onClick={() => handleDeleteClicked(row)}
        />
      ),
      button: true,
    },
  ];

  const handleDeleteClicked = (account) => {
    let confirmation = window.confirm(
      `Are you sure that you want to delete account ${account.username}?`
    );

    if (confirmation) {
      console.log("Deleting account with id " + account.id);

      deleteAccount(account);
    }
  };

  return (
    <DataTable
      data={accounts}
      columns={columns}
      progressPending={isLoading}
      striped
    />
  );
};

UsersTable.propTypes = {};

export default UsersTable;
