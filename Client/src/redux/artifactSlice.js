import { createSlice } from '@reduxjs/toolkit';

const artifactSlice = createSlice({
  name: 'artifacts',
  initialState: {
    artifactList: [{}],
  },
  reducers: {
    setArtifactList: (state, action) => {
      state.artifactList = action.payload;
    },
  },
});

export const { setArtifactList } = artifactSlice.actions;
export default artifactSlice;
