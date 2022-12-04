/* eslint-disable import/no-unresolved */
import React, { useState } from 'react';
import Form from 'components/general-form/Form';
import { useApiClient } from 'apiClient/useApiClient';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const AddArtifactForm = () => {
  
  const { addArtifact, assignArtifactRoom } = useApiClient();

  const navigate = useNavigate();
  const userId = useSelector((state) => state.user?.uid);
  const [artifactTitle, setArtifactTitle] = useState();
  const [artifactURL, setArtifactURL] = useState();
  const [artifactRoom, setArtifactRoom] = useState();
  const [artifactDescription, setArtifactDescription] = useState();

  const onChangeTitle = (e) => {
    // value comes from e.target.value
    setArtifactTitle(e?.target?.value);
  };
  const onChangeArtifactURL = (e) => {
    setArtifactURL(e?.target?.value);
  };
  const onChangeArtifactDescription = (e) => {
    setArtifactDescription(e?.target?.value);
  };
  const onChangeArtifactRoom = (e) => {
    setArtifactRoom(e?.target?.value);
  };

  const onButtonClick = async () => {
    const { artifactId } = await addArtifact(userId, artifactURL, artifactTitle, artifactDescription);
    // await assignArtifactRoom(userId, artifactId, artifactRoom).then(() => navigate('/artifacts'));
    navigate('/artifacts');
  };

  return (
    <Form
      fields={[
        { title: 'Artifact Title', onChange: onChangeTitle },
        { title: 'Artifact URL', onChange: onChangeArtifactURL },
        { title: 'Artifact room', onChange: onChangeArtifactRoom },
        { title: 'Artifact Description', desc: true, onChange: onChangeArtifactDescription },
      ]}
      buttons={[{ title: 'Add Artifact', onClick: onButtonClick }]}
    />
  );
};

export default AddArtifactForm;
