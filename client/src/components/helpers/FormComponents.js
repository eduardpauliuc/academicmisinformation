import { useField } from "formik";
import styled from "styled-components/macro";
import Select from "react-select";

const StyledErrorMessage = styled.div`
  font-size: 12px;
  color: darkred;
  margin-top: 0.25rem;
  /* &:before {
    content: "❌ ";
    font-size: 10px;
  } */
`;

const Input = styled.input`
  width: 100%;
  box-sizing: border-box;
  border: solid lightgray 1px;
  border-radius: 3px;
  height: 2rem;
  font-size: medium;
  padding: 0.5rem;
`;

const StyledSelect = styled(Select)`
  height: 2rem;
  box-sizing: border-box;

  font-size: medium;
`;

export const MyTextInput = ({ label, ...props }) => {
  const [field, meta] = useField(props);
  return (
    <div>
      <Input {...field} {...props} />
      {meta.touched && meta.error ? (
        <StyledErrorMessage>{meta.error}</StyledErrorMessage>
      ) : null}
    </div>
  );
};

export const MyCheckbox = ({ children, ...props }) => {
  const [field, meta] = useField({ ...props, type: "checkbox" });
  return (
    <div>
      <label className="checkbox">
        <input {...field} {...props} type="checkbox" />
        {children}
      </label>
      {meta.touched && meta.error ? (
        <div className="error">{meta.error}</div>
      ) : null}
    </div>
  );
};

export const MySelect = ({ ...props }) => {
  const [field, meta, helpers] = useField(props);

  const {
    options,
    isDisabled,
    isLoading,
    isClearable,
    isSearchable,
    valueChanged,
  } = props;
  const { setValue } = helpers;

  const onChange = (option) => {
    setValue(option.value);
    if (valueChanged) valueChanged(option.value);
    if (props.fieldToReset) props.setFieldValue(props.fieldToReset, null);
  };

  const getValue = () => {
    if (options && field.value) {
      return options.find((option) => option.value === field.value);
    } else {
      return "";
    }
  };

  return (
    <div>
      <StyledSelect
        options={options}
        name={field.name}
        onChange={onChange}
        value={getValue()}
        isDisabled={isDisabled}
        isLoading={isLoading}
        isClearable={isClearable}
        isSearchable={isSearchable}
      />

      {meta.touched && meta.error ? (
        <StyledErrorMessage>{meta.error}</StyledErrorMessage>
      ) : null}
    </div>
  );
};

export const FormLabel = styled.div`
  color: darkblue;
`;

export const StyledFileInput = styled.input`
  margin: 10px 0px 10px 0px;
`;

export const MyFileUpload = (props) => {
  const [field, meta] = useField(props);

  const { setFieldValue } = props;

  return (
    <div>
      <StyledFileInput
        name={field.name}
        type="file"
        onChange={(event) => {
          const file = event.target.files[0];
          setFieldValue(field.name, file);
        }}
      />
      {meta.error ? (
        <StyledErrorMessage>{meta.error}</StyledErrorMessage>
      ) : null}
    </div>
  );
};
