import { useState } from 'react'
import {
  Typography, Box, Tabs, Tab, Accordion, AccordionSummary, AccordionDetails,
  Menu, MenuItem, Button, Breadcrumbs, Link, BottomNavigation, BottomNavigationAction,
  Stepper, Step, StepLabel, StepContent, Paper,
} from '@mui/material'
import {
  ExpandMore as ExpandMoreIcon, Home as HomeIcon, Explore, Settings,
  ArrowForward,
} from '@mui/icons-material'

const steps = [
  { label: 'Select campaign settings', description: 'Choose the campaign type and configure basic settings.' },
  { label: 'Create an ad group', description: 'An ad group contains one or more ads.' },
  { label: 'Create an ad', description: 'Write your ad copy and add media.' },
]

export default function NavigationPage() {
  const [tabVal, setTabVal] = useState(0)
  const [menuAnchor, setMenuAnchor] = useState(null)
  const [bottomNav, setBottomNav] = useState(0)
  const [activeStep, setActiveStep] = useState(0)

  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        Navigation
      </Typography>

      {/* ===== Tabs Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Tabs</Typography>
        <Box sx={{ borderBottom: 1, borderColor: 'divider', mb: 2 }}>
          <Tabs id="basic-tabs" value={tabVal} onChange={(_, v) => setTabVal(v)}>
            <Tab id="tab-overview" label="Overview" />
            <Tab id="tab-details" label="Details" />
            <Tab id="tab-settings" label="Settings" />
          </Tabs>
        </Box>
        <Typography>Tab content: {['Overview content', 'Details content', 'Settings content'][tabVal]}</Typography>
      </Box>

      {/* ===== Accordion Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Accordion</Typography>
        <Accordion id="accordion-personal" sx={{ mb: 1 }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography>Personal Information</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Typography>Name: John Doe</Typography>
            <Typography>Email: john@example.com</Typography>
          </AccordionDetails>
        </Accordion>
        <Accordion id="accordion-account" sx={{ mb: 1 }}>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography>Account Settings</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Typography>Language: English</Typography>
            <Typography>Timezone: UTC+8</Typography>
          </AccordionDetails>
        </Accordion>
        <Accordion id="accordion-disabled" disabled>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography>Disabled Section</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <Typography>This section is disabled.</Typography>
          </AccordionDetails>
        </Accordion>
      </Box>

      {/* ===== Menu Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Menu</Typography>
        <Button id="btn-open-menu" variant="contained" onClick={(e) => setMenuAnchor(e.currentTarget)}>
          Open Menu
        </Button>
        <Menu
          id="test-menu"
          anchorEl={menuAnchor}
          open={Boolean(menuAnchor)}
          onClose={() => setMenuAnchor(null)}
        >
          <MenuItem id="menu-profile" onClick={() => setMenuAnchor(null)}>Profile</MenuItem>
          <MenuItem id="menu-my-account" onClick={() => setMenuAnchor(null)}>My Account</MenuItem>
          <MenuItem id="menu-logout" onClick={() => setMenuAnchor(null)}>Logout</MenuItem>
        </Menu>
      </Box>

      {/* ===== Breadcrumbs Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Breadcrumbs</Typography>
        <Breadcrumbs id="breadcrumbs-nav" separator={<ArrowForward fontSize="small" />}>
          <Link underline="hover" color="inherit" href="/">
            <HomeIcon sx={{ mr: 0.5 }} /> Home
          </Link>
          <Link underline="hover" color="inherit" href="/navigation">
            Navigation
          </Link>
          <Typography color="text.primary">Breadcrumbs</Typography>
        </Breadcrumbs>
      </Box>

      {/* ===== BottomNavigation Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Bottom Navigation</Typography>
        <Paper sx={{ maxWidth: 400 }}>
          <BottomNavigation
            id="bottom-nav"
            showLabels
            value={bottomNav}
            onChange={(_, v) => setBottomNav(v)}
          >
            <BottomNavigationAction id="bnav-home" label="Home" icon={<HomeIcon />} />
            <BottomNavigationAction id="bnav-explore" label="Explore" icon={<Explore />} />
            <BottomNavigationAction id="bnav-settings" label="Settings" icon={<Settings />} />
          </BottomNavigation>
        </Paper>
      </Box>

      {/* ===== Stepper Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Stepper</Typography>
        <Box sx={{ maxWidth: 500 }}>
          <Stepper id="test-stepper" activeStep={activeStep} orientation="vertical">
            {steps.map((step) => (
              <Step key={step.label}>
                <StepLabel>{step.label}</StepLabel>
                <StepContent>
                  <Typography>{step.description}</Typography>
                  <Box sx={{ mt: 1 }}>
                    <Button
                      id="stepper-back"
                      disabled={activeStep === 0}
                      onClick={() => setActiveStep((prev) => prev - 1)}
                      sx={{ mr: 1 }}
                    >
                      Back
                    </Button>
                    <Button
                      id="stepper-next"
                      variant="contained"
                      onClick={() => setActiveStep((prev) => prev + 1)}
                    >
                      {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                    </Button>
                  </Box>
                </StepContent>
              </Step>
            ))}
          </Stepper>
          {activeStep === steps.length && (
            <Paper sx={{ p: 2, mt: 2 }}>
              <Typography>All steps completed!</Typography>
              <Button id="stepper-reset" onClick={() => setActiveStep(0)}>Reset</Button>
            </Paper>
          )}
        </Box>
      </Box>
    </>
  )
}
