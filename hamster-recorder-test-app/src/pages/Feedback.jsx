import { useState } from 'react'
import {
  Typography, Box, Button, Dialog, DialogTitle, DialogContent,
  DialogContentText, DialogActions, Snackbar, Alert, Backdrop,
  CircularProgress, LinearProgress, Stack,
} from '@mui/material'

export default function Feedback() {
  const [dialogOpen, setDialogOpen] = useState(false)
  const [snackOpen, setSnackOpen] = useState(false)
  const [backdropOpen, setBackdropOpen] = useState(false)

  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        Feedback
      </Typography>

      {/* ===== Dialog Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Dialog</Typography>
        <Button id="btn-open-dialog" variant="contained" onClick={() => setDialogOpen(true)}>
          Open Dialog
        </Button>
        <Dialog id="test-dialog" open={dialogOpen} onClose={() => setDialogOpen(false)}>
          <DialogTitle>Test Dialog</DialogTitle>
          <DialogContent>
            <DialogContentText>
              This is a test dialog for recorder validation. It contains text and action buttons.
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button id="dialog-cancel" onClick={() => setDialogOpen(false)}>Cancel</Button>
            <Button id="dialog-confirm" variant="contained" onClick={() => setDialogOpen(false)}>
              Confirm
            </Button>
          </DialogActions>
        </Dialog>
      </Box>

      {/* ===== Snackbar Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Snackbar</Typography>
        <Stack direction="row" spacing={2}>
          <Button id="btn-open-snackbar" variant="contained" onClick={() => setSnackOpen(true)}>
            Show Snackbar
          </Button>
        </Stack>
        <Snackbar
          id="test-snackbar"
          open={snackOpen}
          autoHideDuration={3000}
          onClose={() => setSnackOpen(false)}
          message="Operation completed successfully"
        />
      </Box>

      {/* ===== Alert Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Alert</Typography>
        <Stack spacing={1} sx={{ maxWidth: 500 }}>
          <Alert id="alert-success" severity="success">Success alert message</Alert>
          <Alert id="alert-info" severity="info">Info alert message</Alert>
          <Alert id="alert-warning" severity="warning">Warning alert message</Alert>
          <Alert id="alert-error" severity="error">Error alert message</Alert>
          <Alert id="alert-filled" severity="error" variant="filled">Filled alert message</Alert>
          <Alert id="alert-outlined" severity="warning" variant="outlined">Outlined alert message</Alert>
        </Stack>
      </Box>

      {/* ===== Backdrop Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Backdrop</Typography>
        <Button id="btn-open-backdrop" variant="contained" onClick={() => setBackdropOpen(true)}>
          Show Backdrop
        </Button>
        <Backdrop open={backdropOpen} sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
          <CircularProgress id="backdrop-spinner" color="inherit" />
        </Backdrop>
      </Box>

      {/* ===== Progress Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Circular Progress</Typography>
        <Stack direction="row" spacing={3} sx={{ mb: 3 }}>
          <CircularProgress id="circular-default" />
          <CircularProgress id="circular-25" value={25} variant="determinate" />
          <CircularProgress id="circular-75" value={75} variant="determinate" color="secondary" />
        </Stack>

        <Typography variant="h5" gutterBottom>Linear Progress</Typography>
        <Stack spacing={2} sx={{ maxWidth: 500 }}>
          <LinearProgress id="linear-default" />
          <LinearProgress id="linear-50" variant="determinate" value={50} />
          <LinearProgress id="linear-75" variant="determinate" value={75} color="secondary" />
        </Stack>
      </Box>
    </>
  )
}
