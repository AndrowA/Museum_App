/* eslint-disable import/no-unresolved */
/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable react-hooks/exhaustive-deps */
import { useCallback } from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { setUid, setType } from 'redux/userSlice';
import { logIn } from 'redux/loginSlice';
import { sendMessage } from 'redux/alertSlice';
import { setArtifactList } from 'redux/artifactSlice';

export const useApiClient = () => {
  const dispatch = useDispatch();
  const url = 'http://localhost:8080';

  // Account endpoints
  const getLoggedInAccount = useCallback(async (requesterId) => {
    await axios
      .get(`${url}/account/info/${requesterId}/${requesterId}`)
      .then((response) => {
        dispatch(setType(response.data?.accountType));
      })
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
  }, []);

  const registerWithEmailAndPassword = useCallback(async (email, password) => {
    await axios
      .post(`${url}/account/register`, {
        email,
        password,
        accountType: 'VISITOR',
      })
      .then((response) => {
        dispatch(setUid(response.data));
        getLoggedInAccount(response.data);
        dispatch(logIn());
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);

  const signInWithEmailAndPassword = useCallback(async (email, password) => {
    await axios
      .post(`${url}/account/login`, {
        email,
        password,
      })
      .then((response) => {
        dispatch(setUid(response.data));
        getLoggedInAccount(response.data);
        dispatch(logIn());
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);

  const removeAccount = useCallback(async (requesterId, targetId) => {
    await axios
      .post(`${url}/account/remove/${requesterId}/${targetId}`)
      .then(() => {
        dispatch(sendMessage({ open: true, message: 'user successfully removed', severity: 'success' }));
      })
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
  }, []);

  // Employee endpoints
  const getArtifact = useCallback(async (artifactID) => {
    const output = await axios
      .get(`${url}/artifact/get/${artifactID}`)
      .then((res) => res.data)
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
    return output;
  }, []);

  const fillArtifactList = useCallback(async (count, page) => {
    await axios
      .get(`${url}/artifact/all/${page}/${count}`)
      .then((response) => {
        dispatch(setArtifactList(response.data));
      })
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
  }, []);

  const addArtifact = useCallback(async (requesterId, imageURL, name, description) => {
    await axios
      .post(`${url}/artifact/add?token=${requesterId}`, {
        url: imageURL,
        name,
        description,
      })
      .then(() => {
        dispatch(sendMessage({ open: true, message: `artifact ${name} successfully added`, severity: 'success' }));
      })
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
  }, []);

  const modifyArtifact = useCallback(async (requesterId, artifactId, imageURL, name, description) => {
    await axios
      .post(`${url}/artifact/${requesterId}?token=${artifactId}`, {
        url: imageURL,
        name,
        description,
      })
      .then(() => {
        dispatch(sendMessage({ open: true, message: `artifact ${name} successfully modified`, severity: 'success' }));
      })
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
  }, []);

  const assignArtifactRoom = useCallback(async (requesterId, artifactId, roomId) => {
    await axios
      .post(`${url}/artifact/assign/room/${artifactId}/${roomId}?token=${requesterId}`)
      .then(() => {
        dispatch(
          sendMessage({ open: true, message: `artifact successfully assigned to room ${roomId}`, severity: 'success' })
        );
      })
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
  }, []);

  const getRoomInfo = useCallback(async (roomId) => {
    const output = await axios
      .get(`${url}/artifact/assign/room/info/${roomId}`)
      .then((res) => res.data)
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
    return output;
  }, []);

  // Employee endpoint
  const registerEmployeeWithEmailAndPassword = useCallback(async (email, password) => {
    await axios
      .post(`${url}/employee/register`, {
        email,
        password,
        accountType: 'EMPLOYEE',
      })
      .then(() => {
        dispatch(sendMessage({ open: true, message: `registered new employee`, severity: 'success' }));
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);

  const removeEmployee = useCallback(async (requesterId, employeeId) => {
    await axios
      .post(`${url}/employee/remove/${requesterId}/${employeeId}`)
      .then(() => {
        dispatch(sendMessage({ open: true, message: `Employee successfully removed`, severity: 'success' }));
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);

  // TOOD: verify format
  const setEmployeeSalary = useCallback(async (requesterId, employeeId, hourlyWage, overTimeHourlyWage) => {
    axios
      .post(`${url}/employee/${requesterId}/${employeeId}`, {
        hourlyWage,
        overTimeHourlyWage,
      })
      .then(() => {
        dispatch(sendMessage({ open: true, message: `Employee salary updated`, severity: 'success' }));
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);

  const getEmployee = useCallback(async (requesterId, employeeId) => {
    const output = await axios
      .get(`${url}/employee/info/${requesterId}/${employeeId}`)
      .then((response) => response.data)
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
    return output;
  }, []);

  const getEmployees = useCallback(async (requesterId) => {
    const output = await axios
      .get(`${url}/employee/getEmployees/${requesterId}`)
      .then((response) => response.data)
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
    return output;
  }, []);

  const getVisitors = useCallback(async (requesterId) => {
    const output = await axios
      .get(`${url}/employee/getVisitors/{rid}`)
      .then((response) => response.data)
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
    return output;
  }, []);

  const getEmployeeSchedule = useCallback(async (requesterId, employeeId) => {
    const output = await axios
      .get(`${url}/employee/schedule/getSchedule/${requesterId}/${employeeId}`)
      .then((response) => response.data)
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
    return output;
  }, []);

  const getWorkdayByDate = useCallback(async (requesterId, employeeId) => {
    const output = await axios
      .get(`${url}/employee/schedule/getWorkDayByDateAndId/${requesterId}/${employeeId}`)
      .then((response) => response.data)
      .catch((err) => dispatch(sendMessage({ open: true, message: err.message, severity: 'error' })));
    return output;
  },[]);

  const addWorkDayForEmployee = useCallback(async (employeeId, requesterId, startTime, endTime, day) => {
    await axios
      .post(`${url}/employee/schedule/${requesterId}/${employeeId}/addWorkDay`, {
        startTime,
        endTime,
        day,
      })
      .then(() => {
        dispatch(sendMessage({ open: true, message: `Workday added to employee schedule`, severity: 'success' }));
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);

  const modifyWorkDayForEmployee = useCallback(async (employeeId, requesterId, startTime, endTime, day) => {
    await axios
      .post(`${url}/employee/schedule/${requesterId}/${employeeId}/ModifyWorkDay`, {
        startTime,
        endTime,
        day,
      })
      .then(() => {
        dispatch(sendMessage({ open: true, message: `Workday modified in employee schedule`, severity: 'success' }));
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);

  const removeWorkDayForEmployee = useCallback(async (requesterId, employeeId, day) => {
    await axios
      .post(`${url}/employee/schedule/${requesterId}/${employeeId}/DeleteWorkDay`, {'day': day} )
      .then(() => {
        dispatch(sendMessage({ open: true, message: `Workday removed from employee schedule`, severity: 'success' }));
      })
      .catch((err) => {
        dispatch(sendMessage({ open: true, message: err.message, severity: 'error' }));
      });
  }, []);
  
  // Loan endpoints
  const getLoan = useCallback(async (loanId) => {
    const output = await axios
        .get(`${url}/loan/get/${loanId}`)
        .then((res)=>res.data)
        .catch((err)=> dispatch(sendMessage({open: true, message: err.message, severity: 'error'})));
    return output;
  }, []);

  const requestLoan = useCallback(async (visitorId, artifactId) => {
    await axios
        .post(`${url}/loan/createLoanRequest/${visitorId}/${artifactId}`)
        .then(() => {
          dispatch( sendMessage({ open: true, message: "Loan successfully requested", severity: 'success' }));
        })
        .catch((err)=> dispatch(sendMessage({open: true, message: err.message, severity: 'error'})));
  }, []);

  const approveLoan = useCallback(async (loanId, requesterId) => {
    await axios
        .post(`${url}/loan/approveLoan/${loanId}/${requesterId}`)
        .then((res) => {
          dispatch( sendMessage({ open: true, message: "Successfully approved loan", severity: 'success' }));
        })
        .catch((err)=> dispatch(sendMessage({open: true, message: err.message, severity: 'error'})));
  }, []);

  const returnLoan = useCallback(async (loanId) => {
    await axios
        .post(`${url}/loan/returnLoan/${loanId}`)
        .then((res) => {
          dispatch( sendMessage({ open: true, message: "Loan has been returned successfully", severity: 'success' }));
        })
        .catch((err)=> dispatch(sendMessage({open: true, message: err.message, severity: 'error'})));
  }, []);

  const rejectLoan = useCallback(async (loanId, requesterId) => {
    await axios
        .post(`${url}/loan/rejectLoan/${loanId}/${requesterId}`)
        .then((res) => {
          dispatch( sendMessage({ open: true, message: "Loan rejected", severity: 'success' }));
        })
        .catch((err)=> dispatch(sendMessage({open: true, message: err.message, severity: 'error'})));
  }, []);

// pass endpoints
  const buyPass = useCallback(async (visitorId, passDate) => {
    await axios
        .post(`${url}/museumPass/${visitorId}/buy}`,{
          passDate,
        })
        .then((res) => {
          dispatch( sendMessage({ open: true, message: "Successfully bought a pass", severity: 'success' }));
        })
        .catch((err)=> dispatch(sendMessage({open: true, message: err.message, severity: 'error'})));
  }, []);

  const getPass = useCallback(async (visitorId, passDate) => {
    const output = await axios
        .get(`${url}/museumPass/${visitorId}/info}`)
        .then((res) => res.data)
        .catch((err)=> dispatch(sendMessage({open: true, message: err.message, severity: 'error'})));
    return output;
  }, []);

  return {
    registerWithEmailAndPassword,
    signInWithEmailAndPassword,
    getLoggedInAccount,
    removeAccount,
    getArtifact,
    fillArtifactList,
    addArtifact,
    modifyArtifact,
    assignArtifactRoom,
    getRoomInfo,
    registerEmployeeWithEmailAndPassword,
    removeEmployee,
    setEmployeeSalary,
    getEmployee,
    getEmployees,
    getVisitors,
    getEmployeeSchedule,
    getWorkdayByDate,
    addWorkDayForEmployee,
    modifyWorkDayForEmployee,
    removeWorkDayForEmployee,
    getLoan,
    requestLoan,
    approveLoan,
    returnLoan,
    rejectLoan,
    buyPass,
    getPass,
  };
};
