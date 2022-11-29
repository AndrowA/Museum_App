/* eslint-disable import/no-unresolved */
import { useState, React } from 'react';
import { useSelector } from 'react-redux';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate, useParams } from 'react-router-dom';
import Form from 'components/general-form/Form';
import { setDate } from 'date-fns';

const LoanRequestForm = () => {
  
    const [startTime, setStartTime] = useState();
    const [endTime, setEndTime] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [baseSalary, setBaseSalary] = useState(30.0);
    const [overTimeSalary, setOverTimeSalary] = useState(30.0);
    const { requestLoan } = useApiClient();
    const userId = useSelector(state => state?.user?.uid);
    const navigate = useNavigate(); 
    const {id:artifactId} = useParams();


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


const onBeginChange = (value) => {
  const date = new Date(value.$d)
  
  // var datestring = ("0" + d.getDate()).slice(-2) + "-" + ("0"+(d.getMonth()+1)).slice(-2) + "-" +
  // d.getFullYear() + " " + ("0" + d.getHours()).slice(-2) + ":" + ("0" + d.getMinutes()).slice(-2);
  
  setStartTime(date);
}
const onEndChange = (value) => {
  const date = new Date(value.$d);
  setEndTime(date);
}

const onClick = async () => {
  await requestLoan(userId, artifactId, startTime, endTime);
  // navigate("/dashboard/");
}

  return (
    <Form fields={[]} datePickers={[{ title: 'Beginning of shift', onChange: onBeginChange }, { title: 'End of shift', onChange: onEndChange }]} buttons={[{ title: 'Add Workday' , onClick}]} />
  );
};

export default LoanRequestForm;
