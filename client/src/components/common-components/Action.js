import React from "react";
import PropTypes from "prop-types";
import styled from "styled-components/macro";

const TitleIconContainer = styled.div`
  display: flex;
  align-items: baseline;
  gap: 10px;
`;

const ActionTitle = styled.h2`
  color: #005382;
  margin: 0px;
`;

const ActionSubtitle = styled.h4`
  color: white;
  margin: 0px;
`;

const Action = ({ name, subname, icon, clickHandler }) => {
  return (
    <div onClick={clickHandler} style={{ width: "25%" }}>
      <TitleIconContainer>
        <ActionTitle>{name}</ActionTitle>
        {icon}
      </TitleIconContainer>
      <ActionSubtitle>{subname}</ActionSubtitle>
    </div>
  );
};

Action.propTypes = {
  name: PropTypes.string.isRequired,
  subname: PropTypes.string,
  icon: PropTypes.element,
  clickHandler: PropTypes.func,
};

export default Action;
