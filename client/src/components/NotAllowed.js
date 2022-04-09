import React from "react";
import { useNavigate } from "react-router-dom";

const NotAllowed = () => {
  const navigate = useNavigate();

  return (
    <div>
      Not Allowed!
      <button onClick={() => navigate("/")}>Home</button>
    </div>
  );
};

export default NotAllowed;
