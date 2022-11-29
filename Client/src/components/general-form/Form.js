/* eslint-disable react/prop-types */
/* eslint-disable react-hooks/rules-of-hooks */
/* eslint-disable react/jsx-key */

import React, { useState } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import Button from '@mui/material/Button';

// props:
// array of objects pretaining to field description
// array of objects pretianing to date pickers (ranges)
// aray of objects pretaining to buttons
// array of forward functions executed from child for each of field filling, date picking, and button pressing

const Form = (props) => {
  const { fields, datePickers, buttons } = props;
  console.log(fields);

  return (
    <>
      {fields?.map?.((field) => {
        console.log(field);
        return (
          <Box
            sx={{
              width: '100%',
              maxWidth: '100%',
              marginBottom: '2em',
            }}
          >
            <TextField
              fullWidth
              label={field?.title}
              id="fullWidth"
              multiline={field?.desc}
              rows={4}
              placeholder={field.placeholder}
              value={field?.value}
              onChange={field?.onChange}
              type={field?.number && 'number'}
            />
          </Box>
        );
      })}
      <Box
        sx={{
          width: '100%',
          maxWidth: '100%',
          marginBottom: '2em',
          display: 'flex',
          flexDirection: 'row',
        }}
      >
        {datePickers?.map?.((datePicker) => {
          const [value, setValue] = useState('2022-04-07');
          return (
            <Box style={{ marginRight: '2em' }}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                {datePicker?.dateOnly ? (
                  <DesktopDatePicker
                    renderInput={(props) => <TextField {...props} />}
                    label={datePicker?.title}
                    value={value}
                    onChange={(newValue) => {
                      setValue(newValue);
                    }}
                  />
                ) : (
                  <DateTimePicker
                    renderInput={(props) => <TextField {...props} />}
                    label={datePicker?.title}
                    value={value}
                    onChange={(newValue) => {
                      setValue(newValue);
                      datePicker?.onChange?.(newValue);
                    }}
                  />
                )}
              </LocalizationProvider>
            </Box>
          );
        })}
      </Box>
      <Box
        sx={{
          width: '100%',
          maxWidth: '100%',
          marginBottom: '2em',
          flexDirection: 'row',
          justifyContent: 'space-evenly',
        }}
      >
        {buttons?.map?.((button) => (
          <Button sx={{ marginRight: '2em' }} variant="contained" onClick={button?.onClick}>
            {button?.title}
          </Button>
        ))}{' '}
      </Box>
    </>
  );
};

export default Form;