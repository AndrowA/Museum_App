/* eslint-disable import/no-unresolved */
import { Helmet } from 'react-helmet-async';
import { useSelector } from 'react-redux';
import { useState, useEffect } from 'react';
// @mui
import { Container, Stack, Typography } from '@mui/material';
import { useApiClient } from 'apiClient/useApiClient';
// components
import { ProductSort, ProductList, ProductCartWidget, ProductFilterSidebar } from '../sections/@dashboard/products';
// mock
import PRODUCTS from '../_mock/products';


// ----------------------------------------------------------------------

export default function ProductsPage() {

  const {fillArtifactList} = useApiClient();

  const userId = useSelector(state=>state.user?.uid);

  const artifactList = useSelector(state=>state.artifact?.artifactList);

  const [openFilter, setOpenFilter] = useState(false);

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

  useEffect(() => {
    (async()=>{
    await fillArtifactList(100, 1);
    console.log(artifactList)
    }) ()
  }, [fillArtifactList])

  return (
    <>
      <Helmet>
        <title> Dashboard: Products | Minimal UI </title>
      </Helmet>

      <Container>
        <Typography variant="h4" sx={{ mb: 5 }}>
          Museum's Artifacts
        </Typography>

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
        <ProductList products={artifactList} />
        {/* <ProductCartWidget /> */}
      </Container>
    </>
  );
}
