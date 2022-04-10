import React from "react";
import Role, { roleOptions } from "../../helpers/role";
import Popup from "../helpers/Popup";
import * as Yup from "yup";

import AdminService from "../../services/admin.service";
import { Form, Formik } from "formik";
import { FormContainer, Label } from "../helpers/PopupForm.styles";
import { ButtonsWrapper } from "../helpers/Popup.styles";
import { StyledButton } from "../helpers/Button.style";
import { MySelect, MyTextInput } from "../helpers/FormComponents";

import { toast } from "react-toastify";

const CreateAccountPopup = ({ closePopup }) => {
  const initialValues = {
    username: "",
    password: "",
    email: "",
    role: Role.Student,
  };

  const validationSchema = Yup.object().shape({
    username: Yup.string().required(),
    password: Yup.string().required(),
    email: Yup.string().email().required(),
    role: Yup.mixed().oneOf([...Object.values(Role)]),
  });

  const handleFormSubmitted = (formValue, actions) => {
    AdminService.createUser(
      formValue.username,
      formValue.password,
      formValue.email,
      formValue.role
    )
      .then((response) => {
        closePopup();
        toast.success("User created!");
      })
      .catch((e) => {
        toast.error(e.message);
        actions.setSubmitting(false);
      });
  };

  return (
    <Popup title="Create account" onCancel={closePopup}>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleFormSubmitted}
      >
        {({ errors, touched, handleSubmit, isSubmitting, isValid }) => (
          <Form
            style={{
              flexGrow: "1",
              display: "flex",
              flexDirection: "column",
            }}
          >
            <FormContainer>
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
        )}
      </Formik>
    </Popup>
  );
};

CreateAccountPopup.prototype = {};

export default CreateAccountPopup;
