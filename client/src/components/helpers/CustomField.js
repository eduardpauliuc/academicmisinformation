import { Field } from "formik";
import React from "react";

const CustomField = ({ className, valid, error, ...props }) => {
  return <Field className={className} {...props} />;
};

export default CustomField;
