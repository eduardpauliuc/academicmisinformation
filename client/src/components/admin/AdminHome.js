import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import ActionsCategory from "../common-components/home/ActionsCategory";
import { HomeContainer } from "../common-components/home/HomePage.styles";
import NavBar from "../Navbar";
import styled from "styled-components/macro";
import { Icon } from "@iconify/react";
import CreateAccountPopup from "./CreateAccountPopup";
import UsersTable from "./UsersTable";
import AdminService from "../../services/admin.service";

import { toast } from "react-toastify";

const AddIcon = styled(Icon)`
  margin-left: auto;
  font-size: xx-large;
`;

const AdminHome = () => {
  const { user } = useSelector((state) => state.auth);

  const [accounts, setAccounts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const [addPopupVisible, setAddPopupVisible] = useState(false);

  const handleAddClicked = () => setAddPopupVisible(true);

  const handleClosePopup = () => setAddPopupVisible(false);

  useEffect(() => {
    console.log("getting accounts");
    AdminService.getAllUsers().then((response) => {
      console.log(response.data);
      setIsLoading(false);
      setAccounts(response.data);
    }).catch((e) => {
      toast.error(e.message);
      setIsLoading(false);
    });
  }, []);

  const deleteAccount = (accToDelete) => {
    AdminService.deleteUser(accToDelete.id).then(
      response => {
        toast.success("Account deleted!");
        setAccounts(accounts.filter((acc) => acc !== accToDelete));
      },
      error => {
        console.log("Something went wrong");
      }
    );
  };

  const addAccount = (newAccount) => {
    setAccounts([...accounts, newAccount]);
  };

  return (
    <>
      <NavBar user={user}/>
      <HomeContainer>
        <ActionsCategory
          title="Accounts"
          otherHeaderContent={
            <AddIcon
              icon="carbon:add-alt"
              color="#bdf841"
              onClick={handleAddClicked}
            />
          }
        >
          <UsersTable
            accounts={accounts}
            isLoading={isLoading}
            deleteAccount={deleteAccount}
          />
        </ActionsCategory>
      </HomeContainer>

      {addPopupVisible && (
        <CreateAccountPopup
          closePopup={handleClosePopup}
          addAccount={addAccount}
        />
      )}
    </>
  );
};

export default AdminHome;
