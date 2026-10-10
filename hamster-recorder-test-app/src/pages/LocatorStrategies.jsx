import {
  Typography, Box, Button, TextField, Checkbox, FormControlLabel,
  Radio, RadioGroup, Paper, Divider,
} from '@mui/material'

/**
 * This page is specifically designed to test all locator strategies
 * supported by the Hamster Selenium Recorder:
 *
 * 1. ID-based: elements with unique id attributes
 * 2. Name-based: elements with name attributes
 * 3. Custom attribute (data-testid): elements with data-testid
 * 4. Ancestor anchor: elements inside containers with id/data-testid
 * 5. Sibling list: groups of same-tag siblings
 * 6. Text-based: elements identifiable by visible text
 */
export default function LocatorStrategies() {
  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        Locator Strategies
      </Typography>
      <Typography variant="body1" paragraph>
        This page tests all recorder locator strategies. Each section targets a specific strategy.
      </Typography>

      {/* ===== 1. ID-based Locator ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>1. ID-based Locator</Typography>
        <Typography variant="body2" paragraph>Elements located by unique id attribute → By.id()</Typography>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
          <Button id="unique-submit-btn" variant="contained">Submit</Button>
          <Button id="unique-cancel-btn" variant="outlined">Cancel</Button>
          <TextField id="unique-username" label="Username" variant="outlined" />
          <TextField id="unique-email" label="Email" variant="outlined" />
        </Box>
      </Paper>

      {/* ===== 2. Name-based Locator ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>2. Name-based Locator</Typography>
        <Typography variant="body2" paragraph>Elements located by name attribute → By.name()</Typography>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, maxWidth: 400 }}>
          <TextField name="firstName" label="First Name" variant="outlined" />
          <TextField name="lastName" label="Last Name" variant="outlined" />
          <TextField name="phone" label="Phone" variant="outlined" />
        </Box>
        <Box sx={{ mt: 2 }}>
          <RadioGroup name="contactMethod">
            <FormControlLabel name="contactMethod" value="email" control={<Radio />} label="Email" />
            <FormControlLabel name="contactMethod" value="phone" control={<Radio />} label="Phone" />
            <FormControlLabel name="contactMethod" value="sms" control={<Radio />} label="SMS" />
          </RadioGroup>
        </Box>
      </Paper>

      {/* ===== 3. Custom Attribute (data-testid) ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>3. Custom Attribute (data-testid)</Typography>
        <Typography variant="body2" paragraph>
          Elements with data-testid → By.cssSelector([data-testid="value"])
        </Typography>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
          <Button data-testid="action-save" variant="contained">Save</Button>
          <Button data-testid="action-delete" variant="outlined" color="error">Delete</Button>
          <Button data-testid="action-export" variant="text">Export</Button>
          <TextField data-testid="search-input" label="Search" variant="outlined" />
          <Checkbox data-testid="agree-terms" />
          <Typography data-testid="status-label">Status: Active</Typography>
        </Box>
      </Paper>

      {/* ===== 4. Ancestor Anchor Locator ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>4. Ancestor Anchor Locator</Typography>
        <Typography variant="body2" paragraph>
          Elements inside containers with id or data-testid → ancestor + child path
        </Typography>

        <Box id="user-profile-section" sx={{ p: 2, border: '1px solid #ccc', mb: 2, borderRadius: 1 }}>
          <Typography variant="h6">User Profile Section</Typography>
          <Button variant="contained">Edit Profile</Button>
          <Button variant="outlined">View History</Button>
          <TextField label="Display Name" variant="outlined" sx={{ mt: 1, display: 'block' }} />
        </Box>

        <Box data-testid="order-section" sx={{ p: 2, border: '1px solid #ccc', mb: 2, borderRadius: 1 }}>
          <Typography variant="h6">Order Section</Typography>
          <Button variant="contained">Place Order</Button>
          <Button variant="outlined">View Cart</Button>
          <TextField label="Coupon Code" variant="outlined" sx={{ mt: 1, display: 'block' }} />
        </Box>

        <Box id="settings-section" sx={{ p: 2, border: '1px solid #ccc', borderRadius: 1 }}>
          <Typography variant="h6">Settings Section</Typography>
          <Button variant="contained">Apply</Button>
          <Button variant="outlined">Reset</Button>
          <TextField label="API Key" variant="outlined" sx={{ mt: 1, display: 'block' }} />
        </Box>
      </Paper>

      {/* ===== 5. Sibling List Locator ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>5. Sibling List Locator</Typography>
        <Typography variant="body2" paragraph>
          Groups of same-tag siblings → List locator with findComponents()
        </Typography>

        <Box id="button-group-test">
          <Typography variant="subtitle1">Button Siblings:</Typography>
          <Box sx={{ display: 'flex', gap: 1, mb: 2 }}>
            <Button variant="outlined">Action A</Button>
            <Button variant="outlined">Action B</Button>
            <Button variant="outlined">Action C</Button>
            <Button variant="outlined">Action D</Button>
          </Box>
        </Box>

        <Box id="input-group-test">
          <Typography variant="subtitle1">Input Siblings:</Typography>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1, maxWidth: 300, mb: 2 }}>
            <TextField label="Field 1" variant="outlined" size="small" />
            <TextField label="Field 2" variant="outlined" size="small" />
            <TextField label="Field 3" variant="outlined" size="small" />
          </Box>
        </Box>

        <Box id="checkbox-group-test">
          <Typography variant="subtitle1">Checkbox Siblings:</Typography>
          <Box>
            <FormControlLabel control={<Checkbox />} label="Option Alpha" />
            <FormControlLabel control={<Checkbox />} label="Option Beta" />
            <FormControlLabel control={<Checkbox />} label="Option Gamma" />
          </Box>
        </Box>
      </Paper>

      {/* ===== 6. Text-based Locator ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>6. Text-based Locator</Typography>
        <Typography variant="body2" paragraph>
          Elements identifiable by visible text → XPath with normalize-space()
        </Typography>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
          <Button variant="contained">Confirm Changes</Button>
          <Button variant="outlined">Discard Draft</Button>
          <Button variant="text">Learn More</Button>
        </Box>
      </Paper>

      {/* ===== 7. Mixed Strategy Section ===== */}
      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>7. Mixed Strategies</Typography>
        <Typography variant="body2" paragraph>
          Elements with multiple locator attributes for strategy priority testing
        </Typography>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
          <Button id="mixed-1" name="primaryAction" data-testid="btn-primary" variant="contained">
            Primary Action
          </Button>
          <TextField
            id="mixed-2"
            name="searchQuery"
            data-testid="search-field"
            label="Search Query"
            variant="outlined"
          />
          <Checkbox id="mixed-3" name="acceptTerms" data-testid="terms-checkbox" />
        </Box>
      </Paper>
    </>
  )
}
