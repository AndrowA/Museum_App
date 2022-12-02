/* eslint-disable import/no-unresolved */
import { useState, React } from 'react';
import { useSelector } from 'react-redux';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate } from 'react-router-dom';
import Form from 'components/general-form/Form';

const PurchasePassForm = () => {
  const { buyPass } = useApiClient();
  const [date, setDate] = useState();
  const userId = useSelector((state) => state?.user?.uid);
  const navigate = useNavigate();

  const onChangeDate = (value) => {
    console.log('executing');
    console.log(value);
    const date = new Date(value.$d);
    setDate(date);
  };

  const onClick = async () => {
    console.log(date);
    await buyPass(userId, date);
    navigate('/dashboard/');
  };

  return (
    <Form
      fields={[]}
      datePickers={[{ title: 'Pass Date', onChange: onChangeDate, dateOnly: true }]}
      buttons={[{ title: 'Buy Pass', onClick }]}
    />
  );
};

export default PurchasePassForm;
