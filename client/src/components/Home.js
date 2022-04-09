import React from "react";
import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import Role from "../helpers/role";

function Home() {
  const { user } = useSelector((state) => state.auth);

  const renderSwitch = () => {
    switch (user.role) {
      case Role.Admin:
        return <Navigate to="/admin" replace />;
      case Role.Student:
        return <Navigate to="/student" replace />;
      case Role.Chief:
        return <Navigate to="/chief" replace />;
      case Role.Staff:
        return <Navigate to="/staff" replace />;
      case Role.Teacher:
        return <Navigate to="/teacher" replace />;
      default:
        return <div>Invalid role!</div>;
    }
  };

  return <>{!user ? <div>Not logged in</div> : <>{renderSwitch()}</>}</>;
}

export default Home;
