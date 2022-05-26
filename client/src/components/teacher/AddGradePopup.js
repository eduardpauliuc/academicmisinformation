import React, { useEffect, useState } from "react";
import Popup from "../helpers/Popup";
import * as Yup from "yup";

import { Form, Formik } from "formik";
import { FormContainer, Label } from "../helpers/PopupForm.styles";
import { ButtonsWrapper } from "../helpers/Popup.styles";
import { StyledButton } from "../helpers/Button.style";
import { MySelect, MyTextInput } from "../helpers/FormComponents";

import { toast } from "react-toastify";
import TeacherService from "../../services/teacher.service";

const AddGradePopup = ({ closePopup, courses }) => {
  const [students, setStudents] = useState([]);

  const studentOptions = students.map((student) => {
    return {
      value: student.studentId,
      label: student.studentName,
    };
  });
  console.log(courses);
  const courseOptions = courses
    .filter((c) => c.status !== "PENDING" && c.status !== "REJECTED")
    .map((course) => {
      return {
        value: course.id,
        label: course.name,
      };
    });

  const initialValues = {
    courseID: undefined,
    studentID: undefined,
    grade: "",
  };

  const validationSchema = Yup.object().shape({
    courseID: Yup.number().required(),
    studentID: Yup.number().required(),
    grade: Yup.number().integer().min(1).max(10).required(),
  });

  const handleFormSubmitted = (formValue, actions) => {
    let promise = TeacherService.addGrade(
      formValue.grade,
      formValue.courseID,
      formValue.studentID
    );

    promise
      .catch((err) => console.log(err))
      .then(() => actions.setSubmitting(false));

    toast.promise(promise, {
      pending: "Assigning",
      success: "Added grade",
      error: "Error assigning grade",
    });
  };

  const courseChanged = (courseID) => {
    console.log(courseID);
    TeacherService.getStudents(courseID).then((data) => {
      console.log("Got students: ", data.data);
      setStudents(data.data);
    });
  };

  const studentChanged = (studentId, setFieldValue) => {
    console.log(studentId);
    var student = students.filter((s) => s.studentId === studentId)[0];
    let currentGrade = student.grade ?? "";
    console.log("settting previous grade: ", currentGrade);
    setFieldValue("grade", currentGrade);
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
              <MySelect
                name="studentID"
                options={studentOptions}
                valueChanged={(studentId) =>
                  studentChanged(studentId, setFieldValue)
                }
              />

              <Label htmlFor="grade">Grade:</Label>
              <MyTextInput type="number" name="grade" min="1" max="10" />
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
