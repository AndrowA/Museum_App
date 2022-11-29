import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import { useSelector } from 'react-redux';
import DashboardLayout from './layouts/dashboard';
// import SimpleLayout from './layouts/simple';
//
import BlogPage from './pages/BlogPage';
import UserPage from './pages/UserPage';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import Page404 from './pages/Page404';
import ProductsPage from './pages/ProductsPage';
import DashboardAppPage from './pages/DashboardAppPage';
import EmployeeSchedulePage from './pages/EmployeeSchedulePage';
import EmployeePage from './pages/EmployeePage';
import AddEmployeeForm from './pages/AddEmployeeForm';

import VisitorPage from './pages/VisitorPage';

// ----------------------------------------------------------------------

export default function Router() {
  const isLoggedIn = useSelector((state) => state.login.value);
  console.log(isLoggedIn);
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
            { path: '/artifacts', element: <ProductsPage /> },
            { path: '/dashboard/loans', element: <UserPage /> },
            { path: '/dashboard/app', element: <DashboardAppPage /> },
            { path: '/dashboard/user', element: <UserPage /> },
            { path: '/dashboard/employees', element: <EmployeePage /> },
            { path: '/dashboard/employeeSchedulePage/:id', element: <EmployeeSchedulePage /> },
            { path: '/dashboard/employeeForm', element: <AddEmployeeForm /> },
            { path: '/dashboard/products', element: <ProductsPage /> },
            { path: '/dashboard/blog', element: <BlogPage /> },
            { path: '/dashboard/VisitorPage', element: <VisitorPage /> },
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
