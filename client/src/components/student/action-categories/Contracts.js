import React from "react";
import PropTypes from "prop-types";
import ActionsCategory from "../../common-components/home/ActionsCategory";
import Action from "../../common-components/Action";
import { Icon } from "@iconify/react";

const Contracts = (props) => {
  const generateClicked = () => {
    console.log("Generate clicked");
  };

  const uploadClicked = () => {
    console.log("Upload clicked!");
  };

  const generateAction = (
    <Action
      name="Generate"
      subname="contract to sign"
      icon={<Icon icon="carbon:generate-pdf" color="#bdf841" />}
      clickHandler={generateClicked}
      key={1}
    />
  );

  const uploadAction = (
    <Action
      name="Upload"
      subname="signed contract"
      icon={<Icon icon="carbon:upload" color="#bdf841" />}
      clickHandler={uploadClicked}
      key={2}
    />
  );

  return (
    <ActionsCategory
      title="Contracts"
      actions={[generateAction, uploadAction]}
    ></ActionsCategory>
  );
};

Contracts.propTypes = {};

export default Contracts;
