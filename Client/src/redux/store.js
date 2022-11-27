import { configureStore } from '@reduxjs/toolkit';
import alertSlice from './alertSlice';
import artifactSlice from './artifactSlice';
import loginSlice from './loginSlice';
import userSlice from './userSlice';

export const store = configureStore({
  reducer: {
    alert: alertSlice.reducer,
    login: loginSlice.reducer,
    user: userSlice.reducer,
    artifact: artifactSlice.reducer,
  },
});

export default store;
