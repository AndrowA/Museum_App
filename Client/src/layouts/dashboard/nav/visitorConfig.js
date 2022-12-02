// component
import SvgColor from '../../../components/svg-color';
// ----------------------------------------------------------------------

const icon = (name) => <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />;

const visitorConfig = (vId) => [
  {
    title: 'Artifacts',
    path: '/artifacts',
    icon: icon('ic_artifact'),
  },
  {
    title: 'My passes',
    path: '/dashboard/MuseumPassPage',
    icon: icon('ic_pass'),
  },
  {
    title: 'My Loans',
    path: '/dashboard/loans',
    icon: icon('ic_loan'),
  },
  {
    title: 'Donate',
    path: '/dashboard/AddArtifactForm',
    icon: icon('ic_artifact'),
  },
];

export default visitorConfig;
