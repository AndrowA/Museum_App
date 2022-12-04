/* eslint-disable import/no-unresolved */
import { useState } from 'react';
// @mui
import { Link, Stack, IconButton, InputAdornment, TextField, Checkbox } from '@mui/material';
import { LoadingButton } from '@mui/lab';
// components
import { useApiClient } from 'apiClient/useApiClient';
import { useDispatch } from 'react-redux';
import { sendMessage } from 'redux/alertSlice';
import Iconify from '../../../components/iconify';

// ----------------------------------------------------------------------

export default function LoginForm() {
  const { registerWithEmailAndPassword } = useApiClient();
  const dispatch = useDispatch();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState(undefined);
  const [password1, setPassword1] = useState(undefined);
  const [password2, setPassword2] = useState(undefined);

  const handleClick = async () => {
    if (password1 === password2) {
      registerWithEmailAndPassword(firstName, lastName, email, password1);
    } else {
      dispatch(sendMessage({ open: true, message: 'Passwords do not match', severity: 'error' }));
    }
  };

  return (
    <>
      <Stack spacing={3}>
        <TextField
          name="firstName"
          label="First Name"
          onChange={(e) => {
            setFirstName(e.target.value);
          }}
        />
        <TextField
          name="lastName"
          label="Last Name"
          onChange={(e) => {
            setLastName(e.target.value);
          }}
        />
        <TextField
          name="email"
          label="Email address"
          onChange={(e) => {
            setEmail(e.target.value);
          }}
        />

        <TextField
          name="password"
          label="Password"
          type={showPassword ? 'text' : 'password'}
          onChange={(e) => {
            setPassword1(e.target.value);
          }}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                  <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'} />
                </IconButton>
              </InputAdornment>
            ),
          }}
        />

        <TextField
          name="password"
          label="Confirm Password"
          type="password"
          onChange={(e) => {
            setPassword2(e.target.value);
          }}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton edge="end" />
              </InputAdornment>
            ),
          }}
        />
      </Stack>
      <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }} />

      <LoadingButton fullWidth size="large" type="submit" variant="contained" onClick={handleClick}>
        Sign up
      </LoadingButton>
    </>
  );
}
