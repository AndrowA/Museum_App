import { createSlice } from '@reduxjs/toolkit';

const userSlice = createSlice({
  name: 'user',
  initialState: {
    uid: undefined,
    type: undefined,
  },
  reducers: {
    setUid: (state, action) => {
      state.uid = action.payload;
    },
    setType: (state, action) => {
      state.type = action.payload;
    },
  },
});

export const { setUid, setType } = userSlice.actions;
export default userSlice;
