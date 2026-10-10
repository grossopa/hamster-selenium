import { Routes, Route, useNavigate, useLocation } from 'react-router-dom'
import {
  Box, Drawer, List, ListItemButton, ListItemIcon, ListItemText,
  AppBar, Toolbar, Typography, Divider,
} from '@mui/material'
import {
  Home as HomeIcon, Input as InputIcon, ViewList as DataDisplayIcon,
  Notifications as FeedbackIcon, Navigation as NavIcon, Layers as SurfacesIcon,
  MyLocation as LocatorIcon, Code as HtmlIcon, Route as RouteIcon,
} from '@mui/icons-material'
import Home from './pages/Home.jsx'
import Inputs from './pages/Inputs.jsx'
import DataDisplay from './pages/DataDisplay.jsx'
import Feedback from './pages/Feedback.jsx'
import NavigationPage from './pages/NavigationPage.jsx'
import Surfaces from './pages/Surfaces.jsx'
import LocatorStrategies from './pages/LocatorStrategies.jsx'
import HtmlElements from './pages/HtmlElements.jsx'
import DynamicRoute from './pages/DynamicRoute.jsx'

const DRAWER_WIDTH = 240

const menuItems = [
  { path: '/', label: 'Home', icon: <HomeIcon /> },
  { path: '/inputs', label: 'Inputs', icon: <InputIcon /> },
  { path: '/data-display', label: 'Data Display', icon: <DataDisplayIcon /> },
  { path: '/feedback', label: 'Feedback', icon: <FeedbackIcon /> },
  { path: '/navigation', label: 'Navigation', icon: <NavIcon /> },
  { path: '/surfaces', label: 'Surfaces', icon: <SurfacesIcon /> },
  { path: '/locator-strategies', label: 'Locator Strategies', icon: <LocatorIcon /> },
  { path: '/html-elements', label: 'HTML Elements', icon: <HtmlIcon /> },
  { path: '/users/1', label: 'Dynamic Route', icon: <RouteIcon /> },
]

function AppLayout() {
  const navigate = useNavigate()
  const location = useLocation()

  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <Toolbar>
          <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
            Hamster Recorder Test App
          </Typography>
          <Typography variant="body2">MUI v5 Test Suite</Typography>
        </Toolbar>
      </AppBar>

      <Drawer
        variant="permanent"
        sx={{
          width: DRAWER_WIDTH,
          flexShrink: 0,
          '& .MuiDrawer-paper': { width: DRAWER_WIDTH, boxSizing: 'border-box' },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: 'auto' }}>
          <List>
            {menuItems.map((item) => (
              <ListItemButton
                key={item.path}
                selected={location.pathname === item.path}
                onClick={() => navigate(item.path)}
              >
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.label} />
              </ListItemButton>
            ))}
          </List>
          <Divider />
        </Box>
      </Drawer>

      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/inputs" element={<Inputs />} />
          <Route path="/data-display" element={<DataDisplay />} />
          <Route path="/feedback" element={<Feedback />} />
          <Route path="/navigation" element={<NavigationPage />} />
          <Route path="/surfaces" element={<Surfaces />} />
          <Route path="/locator-strategies" element={<LocatorStrategies />} />
          <Route path="/html-elements" element={<HtmlElements />} />
          <Route path="/users/:id" element={<DynamicRoute />} />
          <Route path="/products/:id" element={<DynamicRoute />} />
        </Routes>
      </Box>
    </Box>
  )
}

export default function App() {
  return <AppLayout />
}
