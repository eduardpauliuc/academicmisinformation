import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import ActionsCategory from "../../common-components/home/ActionsCategory";
import Action from "../../common-components/Action";
import { Icon } from "@iconify/react";
import ContractsTable from "./ContractsTable";
import StudentService from "../../../services/student.service";
import { toast } from "react-toastify";

const Contracts = ({ user, setGenerateVisible, setUploadVisible }) => {
  const [contracts, setContracts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const generateClicked = () => {
    // if (contracts.length == 2) {
    //   toast.error("Maximum number of contracts!");
    //   return;
    // }

    setGenerateVisible(true);
  };

  const uploadClicked = () => {
    setUploadVisible(true);
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

  useEffect(() => {
    if (!user) return;

    StudentService.getStudentContracts().then((response) => {
      setContracts(response.data);
      setIsLoading(false);
    });
  }, [user]);

  return (
    <ActionsCategory title="Contracts" actions={[generateAction, uploadAction]}>
      <ContractsTable isLoading={isLoading} contracts={contracts} />
    </ActionsCategory>
  );
};

Contracts.propTypes = {};

export default Contracts;
