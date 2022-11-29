/* eslint-disable import/no-unresolved */
import { useState, React } from 'react';
import { useSelector } from 'react-redux';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate } from 'react-router-dom';
import Form from 'components/general-form/Form';

const AddEmployeeForm = () => {
  
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [baseSalary, setBaseSalary] = useState(30.0);
    const [overTimeSalary, setOverTimeSalary] = useState(30.0);
    const { registerEmployeeWithEmailAndPassword, setEmployeeSalary } = useApiClient();
    const userId = useSelector(state => state?.user?.uid);
    const navigate = useNavigate(); 


    const onChangeEmail = (e) => {
        setEmail(e?.target?.value);
    };
    
    const onChangePassword = (e) => {
        setPassword(e?.target?.value);
    }

    const onChangeBaseSalary = (e) =>{
        setBaseSalary(e?.target?.value);
    }

    const onChangeOverTimeSalary = (e) => {
        setOverTimeSalary(e?.target?.value);
    }

  const onButtonClick = async() => {
    const id = await registerEmployeeWithEmailAndPassword(email, password); 
      await setEmployeeSalary(userId, id, baseSalary, overTimeSalary);
    navigate("/dashboard/employees");
}

  return (
    <Form
      fields={[
        { title: 'Email', onChange: onChangeEmail },
        { title: 'Password' , onChange: onChangePassword},
        { title: 'Base Hourly Salary ($)', number: true , onChange: onChangeBaseSalary},
        { title: 'Overtime Hourly Salary ($)', number: true, onChange: onChangeOverTimeSalary },
      ]}
      buttons={[{ title: 'Add Employee', onClick: onButtonClick }]}
    />
  );
};

export default AddEmployeeForm;
