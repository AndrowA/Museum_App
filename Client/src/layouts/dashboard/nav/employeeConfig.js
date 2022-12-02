// component
import SvgColor from '../../../components/svg-color';
// ----------------------------------------------------------------------

const icon = (name) => <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />;

const employeeConfig = (eId) => [
  {
    title: 'Artifacts',
    path: '/artifacts',
    icon: icon('ic_artifact'),
  },
  {
    title: 'Loans',
    path: '/dashboard/loans',
    icon: icon('ic_user'),
  },
  {
    title: 'My Schedule',
    path: `dashboard/employeeSchedulePage/${eId}`,
    icon: icon('ic_user'),
  },
  {
    title: 'Museum Passes',
    path: '/dashboard/MuseumPassPage',
    icon: icon('ic_user'),
  },
  {
    title: 'Visitors',
    path: '/dashboard/VisitorPage',
    icon: icon('ic_blog'),
  },
];

export default employeeConfig;
