import React from "react";

import PropTypes from "prop-types";

import { Container, PopupContainer, Title, CustomIcon } from "./Popup.styles";

const Popup = ({
  title,
  children,
  onCancel,
  // onSubmit,
  // cancelText,
  // submitText,
  // hideCancel,
}) => {
  return (
    <Container>
      <PopupContainer>
        <div>
          <Title>{title}</Title>
          <CustomIcon icon="ci:off-outline-close" color="#bdf841" onClick={onCancel}/>
        </div>
        {children}
      </PopupContainer>
    </Container>
  );
};

Popup.propTypes = {
  title: PropTypes.string,
  // onCancel: PropTypes.func,
  // onSubmit: PropTypes.func,
  // cancelText: PropTypes.string,
  // submitText: PropTypes.string,
  // hideCancel: PropTypes.bool,
};

Popup.defaultProps = {
  // hideCancel: false,
  // cancelText: "Cancel",
  // submitText: "Submit",
};

export default Popup;
