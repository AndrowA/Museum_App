/* eslint-disable import/no-unresolved */
// routes
import CustomAlert from 'components/custom-alert/CustomAlert';
import Router from './routes';
// theme
import ThemeProvider from './theme';
// components
import ScrollToTop from './components/scroll-to-top';
import { StyledChart } from './components/chart';

// ----------------------------------------------------------------------

export default function App() {
  return (
    <ThemeProvider>
      <CustomAlert />
      <ScrollToTop />
      <StyledChart />
      <Router />
    </ThemeProvider>
  );
}
