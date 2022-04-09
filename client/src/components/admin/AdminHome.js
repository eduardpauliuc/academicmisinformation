import React from "react";
import { useSelector } from "react-redux";
import NavBar from "../Navbar";

const AdminHome = () => {
  const { user } = useSelector((state) => state.auth);

  return (
    <>
      <NavBar user={user}/>
      Admin home
    </>
  );
};

export default AdminHome;
