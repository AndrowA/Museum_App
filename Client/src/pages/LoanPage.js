/* eslint-disable import/no-unresolved */
import { useSelector } from 'react-redux';
import { Helmet } from 'react-helmet-async';
import { filter } from 'lodash';
import { sentenceCase } from 'change-case';
import { useState, useEffect } from 'react';
// @mui

import {
  Card,
  Table,
  Stack,
  Paper,
  Avatar,
  Button,
  Popover,
  Checkbox,
  TableRow,
  TableHead,
  MenuItem,
  TableBody,
  TableCell,
  Container,
  Typography,
  IconButton,
  TableContainer,
  TablePagination,
} from '@mui/material';
import { useApiClient } from 'apiClient/useApiClient';
// components
import { useNavigate, useParams } from 'react-router-dom';

import Iconify from '../components/iconify';
import Scrollbar from '../components/scrollbar';
// sections
import { UserListHead, UserListToolbar } from '../sections/@dashboard/user';
// mock
import USERLIST from '../_mock/user';

// ----------------------------------------------------------------------

const TABLE_HEAD = [
  { id: 'loanId', label: 'loanId', alignRight: false },
  { id: 'email', label: 'email', alignRight: false },
  { id: 'startDate', label: 'startDate', alignRight: false },
  { id: 'endDate', label: 'endDate', alignRight: false },
  { id: 'loanStatus', label: 'loanStatus', alignRight: false },
  { id: 'artifactName', label: 'artifactName', alignRight: false },
  { id: '' },
];

// ----------------------------------------------------------------------

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function applySortFilter(array, comparator, query) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  if (query) {
    return filter(array, (_user) => _user.day.toLowerCase().indexOf(query.toLowerCase()) !== -1);
  }
  return stabilizedThis.map((el) => el[0]);
}

export default function LoanPage() {
  const { getAllLoans, getVisitorLoans, returnLoan, approveLoan, rejectLoan } = useApiClient();

  const userId = useSelector((state) => state.user?.uid);

  const accountType = useSelector((state) => state?.user?.type);

  const [loanList, setLoanList] = useState([]);

  const [open, setOpen] = useState();

  const [currentId, setcurrentId] = useState();

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [selected, setSelected] = useState([]);

  const [orderBy, setOrderBy] = useState('day');

  const [filterday, setFilterday] = useState('');

  const [rowsPerPage, setRowsPerPage] = useState(5);

  useEffect(() => {
    (async () => {
      if (accountType === 'VISITOR') {
        const tempLoanList = await getVisitorLoans(userId);
        setLoanList(tempLoanList);
      } else {
        const tempLoanList = await getAllLoans();
        setLoanList(tempLoanList);
      }
    })();
  }, [getAllLoans]);

  const handleOpenMenu = (event) => {
    console.log(event);
    setOpen(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setOpen(null);
  };

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelecteds = USERLIST.map((n) => n.day);
      setSelected(newSelecteds);
      return;
    }
    setSelected([]);
  };

  // to do
  const handleClick = (event, day) => {
    const selectedIndex = selected.indexOf(day);
    let newSelected = [];
    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, day);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(selected.slice(0, selectedIndex), selected.slice(selectedIndex + 1));
    }
    setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setPage(0);
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const handleFilterByday = (event) => {
    setPage(0);
    setFilterday(event.target.value);
  };

  const emptyRows = page > 0 ? Math.max(0, (1 + page) * rowsPerPage - USERLIST.length) : 0;

  const filteredUsers = applySortFilter(USERLIST, getComparator(order, orderBy), filterday);

  // const filteredWorkDays = applySortFilter(workDayList, getComparator(order, orderBy), filterday);

  const isNotFound = !filteredUsers.length && !!filterday;

  return (
    <>
      <Helmet>
        <title> User | Minimal UI </title>
      </Helmet>

      <Container>
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4" gutterBottom>
            Loans
          </Typography>
        </Stack>

        <Card>

          <Scrollbar>
            <TableContainer sx={{ minWidth: 800 }}>
              <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Loan Id</TableCell>
                  <TableCell>Email</TableCell>
                  <TableCell>Start Date</TableCell>
                  <TableCell>End Date</TableCell>
                  <TableCell>Loan Status</TableCell>
                  <TableCell>Artifact Name</TableCell>
                  <TableCell> </TableCell>
                </TableRow>
              </TableHead>
                <TableBody>
                  {loanList?.map?.((row) => {
                    console.log('this is the workDay List', loanList);
                    const { loanId, aLoaneeName, aStartDate, aEndDate, aLoanStatus, aArtifactName } = row;

                    return (
                      <TableRow hover key={loanId} tabIndex={-1}>

                        {/* <TableCell component="th" scope="row" padding="none">
                          <Stack direction="row" alignItems="center" spacing={2}>
                            <Avatar alt={day} src={avatarUrl} />
                            <Typography variant="subtitle2" noWrap>
                              {day}
                            </Typography>
                          </Stack>
                        </TableCell> */}

                        <TableCell align="left">{loanId}</TableCell>

                        <TableCell align="left">{aLoaneeName}</TableCell>

                        <TableCell align="left">{aStartDate}</TableCell>

                        <TableCell align="left">{aEndDate}</TableCell>

                        <TableCell align="left">{aLoanStatus}</TableCell>

                        <TableCell align="left">{aArtifactName}</TableCell>

                        {/* <TableCell align="left">
                          <Label color={(status === 'banned' && 'error') || 'success'}>{sentenceCase(status)}</Label>
                        </TableCell> */}

                        <TableCell align="right">
                          <IconButton
                            size="large"
                            color="inherit"
                            onClick={(e) => {
                              handleOpenMenu(e);
                              setcurrentId(loanId);
                            }}
                          >
                            <Iconify icon={'eva:more-vertical-fill'} />
                          </IconButton>
                        </TableCell>
                      </TableRow>
                    );
                  })}
                  {emptyRows > 0 && (
                    <TableRow style={{ height: 53 * emptyRows }}>
                      <TableCell colSpan={6} />
                    </TableRow>
                  )}
                </TableBody>

                {isNotFound && (
                  <TableBody>
                    <TableRow>
                      <TableCell align="center" colSpan={6} sx={{ py: 3 }}>
                        <Paper
                          sx={{
                            textAlign: 'center',
                          }}
                        >
                          <Typography variant="h6" paragraph>
                            Not found
                          </Typography>

                          <Typography variant="body2">
                            No results found for &nbsp;
                            <strong>&quot;{filterday}&quot;</strong>.
                            <br /> Try checking for typos or using complete words.
                          </Typography>
                        </Paper>
                      </TableCell>
                    </TableRow>
                  </TableBody>
                )}
              </Table>
            </TableContainer>
          </Scrollbar>

          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={USERLIST.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Card>
      </Container>

      <Popover
        open={Boolean(open)}
        anchorEl={open}
        onClose={handleCloseMenu}
        anchorOrigin={{ vertical: 'top', horizontal: 'left' }}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        PaperProps={{
          sx: {
            p: 1,
            width: 140,
            '& .MuiMenuItem-root': {
              px: 1,
              typography: 'body2',
              borderRadius: 0.75,
            },
          },
        }}
      >
        {accountType !== 'VISITOR' && (
          <MenuItem
            sx={{ color: 'success.main' }}
            onClick={async () => {
              await approveLoan(currentId, userId);
              const temp = await getAllLoans();
              setLoanList(temp);
            }}
          >
            <Iconify icon={'zondicons:checkmark'} sx={{ mr: 2 }} />
            Approve
          </MenuItem>
        )}

        <MenuItem
          onClick={async () => {
            await returnLoan(currentId);
            const temp = accountType === 'VISITOR' ? await getVisitorLoans(userId) : await getAllLoans();
            setLoanList(temp);
          }}
        >
          <Iconify icon={'ion:return-up-back'} sx={{ mr: 2 }} />
          return
        </MenuItem>

        <MenuItem
          sx={{ color: 'error.main' }}
          onClick={async () => {
            await rejectLoan(currentId, userId);
            const temp = accountType === 'VISITOR' ? await getVisitorLoans(userId) : await getAllLoans();
            setLoanList(temp);
          }}
        >
          <Iconify icon={'eva:trash-2-outline'} sx={{ mr: 2 }} />
          {accountType === 'VISITOR' ? 'Cancel' : 'reject'}
        </MenuItem>
      </Popover>
    </>
  );
}
