import React, { useEffect, useState } from "react";
import Popup from "../helpers/Popup";
import * as Yup from "yup";

import { Form, Formik } from "formik";
import { FormContainer, Label } from "../helpers/PopupForm.styles";
import { ButtonsWrapper } from "../helpers/Popup.styles";
import { StyledButton } from "../helpers/Button.style";
import { MySelect, MyTextInput } from "../helpers/FormComponents";

import { toast } from "react-toastify";

const AddGradePopup = ({ closePopup, courses }) => {
  const [students, setStudents] = useState([]);

  const studentOptions = students.map((student) => {
    return {
      value: student.id,
      label: student.name,
    };
  });

  const courseOptions = courses.map((course) => {
    return {
      value: course.id,
      label: course.name,
    };
  });

  useEffect(() => {
    // TODO call service
    setStudents([
      {
        id: 1,
        name: "Vasile Ion",
      },
      {
        id: 2,
        name: "George Satmarean",
      },
      {
        id: 3,
        name: "Mihai Popovici",
      },
    ]);
  }, []);

  const initialValues = {
    courseID: undefined,
    studentID: undefined,
    grade: 10,
  };

  const validationSchema = Yup.object().shape({
    courseID: Yup.number().required(),
    studentID: Yup.number().required(),
    grade: Yup.number().integer().min(1).max(10).required(),
  });

  const handleFormSubmitted = (formValue, actions) => {
    console.log(formValue);
    actions.setSubmitting(false);
  };

  const courseChanged = (courseID) => {
    // TODO
  };

  return (
    <Popup title="Add grade" onCancel={closePopup}>
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
              <Label htmlFor="courseID">Course:</Label>
              <MySelect
                name="courseID"
                options={courseOptions}
                valueChanged={courseChanged}
                fieldToReset="studentID"
                setFieldValue={setFieldValue}
              />
              <Label htmlFor="studentID">Student:</Label>
              <MySelect name="studentID" options={studentOptions} />

              <Label htmlFor="grade">Grade:</Label>
              <MyTextInput name="grade" />
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

AddGradePopup.prototype = {};

export default AddGradePopup;
