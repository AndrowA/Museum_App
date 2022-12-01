/* eslint-disable import/no-unresolved */
import { useState, useEffect, React } from 'react';
import { useSelector } from 'react-redux';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate, useParams } from 'react-router-dom';
import Form from 'components/general-form/Form';
import { setDate } from 'date-fns';
import { Typography } from '@mui/material';
import { Stack } from '@mui/system';

const LoanRequestForm = () => {
  const [startTime, setStartTime] = useState();
  const [endTime, setEndTime] = useState();
  const [artifactTitle, setArtifactTitle] = useState();
  const [artifactURL, setArtifactURL] = useState();
  const [artifactDescription, setArtifactDescription] = useState();
  const { requestLoan, getArtifact } = useApiClient();
  const userId = useSelector((state) => state?.user?.uid);
  const navigate = useNavigate();
  const { id: artifactId } = useParams();

  useEffect(() => {
    (async () => {
      const artifact = await getArtifact(artifactId);
      setArtifactTitle(artifact?.name);
      setArtifactDescription(artifact?.description);
      setArtifactURL(artifact?.url);
    })();
  }, []);

  const onBeginChange = (value) => {
    const date = new Date(value.$d);

    // var datestring = ("0" + d.getDate()).slice(-2) + "-" + ("0"+(d.getMonth()+1)).slice(-2) + "-" +
    // d.getFullYear() + " " + ("0" + d.getHours()).slice(-2) + ":" + ("0" + d.getMinutes()).slice(-2);

    setStartTime(date);
  };
  const onEndChange = (value) => {
    const date = new Date(value.$d);
    setEndTime(date);
  };

  const onClick = async () => {
    await requestLoan(userId, artifactId, startTime, endTime);
    // navigate("/dashboard/");
  };

  return (
    <Stack>
      <img
        src={artifactURL}
        alt={artifactTitle}
        height={200}
        width="100%"
        style={{ margin: 'none', objectFit: 'contain' }}
      />
      <Typography variant="h4" sx={{ mb: 5 }}>
        {artifactTitle}
      </Typography>
      <Typography variant="p" sx={{ mb: 5 }}>
        {artifactDescription}
      </Typography>
      <Form
        fields={[]}
        datePickers={[
          { title: 'Date of Beginning of loan', onChange: onBeginChange, dateOnly: true },
          { title: 'Date of end of loan', onChange: onEndChange, dateOnly: true },
        ]}
        buttons={[{ title: 'Request', onClick }]}
      />
    </Stack>
  );
};

export default LoanRequestForm;
