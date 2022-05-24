import React from "react";
import PropTypes from "prop-types";
import styled from "styled-components/macro";

const Container = styled.div`
  background-color: #24acf5;
  min-height: 200px;
  box-sizing: border-box;
  width: 100%;
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
  position: relative;
  border-radius: 10px;
  overflow: hidden;
  padding: 20px 20px 20px 40px;

  & .one, .two {
    position: absolute;
    right: 0;
    top: 0;
    height: 200px;
  }

  & .one {
    width: 50%;
  }
  & .two {
    width: 25%;
  }
`;

const AccentBar = styled.div`
  background-color: ${(props) => props.theme.accent};
  position: absolute;
  left: 0;
  top: 0;
  height: 100%;
  width: 10px;
  margin-right: 15px;
`;

const ContainerHeader = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 30px;
`;

const CategoryName = styled.h1`
  color: white;
  width: 25%;
`;

const twoActionPath = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 432 210"
    preserveAspectRatio="none"
    className="two"
  >
    <path
      stroke="#BDF841"
      strokeLinecap="round"
      strokeOpacity={0.6}
      strokeWidth={3}
      d="M2 2c77 5 172.208 28.308 205.5 119 38.177 104 175 90 222.5 79"
    />
  </svg>
);

const oneActionPath = (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 726 206"
    preserveAspectRatio="none"
    className="one"
  >
    <path
      stroke="#BDF841"
      strokeLinecap="round"
      strokeOpacity={0.6}
      strokeWidth={3}
      d="M1.5 204C36.5 155.333 127.2 61.1 210 73.5 313.5 89 355 169 473 154S673.5 11 724.5 1.5"
    />
  </svg>
);

const ActionsCategory = ({ title, actions, children, otherHeaderContent, onlyHeader }) => {
  const numberOfActions = actions?.length;
  return (
    <Container>
      <AccentBar />
      {numberOfActions === 1
        ? oneActionPath
        : numberOfActions === 2
        ? twoActionPath
        : undefined}

      <ContainerHeader>
        <CategoryName
          style={onlyHeader && {width: "100%"}}
        >{title}</CategoryName>
        {actions}
        {otherHeaderContent}
      </ContainerHeader>
      {children}
    </Container>
  );
};

ActionsCategory.propTypes = {
  title: PropTypes.string.isRequired,
  actions: PropTypes.arrayOf(PropTypes.element),
};

export default ActionsCategory;
