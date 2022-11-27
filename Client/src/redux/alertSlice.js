import { createSlice } from '@reduxjs/toolkit';

const alertSlice = createSlice({
  name: 'alert',
  initialState: {
    open: false,
    message: '',
    severity: undefined,
  },
  reducers: {
    sendMessage: (state, action) => {
      state.open = action.payload.open;
      state.message = action.payload.message;
      state.severity = action.payload.severity;
    },
    removeMessage: (state) => {
      state.open = false;
    },
  },
});

export const { sendMessage, removeMessage } = alertSlice.actions;
export default alertSlice;
