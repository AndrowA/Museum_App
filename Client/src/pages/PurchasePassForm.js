/* eslint-disable import/no-unresolved */
import { useState, React } from 'react';
import { useSelector } from 'react-redux';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate} from 'react-router-dom';
import Form from 'components/general-form/Form';

const PurchasePassForm = () => {

    const { buyPass } = useApiClient();
    const [date, setDate] = useState();
    const userId = useSelector(state => state?.user?.uid);
    const navigate = useNavigate();


    const onChange = (value) => {
      const date = new Date(value.$d);
      setDate(date);
    }

    const onClick = async () => {
      await buyPass(userId, date);
      navigate("/dashboard/");
    }

  return (
    <Form 
        fields={[]}
        datePickers={[{ title: 'Pass Date', onChange }]}
        buttons={[{ title: 'Buy Pass' , onClick}]} 
    />
  );
};

export default PurchasePassForm;
