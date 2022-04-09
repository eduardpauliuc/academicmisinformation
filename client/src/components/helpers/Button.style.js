import styled from "styled-components/macro";

export const StyledButton = styled.button`
  background: ${(props) => (props.primary ? props.theme.main : "white")};
  border-radius: 12px;
  border: ${(props) => (props.primary ? "none" : "solid")};
  border-color: ${(props) => props.theme.main};
  width: 150px;
  height: 45px;
  font-size: 18px;
  font-weight: 700;
  color: ${(props) => (props.primary ? "white" : props.theme.main)};

  &:active,
  &:focus,
  &:hover {
    cursor: pointer;
  }

  &:disabled {
    background-color: #7c8b94;
    box-shadow: none;

    &:hover,
    &:focus {
      cursor: not-allowed;
    }
  }
`;
