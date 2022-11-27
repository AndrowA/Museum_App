/* eslint-disable import/no-unresolved */
import * as React from 'react';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { useDispatch, useSelector } from 'react-redux';
import { removeMessage } from 'redux/alertSlice';

export function CustomAlert() {
  const dispatch = useDispatch();
  const snackbar = useSelector((state) => state.alert);

  const handleClose = () => {
    dispatch(removeMessage());
  };

  return (
    <Snackbar
      open={snackbar.open}
      autoHideDuration={6000}
      onClose={handleClose}
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
    >
      <MuiAlert
        onClose={handleClose}
        severity={snackbar.severity}
        sx={{ width: '100%' }}
        variant="filled"
        elevation={6}
      >
        {snackbar.message}
      </MuiAlert>
    </Snackbar>
  );
}

export default CustomAlert;
