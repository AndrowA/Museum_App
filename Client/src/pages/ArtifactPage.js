/* eslint-disable import/no-unresolved */
import { Helmet } from 'react-helmet-async';
import { useSelector } from 'react-redux';
import { useState, useEffect } from 'react';
import * as React from 'react';
// @mui
import { Container, Stack, Typography, Button } from '@mui/material';
import { useApiClient } from 'apiClient/useApiClient';
import { useNavigate, useParams } from 'react-router-dom';
import Pagination from '@mui/material/Pagination';
import Iconify from '../components/iconify';

// components
import { ArtifactList } from '../sections/@dashboard/Aartifacts';
// mock
import PRODUCTS from '../_mock/products';



// ----------------------------------------------------------------------

export default function ProductsPage() {
  const { fillArtifactList } = useApiClient();

  const userId = useSelector((state) => state.user?.uid);
  const artifactList = useSelector((state) => state.artifact?.artifactList);
  const accountType = useSelector((state) => state.user?.type);

  const [openFilter, setOpenFilter] = useState(false);
  
  const handleChange = (event, value) => {
    try {
      (async () => {
        await fillArtifactList(50, value);
      })();
    } catch (error) {
      console.log(error);
    }
  };

  const navigate = useNavigate();

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

  useEffect(() => {
    (async () => {
      await fillArtifactList(100, 1);
    })();
  }, [fillArtifactList]);

  return (
    <>
      <Helmet>
        <title> Artifacts | My Museum </title>
      </Helmet>

      <Container>
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4" sx={{ mb: 5 }}>
            Museum's Artifacts
          </Typography>
          {!(accountType === 'EMPLOYEE') && (
            <Button
              onClick={() =>
                accountType === 'PRESIDENT'
                  ? navigate(`/dashboard/AddArtifactForm`)
                  : accountType === 'VISITOR'
                  ? navigate(`/dashboard/PurchasePassForm/`)
                  : null
              }
              variant="contained"
              startIcon={<Iconify icon="eva:plus-fill" />}
            >
              {accountType === 'PRESIDENT' ? 'Add Artifact' : accountType === 'VISITOR' ? 'Buy Pass' : null}
            </Button>
          )}
        </Stack>
        

        {/* <Stack direction="row" flexWrap="wrap-reverse" alignItems="center" justifyContent="flex-end" sx={{ mb: 5 }}>
          <Stack direction="row" spacing={1} flexShrink={0} sx={{ my: 1 }}>
            <ProductFilterSidebar
              openFilter={openFilter}
              onOpenFilter={handleOpenFilter}
              onCloseFilter={handleCloseFilter}
            />
            <ProductSort />
          </Stack>
        </Stack> */}
        <ArtifactList products={artifactList}/>
        {/* <ProductCartWidget /> */}

        <Stack spacing={2} sx={{paddingTop:"30px", display:"block", marginLeft:"auto", marginRight: "auto", width: "40%"}}>
          <Pagination count={10} onChange={handleChange} />
        </Stack>
      </Container>
    </>
  );
}
