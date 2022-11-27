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
  };
};
