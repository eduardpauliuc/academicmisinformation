import { Icon } from "@iconify/react";
import { css } from "styled-components";
import styled from "styled-components/macro";

export const Container = styled.div`
  height: 100%;
  width: 100%;
  position: absolute;
  background: rgba(0, 0, 0, 0.2);
  z-index: 10;
  top: 0;
`;

export const PopupContainer = styled.div`
  min-width: 500px;
  min-height: 200px;
  background-color: white;
  border-radius: 34px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px 60px;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;

  ${({ large }) =>
    large &&
    css`
      min-width: 700px;
    `}
`;

export const Title = styled.div`
  font-size: large;
  display: inline;
`;

export const CustomIcon = styled(Icon)`
  display: inline;
  float: right;
  font-size: 1.6em;
`;

export const ButtonsWrapper = styled.div`
  display: flex;
  justify-content: center;

  gap: 20px;
`;
