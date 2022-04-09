import styled from "styled-components/macro";
import { css } from "styled-components";
import CustomField from "../helpers/CustomField";

export const MainContainer = styled.div`
  height: 80%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 3em;
`;

export const LoginContainer = styled.div`
  background-color: rgba(36, 138, 196, 0.91);
  border-radius: 14px;
  padding: 0px;
  padding-bottom: 10px;

  & * {
    box-sizing: border-box;
    display: block;
  }

  & button {
    margin: auto;
    margin-top: 30px;
  }
`;

export const AccentBar = styled.div`
  background-color: ${(props) => props.theme.accent};
  height: 14px;
  width: 100%;
  border-radius: 14px 14px 0px 0px;
`;

export const Hint = styled.p`
    color: white;
    font-size: 14px;
    font-weight: 400;
    margin-bottom: 0.25em;
`;

export const StyledCustomField = styled(CustomField)`
  background-color: white;
  border: 1px solid lightgrey;
  border-radius: 7px;
  font-size: 1rem;
  line-height: 1em;
  font-style: normal;
  font-weight: 400;
  width: 20em;
  margin-top: 0.25em;
  padding: 0.75rem 0.75rem;

  &:focus,
  &:active {
    box-shadow: rgb(210, 213, 217) 0px 0px 2px 1px,
      rgb(227, 230, 232) 0px 0px 0px 3px;
    border: 1px solid rgb(26, 33, 43);
    outline: none;
  }

  /* Autocomplete styles in Chrome*/
  &:-webkit-autofill,
  &:-webkit-autofill:hover,
  &:-webkit-autofill:focus {
    background-color: white;
    border: 1px solid lightgrey;
    box-shadow: 0 0 0px 1000px #fff inset;
    -webkit-box-shadow: 0 0 0px 1000px #fff inset;
    transition: background-color 5000s ease-in-out 0s;
    -webkit-text-fill-color: black;
  }

  ${({ valid }) =>
    valid &&
    css`
      border: 1px solid rgb(0, 156, 38);

      &:focus,
      &:active {
        border: 1px solid rgb(0, 156, 38);
        box-shadow: rgb(106, 237, 97) 0px 0px 2px 1px,
          rgb(177, 247, 160) 0px 0px 0px 3px;
        outline: none;
      }

      /* Autocomplete styles in Chrome*/
      &:-webkit-autofill,
      &:-webkit-autofill:hover,
      &:-webkit-autofill:focus {
        border: 1px solid rgb(0, 156, 38);
      }
    `}

  ${({ error }) =>
    error &&
    css`
      border: 1px solid rgb(191, 49, 12);
      outline: none;

      &:focus,
      &:active {
        box-shadow: rgb(244, 129, 116) 0px 0px 2px 1px,
          rgb(251, 178, 174) 0px 0px 0px 3px;
        border: 1px solid rgb(191, 49, 12);
        outline: none;
      }

      /* Autocomplete styles in Chrome*/
      &:-webkit-autofill,
      &:-webkit-autofill:hover,
      &:-webkit-autofill:focus {
        border: 1px solid rgb(191, 49, 12);
      }
    `}
`;

export const StyledInlineErrorMessage = styled.div`
  color: #f2d3d3;
  display: block;

  padding: 0.2rem 0.75rem;
  margin-top: 0rem;
  white-space: pre-line;
`;