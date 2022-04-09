import React from "react";
import { useSelector } from "react-redux";
import Navbar from "../Navbar";


import * as HomeStyles from "../common-components/home/HomePage.styles";

const ChiefHome = () => {
  const { user } = useSelector((state) => state.auth);

  return (
    <>
        <Navbar user={user}/>
        <HomeStyles.HomeContainer>
            Chief Home
        </HomeStyles.HomeContainer>
    </>
  
    )
};

export default ChiefHome;
