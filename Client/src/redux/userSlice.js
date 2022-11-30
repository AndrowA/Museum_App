import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
  name: 'user',
  initialState: {
    uid: undefined,
    type: undefined,
    email: undefined,
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
    }
  },
});

export const { setUid, setType , setEmail} = userSlice.actions;
export default userSlice;
