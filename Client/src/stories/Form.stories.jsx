/* eslint-disable import/no-unresolved */
import React from 'react';
import { Provider } from 'react-redux';
import store from 'redux/store';
import Form from 'components/general-form/Form';

export default {
  title: 'Pages/General Form',
  component: Form,
  decorators: [
    (Story) => (
      <Provider store={store}>
        <Story />
      </Provider>
    ),
  ],
};

export const Primary = () => <Form />;
