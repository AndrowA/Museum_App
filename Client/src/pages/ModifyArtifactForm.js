/* eslint-disable import/no-unresolved */
import React, { useState, useEffect } from 'react';
import Form from 'components/general-form/Form';
import { useApiClient } from 'apiClient/useApiClient';
import { useSelector } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';

const ModifyArtifactForm = () => {
  const navigate = useNavigate();
  const userId = useSelector((state) => state.user?.uid);
  const [artifactTitle, setArtifactTitle] = useState();
  const [artifactURL, setArtifactURL] = useState();
  const [artifactDescription, setArtifactDescription] = useState();
  const [artifactRoom, setArtifactRoom] = useState();
  const { getArtifact, modifyArtifact, assignArtifactRoom, removeArtifact } = useApiClient();
  const { id: artifactId } = useParams();

  useEffect(() => {
    (async () => {
      const artifact = await getArtifact(artifactId);
      setArtifactTitle(artifact?.name);
      setArtifactDescription(artifact?.description);
      setArtifactURL(artifact?.url);
    })();
  }, [artifactId, getArtifact]);
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

  const onEditClick = async () => {
    await modifyArtifact(userId, artifactId, artifactURL, artifactTitle, artifactDescription);
    // await assignArtifactRoom(userId, artifactId, artifactRoom || 0).then(() => navigate('/artifacts'));
    navigate('/artifact');
  };

  const onDeleteClick = async () => {
    await removeArtifact(userId, artifactId);
    navigate('/artifact');
    // L
  };

  return (
    <Form
      fields={[
        { title: 'Artifact Title', onChange: onChangeTitle, value: artifactTitle },
        { title: 'Artifact URL', onChange: onChangeArtifactURL, value: artifactURL },
        { title: 'Artifact room', onChange: onChangeArtifactRoom, value: artifactRoom },
        {
          title: 'Artifact Description',
          desc: true,
          onChange: onChangeArtifactDescription,
          value: artifactDescription,
        },
      ]}
      buttons={[
        { title: 'Save', onClick: onEditClick },
        { title: 'delete', onClick: onDeleteClick },
      ]}
    />
  );
};

export default ModifyArtifactForm;
