import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import Popup from "../../helpers/Popup";
import { getFaculties } from "../../../services/faculties.service";
import { toast } from "react-toastify";

import * as Yup from "yup";
import StudentService from "../../../services/student.service";
import { Form, Formik } from "formik";
import { Label, LargeFormContainer } from "../../helpers/PopupForm.styles";
import { MyFileUpload, MySelect } from "../../helpers/FormComponents";
import { ButtonsWrapper } from "../../helpers/Popup.styles";
import { StyledButton } from "../../helpers/Button.style";

const UploadPopup = ({ closePopup, user }) => {
  const [faculties, setFaculties] = useState([]);
  const [specializations, setSpecializations] = useState([]);
  const [selectedSpecID, setSelectedSpecID] = useState(undefined);

  useEffect(() => {
    getFaculties()
      .then((response) => {
        setFaculties(response.data);
      })
      .catch((error) => {
        toast.error(error.message);
      });
  }, []);

  const initialValues = {
    facultyID: "",
    specializationID: "",
    year: "",
    semester: "",
    contract: undefined,
  };

  const validationSchema = Yup.object().shape({
    facultyID: Yup.number().required(),
    specializationID: Yup.number().required(),
    year: Yup.number().required(),
    semester: Yup.number().required(),
    contract: Yup.mixed().required("Contract is required"),
  });

  const handleFormSubmitted = (formValue, actions) => {
    console.log(formValue);
    const semester = (formValue.year - 1) * 2 + formValue.semester;

    StudentService.uploadContract(formValue.specializationID, semester, formValue.contract)
      .then((response) => {
        console.log(response);
        closePopup();
      })
      .catch((error) => {
        toast.error(error.message);
      })
      .finally(() => actions.setSubmitting(false));
  };

  const facultyOptions = faculties.map((faculty) => {
    return {
      value: faculty.facultyId,
      label: faculty.name,
    };
  });

  const specializationOptions = specializations.map((spec) => {
    return {
      value: spec.id,
      label: `${spec.name} - ${spec.degreeType}`,
    };
  });

  const getYearOptions = () => {
    if (!selectedSpecID) return [];

    let spec = specializations.find(
      (spec) => spec.id === selectedSpecID
    );

    if (!spec) return [];

    let years = Array.from(
      Array(spec.numberOfSemesters / 2),
      (_, index) => index + 1
    );

    return years.map((number) => {
      return {
        value: number,
        label: number,
      };
    });
  };

  const facultyChanged = (facultyID) => {
    console.log(facultyID);
    setSpecializations(
      faculties.find((faculty) => faculty.facultyId === facultyID).specializations
    );
  };

  return (
    <Popup onCancel={closePopup} title="Upload contract" large>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleFormSubmitted}
      >
        {({
          errors,
          touched,
          handleSubmit,
          isSubmitting,
          isValid,
          setFieldValue,
        }) => (
          <Form
            style={{
              flexGrow: "1",
              display: "flex",
              flexDirection: "column",
            }}
          >
            <LargeFormContainer>
              <Label htmlFor="facultyID">Faculty:</Label>
              <div style={{ gridColumn: "span 3" }}>
                <MySelect
                  name="facultyID"
                  options={facultyOptions}
                  valueChanged={facultyChanged}
                  fieldToReset="specializationID"
                  setFieldValue={setFieldValue}
                />
              </div>
              <Label htmlFor="specializationID">Specialization:</Label>
              <div style={{ gridColumn: "span 3" }}>
                <MySelect
                  name="specializationID"
                  options={specializationOptions}
                  setFieldValue={setFieldValue}
                  valueChanged={(specID) => setSelectedSpecID(specID)}
                  fieldToReset="year"
                />
              </div>
              <Label htmlFor="year">Year:</Label>
              <MySelect name="year" options={getYearOptions()} />
              <Label htmlFor="semester">Semester:</Label>
              <MySelect
                name="semester"
                options={[
                  { label: 1, value: 1 },
                  { label: 2, value: 2 },
                ]}
              />

              <Label htmlFor="contract">Signed contract:</Label>
              <div style={{ gridColumn: "span 3" }}>
                <MyFileUpload name="contract" setFieldValue={setFieldValue} />
              </div>
            </LargeFormContainer>
            <ButtonsWrapper>
              <StyledButton type="submit" primary disabled={isSubmitting}>
                Submit
              </StyledButton>
            </ButtonsWrapper>
          </Form>
        )}
      </Formik>
    </Popup>
  );
};

UploadPopup.propTypes = {
  closePopup: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
};

export default UploadPopup;
