import React, { useState } from "react";
import styled from "styled-components/macro";
import { useTheme } from "styled-components";
import { useDispatch } from "react-redux";
import { logout } from "../slices/authSlice";

import { Icon } from "@iconify/react";
import Role, { readableRole } from "../helpers/role";
import { FACULTY_NAME } from "../helpers/constants";
import ConfigureProfilePopup from "./ConfigureProfilePopup";

const NavContainer = styled.div`
  /* width: 100%; */
  height: 30px;
  padding: 15px 30px;
  margin: 0px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: end;
  gap: 20px;

  /* background-color: darkblue; */

  & h3,
  & h4 {
    color: white;
  }

  & .title {
    margin-right: auto;
  }
`;

const ProfileContainer = styled.div`
  /* display: flex; */

  & > * {
    display: inline;
    margin: 0px 10px;
  }

  position: relative;
  //min-width: 150px;
`;

const ProfileInfoPopup = styled.div`
  position: absolute;
  background-color: white;
  z-index: 10;
  /* height: 100px; */
  width: 100%;
  left: 10px;
  top: 30px;
  border-radius: 0px 20px 20px 20px;
  filter: drop-shadow(4px 5px 14px rgba(0, 0, 0, 0.25));

  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px 10px;
  gap: 15px;

  & * {
    color: black;
  }

  & #role {
    font-size: large;
  }

  & #faculty {
    font-size: small;
  }

  & #settings {
    font-size: small;
    display: flex;
    direction: row;
    gap: 5px;
    align-items: center;
  }
`;

const Navbar = ({ user }) => {
  const dispatch = useDispatch();
  const theme = useTheme();

  const [profileVisible, setProfileVisible] = useState(false);
  const [configureVisible, setConfigureVisible] = useState(false);

  const handleLogout = () => {
    dispatch(logout());
  };

  const showManagePopup = () => {
    setProfileVisible(false);
    setConfigureVisible(true);
  };

  const hideManagePopup = () => {
    setConfigureVisible(false);
  };

  const getIconForRole = (role) => {
    switch (role) {
      case Role.Admin:
        return <Icon icon="icons8:student" color="#bdf841"/>;
      case Role.Student:
        return <Icon icon="icons8:student" color="#bdf841"/>;
      case Role.Chief:
        return <Icon icon="ic:baseline-supervisor-account" color="#bdf841"/>;
      case Role.Teacher:
        return <Icon icon="la:chalkboard-teacher" color="#bdf841"/>;
      case Role.Staff:
        return <Icon icon="icon-park-outline:file-staff-one" color="#bdf841"/>;
      default:
        return undefined;
    }
  };

  return (
    <>
      <NavContainer>
        <h3 className="title">
          Academic
          <span style={{ color: theme.accent }}>Misinformation</span>
        </h3>
        <ProfileContainer>
          {getIconForRole(user.role)}
          <h4 onClick={() => setProfileVisible(!profileVisible)}>
            {(!user.firstName && !user.lastName && <span>Configure profile</span>)}
            {user && (
              <>
                {user.firstName} {user.lastName}
              </>
            )}
          </h4>
          {profileVisible && (
            <ProfileInfoPopup>
              <div id="role">{readableRole(user.role)}</div>
              <div id="faculty">{FACULTY_NAME}</div>
              <div id="settings" onClick={showManagePopup}>
                Manage profile
                <Icon icon="ci:settings-filled" color="#bdf841"/>
              </div>
            </ProfileInfoPopup>
          )}
        </ProfileContainer>
        <Icon onClick={handleLogout} icon="mdi:logout" color="#bdf841"/>
      </NavContainer>
      {configureVisible && (
        <ConfigureProfilePopup user={user} closePopup={hideManagePopup}/>
      )}
    </>
  );
};

export default Navbar;
