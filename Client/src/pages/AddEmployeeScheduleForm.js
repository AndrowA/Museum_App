/* eslint-disable import/no-unresolved */
import { useState, React } from 'react';
import { useSelector } from 'react-redux';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate, useParams } from 'react-router-dom';
import Form from 'components/general-form/Form';

const AddEmployeeScheduleForm = () => {

    const [day, setDay] = useState();
    const [startTime, setStartTime] = useState();
    const [endTime, setEndTime] = useState();
    const { addWorkDayForEmployee } = useApiClient();
    const userId = useSelector(state => state?.user?.uid);
    const navigate = useNavigate();
    const { id } = useParams();


    const onBeginChange = (value) => {
        const date = new Date(value.$d)
        setDay(`${date.getFullYear()}-${String(date.getMonth()+1).padStart(2,"0")}-${String(date.getDate()).padStart(2,"0")}`);
        console.log(day);
        // var datestring = ("0" + d.getDate()).slice(-2) + "-" + ("0"+(d.getMonth()+1)).slice(-2) + "-" +
        // d.getFullYear() + " " + ("0" + d.getHours()).slice(-2) + ":" + ("0" + d.getMinutes()).slice(-2);
        
        setStartTime(`${String(date.getHours()).padStart(2,"0")}:${ String(date.getMinutes()).padStart(2,"0") }`);
    }
    const onEndChange = (value) => {
        const date = new Date(value.$d);
        setEndTime(`${String(date.getHours()).padStart(2,"0")}:${ String(date.getMinutes()).padStart(2,"0") }`);
    }

    const onClick = async () => {
        await addWorkDayForEmployee(id,userId,startTime,endTime,day);
        navigate("/dashboard/employees");
    }
    return (
        <Form fields={[]} datePickers={[{ title: 'Beginning of shift', onChange: onBeginChange }, { title: 'End of shift', onChange: onEndChange }]} buttons={[{ title: 'Add Workday' , onClick}]} />
    );
};

export default AddEmployeeScheduleForm;
