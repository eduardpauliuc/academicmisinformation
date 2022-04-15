import styled from "styled-components/macro";

export const Label = styled.label`
  color: #005382;
`;

export const Input = styled.input``;

export const Select = styled.select``;

export const FormContainer = styled.div`
  flex: 1;
  display: grid;
  grid-template-columns: 1fr 1.5fr;
  margin: 20px 0px;

  row-gap: 15px;
  column-gap: 10px;
  align-items: baseline;
`;

export const LargeFormContainer = styled(FormContainer)`
  grid-template-columns: 1fr 1fr 1fr 1fr;

`