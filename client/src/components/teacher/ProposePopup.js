import { Form, Formik } from "formik";
import React, { useEffect, useState } from "react";
import Popup from "../helpers/Popup";
import * as Yup from "yup";
import { FormContainer, Label } from "../helpers/PopupForm.styles";
import { ButtonsWrapper } from "../helpers/Popup.styles";
import { StyledButton } from "../helpers/Button.style";
import { MySelect, MyTextArea, MyTextInput } from "../helpers/FormComponents";
import { getFaculties } from "../../services/faculties.service";
import { toast } from "react-toastify";
import TeacherService from "../../services/teacher.service";

const ProposePopup = ({ closePopup }) => {
  const [faculties, setFaculties] = useState([]);
  const [specializations, setSpecializations] = useState([]);

  useEffect(() => {
    getFaculties()
      .then((response) => {
        setFaculties(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        toast.error(error.message);
      });
  }, []);

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

  const initialValues = {
    facultyID: "",
    specializationID: "",
    name: "",
    credits: 6,
    description: "no description",
    semesterNumber: 1,
    maximumStudentsNumber: 100,
  };
  const validationSchema = Yup.object().shape({
    facultyID: Yup.number().required(),
    specializationID: Yup.number().required(),
    name: Yup.string().required(),
    credits: Yup.number().positive().required().max(6),
    description: Yup.string(),
    semesterNumber: Yup.number().positive().required(),
    maximumStudentsNumber: Yup.number(),
  });

  const facultyChanged = (facultyID) => {
    setSpecializations(
      faculties.find((faculty) => faculty.facultyId === facultyID)
        .specializations
    );
  };

  const handleFormSubmitted = (formValue, actions) => {
    console.log(formValue);
    actions.setSubmitting(false);

    TeacherService.addOptional(
      formValue.specializationID,
      formValue.name,
      formValue.credits,
      formValue.description,
      formValue.semesterNumber,
      formValue.maximumStudentsNumber
    )
      .then(() => toast.success("Added optional"))
      .catch((error) => {
        toast.error(error.message);
      })
      .finally(() => closePopup());
  };

  return (
    <Popup title="Propose optional" onCancel={closePopup}>
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
            <FormContainer>
              <Label htmlFor="facultyID">Faculty:</Label>
              <MySelect
                name="facultyID"
                options={facultyOptions}
                valueChanged={facultyChanged}
                fieldToReset="specializationID"
                setFieldValue={setFieldValue}
              />
              <Label htmlFor="specializationID">Specialization:</Label>
              <MySelect
                name="specializationID"
                options={specializationOptions}
              />
              <Label htmlFor="name">Name:</Label>
              <MyTextInput name="name" />
              <Label htmlFor="credits">Credits:</Label>
              <MyTextInput name="credits" />
              <Label
                htmlFor="description"
                style={{ alignSelf: "start", marginTop: 5 }}
              >
                Description:
              </Label>
              <MyTextArea name="description" />
              <Label htmlFor="semesterNumber">Semester:</Label>
              <MyTextInput name="semesterNumber" />
              <Label htmlFor="maximumStudentsNumber">Maximum Students:</Label>
              <MyTextInput name="maximumStudentsNumber" />
            </FormContainer>
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

export default ProposePopup;
