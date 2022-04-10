import React, { useState } from "react";
import { useSelector } from "react-redux";
import ActionsCategory from "../common-components/home/ActionsCategory";
import { HomeContainer } from "../common-components/home/HomePage.styles";
import NavBar from "../Navbar";
import styled from "styled-components/macro";
import { Icon } from "@iconify/react";
import CreateAccountPopup from "./CreateAccountPopup";

const AddIcon = styled(Icon)`
  margin-left: auto;
  font-size: xx-large;
`;

const AdminHome = () => {
  const { user } = useSelector((state) => state.auth);

  const [addPopupVisible, setAddPopupVisible] = useState(false);

  const handleAddClicked = () => setAddPopupVisible(true);

  const handleClosePopup = () => setAddPopupVisible(false);

  return (
    <>
      <NavBar user={user} />
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
        ></ActionsCategory>
      </HomeContainer>

      {addPopupVisible && <CreateAccountPopup closePopup={handleClosePopup} />}
    </>
  );
};

export default AdminHome;
