import { Form, Formik } from "formik";
import React from "react";
import { useDispatch } from "react-redux";
import Popup from "./helpers/Popup";
import * as Yup from "yup";

import { StyledButton } from "./helpers/Button.style";
import { ButtonsWrapper } from "./helpers/Popup.styles";
import { FormContainer, Label } from "./helpers/PopupForm.styles";
import { roleOptions } from "../helpers/role";
import { MySelect, MyTextInput } from "./helpers/FormComponents";
import { updateProfile } from "../slices/authSlice";
import ProfileService from "../services/profile.service";

import { toast } from "react-toastify";

const ConfigureProfilePopup = (props) => {
  const { closePopup, user } = props;
  const dispatch = useDispatch();

  const handleFormSubmitted = (formValue, actions) => {
    console.log(formValue);

    ProfileService.updateProfile(formValue)
      .then((response) => {
        dispatch(updateProfile(response.data));
        toast.success("Profile updated");
        closePopup();
      })
      .catch((e) => {
        actions.setSubmitting(false);
        toast.error(e.message);
      });
  };

  const handleClose = () => {
    closePopup();
  };

  const initialValues = {
    role: user.role,
    firstname: user.firstName,
    lastname: user.lastName,
    birthdate: user.birthDate,
    newpassword: "",
    confirmpassword: "",
    email: user.email,
    username: user.username,
  };

  const validationSchema = Yup.object().shape({
    newpassword: Yup.string().min(8, "At least 8 chars"),
    confirmpassword: Yup.string().test(
      "passwords-match",
      "Passwords must match",
      function (value) {
        return this.parent.newpassword === value;
      }
    ),
    birthdate: Yup.date().typeError("Format must be YYYY-MM-DD"),
  });

  return (
    <Popup title="Configure profile" onCancel={closePopup}>
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
              <Label>Username:</Label>
              <MyTextInput name="username" disabled />

              <Label>Email:</Label>
              <MyTextInput name="email" disabled />

              <Label>Role:</Label>
              <MySelect name="role" isDisabled={true} options={roleOptions} />

              <Label>New password:</Label>
              <MyTextInput name="newpassword" type="password" />

              <Label>Confirm password:</Label>
              <MyTextInput name="confirmpassword" type="password" />

              <Label>First Name:</Label>
              <MyTextInput name="firstname" />

              <Label>Last Name:</Label>
              <MyTextInput name="lastname" />

              <Label>Birth Date:</Label>
              <MyTextInput name="birthdate" />
            </FormContainer>
            <ButtonsWrapper>
              <StyledButton type="button" onClick={handleClose}>
                Cancel
              </StyledButton>
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

export default ConfigureProfilePopup;
