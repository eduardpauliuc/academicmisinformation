import { Icon } from "@iconify/react";
import React, { useState } from "react";
import Action from "../common-components/Action";
import ActionsCategory from "../common-components/home/ActionsCategory";
import Disciplines from "./Disciplines";
import Rankings from "./Rankings";

const Teachers = () => {
  const [disciplinesVisible, setDisciplinesVisible] = useState(true);
  const [rankingsVisible, setRankingsVisible] = useState(false);

  const disciplinesClicked = () => {
    setDisciplinesVisible(!disciplinesVisible);
    setRankingsVisible(false);
  };

  const rankingsClicked = () => {
    setRankingsVisible(!disciplinesVisible);
    setDisciplinesVisible(false);
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
    >
      {disciplinesVisible && <Disciplines />}
      {rankingsVisible && <Rankings />}
    </ActionsCategory>
  );
};

export default Teachers;
