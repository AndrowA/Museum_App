import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
  name: 'user',
  initialState: {
    uid: undefined,
    type: undefined,
    email: undefined,
    firstName: undefined,
    lastName: undefined,
  },
  reducers: {
    setUid: (state, action) => {
      state.uid = action.payload;
    },
    setType: (state, action) => {
      state.type = action.payload;
    },
    setEmail: (state, action) => {
      state.email = action.payload;
    },
    setName: (state, action) => {
      state.firstName = action.payload?.firstName;
      state.lastName = action.payload?.lastName;
    },
  },
});

export const { setUid, setType, setEmail, setName } = userSlice.actions;
export default userSlice;
