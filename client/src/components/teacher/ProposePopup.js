import { Form, Formik } from "formik";
import React from "react";
import Popup from "../helpers/Popup";
import * as Yup from "yup";
import { FormContainer, Label } from "../helpers/PopupForm.styles";
import { ButtonsWrapper } from "../helpers/Popup.styles";
import { StyledButton } from "../helpers/Button.style";
import { MyTextInput } from "../helpers/FormComponents";

const ProposePopup = ({ close }) => {
  const initialValues = {};
  const validationSchema = Yup.object().shape({
    username: Yup.string().required(),
    password: Yup.string().required(),
    email: Yup.string().email().required(),
  });

  const handleFormSubmitted = (formValue, action) => {

  }

  return (
    <Popup title="Propose optional" onCancel={close}>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleFormSubmitted}
      >
        {/* {({ errors, touched, handleSubmit, isSubmitting, isValid }) => (
          <Form
            style={{
              flexGrow: "1",
              display: "flex",
              flexDirection: "column",
            }}
          >
            <FormContainer>
              teacherId - current user
                specializationId
                name
                credits
                description
                semesterNumber
                maximumStudentsNumber

              <Label htmlFor="username">Username:</Label>
              <MyTextInput name="username" />

              <Label htmlFor="email">Email:</Label>
              <MyTextInput name="email" />

              <Label htmlFor="role">Role:</Label>
              <MySelect name="role" options={roleOptions} />

              <Label htmlFor="password">Password:</Label>
              <MyTextInput name="password" type="password" />
            </FormContainer>
            <ButtonsWrapper>
              <StyledButton type="submit" primary disabled={isSubmitting}>
                Submit
              </StyledButton>
            </ButtonsWrapper>
          </Form>
        )} */}
      </Formik>
    </Popup>
  );
};

export default ProposePopup;
