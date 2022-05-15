import { Icon } from "@iconify/react";
import React from "react";
import Action from "../common-components/Action";
import ActionsCategory from "../common-components/home/ActionsCategory";

const Manage = () => {
  const reviewClicked = () => {
    console.log("Review");
  };

  const reviewAction = (
    <Action
      name="Reivew"
      subname="list of optional courses"
      icon={<Icon icon="icon-park-outline:preview-open" color="#bdf841" />}
      clickHandler={reviewClicked}
      key={1}
    />
  );
  const endSubmissionClicked = () => {
    console.log("Submission clicked");
  };

  const endSubmissionAction = (
    <Action
      name="End Submissions"
      subname="finish proposal period"
      icon={<Icon icon="bx:stop-circle" color="#bdf841" />}
      clickHandler={endSubmissionClicked}
      key={2}
    />
  );

  return (
    <ActionsCategory
      title="Manage"
      actions={[reviewAction, endSubmissionAction]}
    ></ActionsCategory>
  );
};

export default Manage;
