/* eslint-disable import/no-unresolved */
/* eslint-disable import/no-named-as-default-member */
/* eslint-disable import/no-named-as-default */
import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import { useSelector } from 'react-redux';

import AddArtifactForm from 'pages/AddArtifactForm';
import ModifyArtifactForm from 'pages/ModifyArtifactForm';
import DashboardLayout from './layouts/dashboard';
import LoanPage from './pages/LoanPage';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import Page404 from './pages/Page404';
import ArtifactPage from './pages/ArtifactPage';
import EmployeeSchedulePage from './pages/EmployeeSchedulePage';
import EmployeePage from './pages/EmployeePage';
import AddEmployeeForm from './pages/AddEmployeeForm';
import LoanRequestForm from './pages/LoanRequestForm';
import AddEmployeeScheduleForm from './pages/AddEmployeeScheduleForm';
import MuseumPassPage from './pages/MuseumPassPage';
import PurchasePassForm from './pages/PurchasePassForm';

import VisitorPage from './pages/VisitorPage';

// ----------------------------------------------------------------------

export default function Router() {
  const isLoggedIn = useSelector((state) => state.login?.value);
  const accountType = useSelector((state) => state.user?.type);
  // console.log(isLoggedIn);
  const routes = useRoutes([
    isLoggedIn
      ? { path: '*', element: <Navigate to="artifacts" /> }
      : {
          path: '*',
          element: <Navigate to="login" />,
        },
    isLoggedIn
      ? {
          path: '/',
          element: <DashboardLayout />,
          children: [
            { element: <Navigate to="artifacts" />, index: true },
            accountType === 'PRESIDENT' && { path: '/artifacts', element: <ArtifactPage /> },
            (accountType === 'PRESIDENT' || accountType === 'EMPLOYEE') && {
              path: '/dashboard/loans',
              element: <LoanPage />,
            },
            accountType === 'PRESIDENT' && {
              path: 'dashboard/ModifyArtifactForm/:id',
              element: <ModifyArtifactForm />,
            },
            accountType === 'PRESIDENT' && {
              path: 'dashboard/employeeScheduleForm/:id',
              element: <AddEmployeeScheduleForm />,
            },
            accountType === 'PRESIDENT' && { path: '/dashboard/employees', element: <EmployeePage /> },
            (accountType === 'PRESIDENT' || accountType === 'EMPLOYEE') && {
              path: '/dashboard/employeeSchedulePage/:id',
              element: <EmployeeSchedulePage />,
            },
            accountType === 'PRESIDENT' && { path: '/dashboard/employeeForm', element: <AddEmployeeForm /> },
            accountType === 'PRESIDENT' && { path: '/dashboard/AddArtifactForm', element: <AddArtifactForm /> },
            (accountType === 'PRESIDENT' || accountType === 'EMPLOYEE') && {
              path: '/dashboard/VisitorPage',
              element: <VisitorPage />,
            },
            (accountType === 'PRESIDENT' || accountType === 'EMPLOYEE') && {
              path: '/dashboard/MuseumPassPage',
              element: <MuseumPassPage />,
            },
            accountType === 'VISITOR' && { path: '/dashboard/LoanRequestForm/:id', element: <LoanRequestForm /> },
            accountType === 'VISITOR' && { path: '/dashboard/PurchasePassForm', element: <PurchasePassForm /> },
            { path: '*', element: <Navigate to={<Page404 />} /> },
          ],
        }
      : {
          path: 'login',
          element: <LoginPage />,
        },
    !isLoggedIn && {
      path: 'register',
      element: <SignUpPage />,
    },
  ]);

  return routes;
}
