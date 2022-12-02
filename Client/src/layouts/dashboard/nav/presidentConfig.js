// component
import SvgColor from '../../../components/svg-color';
// ----------------------------------------------------------------------

const icon = (name) => <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />;

const presidentConfig = [
  {
    title: 'Artifacts',
    path: '/artifacts',
    icon: icon('ic_artifact'),
  },
  {
    title: 'Loans',
    path: '/dashboard/loans',
    icon: icon('ic_loan'),
  },
  {
    title: 'Employees',
    path: '/dashboard/employees',
    icon: icon('ic_user'),
  },
  {
    title: 'Museum Passes',
    path: '/dashboard/MuseumPassPage',
    icon: icon('ic_pass'),
  },
  {
    title: 'Visitors',
    path: '/dashboard/VisitorPage',
    icon: icon('ic_user'),
  },
];

export default presidentConfig;
