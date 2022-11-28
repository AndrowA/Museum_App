/* eslint-disable import/no-unresolved */
import React from 'react';
import { Provider, useDispatch } from 'react-redux';
import store from 'redux/store';
import { HelmetProvider } from 'react-helmet-async';
import { setUid } from 'redux/userSlice';
import EmployeeSchedulePage from '../pages/EmployeeSchedulePage';
import ThemeProvider from '../theme';

export default {
  title: 'Pages/Employee Schedule Page',
  component: EmployeeSchedulePage,
  decorators: [
    (Story) => (
      <HelmetProvider>
        <Provider store={store}>
          <ThemeProvider>
            <Story />
          </ThemeProvider>
        </Provider>
      </HelmetProvider>
    ),
  ],
};

export const Primary = () => {
  const dispatch = useDispatch();
  dispatch(setUid(3889));
  return <EmployeeSchedulePage />;
};
