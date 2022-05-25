import { Icon } from "@iconify/react";
import React from "react";
import { toast } from "react-toastify";
import StaffService from "../../services/staff.service";
import Action from "../common-components/Action";
import ActionsCategory from "../common-components/home/ActionsCategory";

const Actions = ({ specialization, semester }) => {
  const optionalsClicked = () => {
    let promise = StaffService.assignToOpionals();

    promise
      .then(() => {})
      .catch((e) => {
        toast.error(e.message);
      });

    toast.promise(promise, {
      pending: "Assigning",
      success: "Assigned",
      error: "Error assigning",
    });
  };

  const groupsClicked = () => {
    let promise = StaffService.assignToGroups();

    promise
      .then(() => {})
      .catch((e) => {
        toast.error(e.message);
      });

    toast.promise(promise, {
      pending: "Assigning",
      success: "Assigned",
      error: "Error assigning",
    });
  };

  const optionalsAction = (
    <Action
      name="Optional"
      subname="assign students"
      icon={<Icon icon="ion:podium-outline" color="#bdf841" />}
      clickHandler={optionalsClicked}
      key={1}
    />
  );

  const groupsAction = (
    <Action
      name="Groups"
      icon={<Icon icon="ant-design:unordered-list-outlined" color="#bdf841" />}
      subname="distribute students"
      clickHandler={groupsClicked}
      key={2}
    />
  );
  return (
    <ActionsCategory
      title="Actions"
      actions={[optionalsAction, groupsAction]}
    ></ActionsCategory>
  );
};

export default Actions;
