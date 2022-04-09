import React, { useEffect } from "react";

import { Formik, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import { useDispatch, useSelector } from "react-redux";

import { login } from "../../slices/authSlice";
import { clearMessage } from "../../slices/message";

import { Navigate, useNavigate } from "react-router-dom";
import { useTheme } from "styled-components";

import * as loginStyles from "./Login.styles";
import { StyledButton } from "../helpers/Button.style";

function Login() {
  const { isLoggedIn } = useSelector((state) => state.auth);
  const { message } = useSelector((state) => state.message);
  const theme = useTheme();

  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(clearMessage());
  }, [dispatch]);

  const initialValues = {
    username: "",
    password: "",
  };

  const validationSchema = Yup.object().shape({
    username: Yup.string().required("Empty username!"),
    password: Yup.string().required("Empty password!"),
  });

  const handleLogin = (formValue, actions) => {
    const { username, password } = formValue;

    dispatch(login({ username, password }))
      .unwrap()
      .then(() => {
        navigate("/", { replace: true });
      })
      .catch((e) => {
        actions.setSubmitting(false);
      });
  };

  if (isLoggedIn) return <Navigate to="/" replace />;

  return (
    <loginStyles.MainContainer>
      <h1 style={{ color: "white" }}>
        Welcome to Academic
        <span style={{ color: theme.accent }}>Misinformation</span>!
      </h1>
      <loginStyles.LoginContainer>
        <loginStyles.AccentBar />
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleLogin}
        >
          {({ errors, touched, handleSubmit, isSubmitting, isValid }) => (
            <Form style={{ padding: "10px 50px 10px 50px" }}>
              <h2 style={{ color: "white" }}>Log into your account</h2>
              <loginStyles.StyledInlineErrorMessage>
                {message}
              </loginStyles.StyledInlineErrorMessage>
              <div>
                <loginStyles.Hint htmlFor="username">Username</loginStyles.Hint>
                <loginStyles.StyledCustomField
                  name="username"
                  type="text"
                  error={touched.username && errors.username}
                />
                <ErrorMessage name="username">
                  {(msg) => (
                    <loginStyles.StyledInlineErrorMessage>
                      {msg}
                    </loginStyles.StyledInlineErrorMessage>
                  )}
                </ErrorMessage>
              </div>
              <div>
                <loginStyles.Hint htmlFor="password">Password</loginStyles.Hint>
                <loginStyles.StyledCustomField
                  name="password"
                  type="password"
                  error={touched.password && errors.password}
                />
                <ErrorMessage name="password">
                  {(msg) => (
                    <loginStyles.StyledInlineErrorMessage>
                      {msg}
                    </loginStyles.StyledInlineErrorMessage>
                  )}
                </ErrorMessage>
              </div>
              <StyledButton
                type="submit"
                disabled={!isValid || isSubmitting}
                primary
              >
                Log in
              </StyledButton>
            </Form>
          )}
        </Formik>
      </loginStyles.LoginContainer>
    </loginStyles.MainContainer>
  );
}

export default Login;
