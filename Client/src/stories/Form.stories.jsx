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

export const LoanRequestForm = () => (
  <Form fields={[]} datePickers={[{ title: 'loan start date' }]} buttons={[{ title: 'Request' }]} />
);

export const AddArtifactForm = () => {
  const onChangeTitle = (e) => {
    // value comes from e.target.value
  };

  const onButtonClick = (e) => {
    alert('button clicked');
  };

  return (
    <Form
      fields={[
        { title: 'Artifact Title', onChange: onChangeTitle },
        { title: 'Artifact URL' },
        { title: 'Artifact Description', desc: true },
      ]}
      buttons={[{ title: 'Add Artifact', onClick: onButtonClick }]}
    />
  );
};

export const AddEmployeeForm = () => {
  const onChangeEmail = (e) => {
    // value comes from e.target.value
  };

  const onButtonClick = (e) => {
    alert('button clicked');
  };

  return (
    <Form
      fields={[
        { title: 'Email', onChange: onChangeEmail },
        { title: 'Password' },
        { title: 'Base Hourly Salary ($)', number: true },
        { title: 'Overtime Hourly Salary ($)', number: true },
      ]}
      buttons={[{ title: 'Add Employee', onClick: onButtonClick }]}
    />
  );
};

export const PassPurchaseForm = () => (
  <Form fields={[]} datePickers={[{ title: 'Pass Date', dateOnly: true }]} buttons={[{ title: 'Request' }]} />
);
