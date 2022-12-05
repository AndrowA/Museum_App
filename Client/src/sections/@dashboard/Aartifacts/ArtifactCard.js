/* eslint-disable no-undef */

import { useNavigate, useParams } from 'react-router-dom';

import PropTypes from 'prop-types';
// @mui
import { Box, Card, Link, Typography, Stack } from '@mui/material';
import { styled } from '@mui/material/styles';
// utils
import { useSelector } from 'react-redux';


// ----------------------------------------------------------------------

const StyledProductImg = styled('img')({
  top: 0,
  width: '100%',
  height: '100%',
  objectFit: 'cover',
  position: 'absolute',
});

// ----------------------------------------------------------------------

AritfactCard.propTypes = {
  product: PropTypes.object,
};
export default function AritfactCard({ product }) {
  const navigate = useNavigate();
  const accountType = useSelector((state)=> state?.user?.type)
  const { id, name, url, roomName } = product;

  return (
    <Card
      onClick={() => accountType==="VISITOR"? navigate(`/dashboard/LoanRequestForm/${id}`): accountType==="PRESIDENT" && navigate(`/dashboard/ModifyArtifactForm/${id}`)}
      sx={{
        cursor: 'pointer',
      }}
    >
      <Box sx={{ pt: '100%', position: 'relative' }}>
        {/* {status && (
          <Label
            variant="filled"
            color={(status === 'sale' && 'error') || 'info'}
            sx={{
              zIndex: 9,
              top: 16,
              right: 16,
              position: 'absolute',
              textTransform: 'uppercase',
            }}
          >
            {status}
          </Label>
        )} */}
        <StyledProductImg alt={name} src={url} />
      </Box>

      <Stack spacing={2} sx={{ p: 3 }}>
        <Link color="inherit" underline="hover">
          <Typography variant="subtitle2" noWrap>
            {name}
          </Typography>
          {/* <button onClick={()=> navigate(`/dashboard/LoanRequestForm/${id}`)} >
            request Loan
          </button> */}
        </Link>

        <Stack direction="row" alignItems="center" justifyContent="space-between">
          {/* <ColorPreview colors={colors} /> */}
          <Typography variant="subtitle1">
            <Typography
              component="span"
              variant="body1"
              sx={{
                color: 'text.disabled',
                textDecoration: 'line-through',
              }}
            >
              {/* {priceSale && fCurrency(priceSale)} */}
            </Typography>
            {/* &nbsp;
            {fCurrency(roomId)} */}
            {roomName}
          </Typography>
        </Stack>
      </Stack>
    </Card>
  );
}
