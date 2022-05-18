import { Icon } from "@iconify/react";
import React from "react";
import Action from "../common-components/Action";
import ActionsCategory from "../common-components/home/ActionsCategory";

const Teachers = () => {
  const disciplinesClicked = () => {
    console.log("Show disciplines");
  };

  const disciplinesAction = (
    <Action
      name="Disciplines"
      subname="for a teacher"
      icon={<Icon icon="ant-design:unordered-list-outlined" color="#bdf841" />}
      clickHandler={disciplinesClicked}
      key={1}
    />
  );

  const rankingsClicked = () => {
    console.log("Show rankings");
  };

  const rankingsAction = (
    <Action
      name="Rankings"
      subname="of all teachers"
      icon={<Icon icon="ion:podium-outline" color="#bdf841" />}
      clickHandler={rankingsClicked}
      key={2}
    />
  );
  return (
    <ActionsCategory
      title="Teachers"
      actions={[disciplinesAction, rankingsAction]}
    ></ActionsCategory>
  );
};

export default Teachers;
