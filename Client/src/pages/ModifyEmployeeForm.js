/* eslint-disable import/no-unresolved */
import { useState, React } from 'react';
import { useSelector } from 'react-redux';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate, useParams } from 'react-router-dom';
import Form from 'components/general-form/Form';

const ModifyEmployeeForm = () => {
  const [baseSalary, setBaseSalary] = useState(30.0);
  const [overTimeSalary, setOverTimeSalary] = useState(30.0);
  const { registerEmployeeWithEmailAndPassword, setEmployeeSalary, setAccountNames } = useApiClient();
  const userId = useSelector((state) => state?.user?.uid);
  const navigate = useNavigate();
  const { id } = useParams();

  const onChangeBaseSalary = (e) => {
    setBaseSalary(e?.target?.value);
  };

  const onChangeOverTimeSalary = (e) => {
    setOverTimeSalary(e?.target?.value);
  };

  const onButtonClick = async () => {
    await setEmployeeSalary(userId, id, baseSalary, overTimeSalary);
    navigate('/dashboard/employees');
  };

  return (
    <Form
      fields={[
        { title: 'Base Hourly Salary ($)', number: true, onChange: onChangeBaseSalary },
        { title: 'Overtime Hourly Salary ($)', number: true, onChange: onChangeOverTimeSalary },
      ]}
      buttons={[{ title: 'Change Salary', onClick: onButtonClick }]}
    />
  );
};

export default ModifyEmployeeForm;
