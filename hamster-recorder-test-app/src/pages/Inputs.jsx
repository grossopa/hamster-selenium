import { useState } from 'react'
import {
  Typography, Box, TextField, Button, ButtonGroup,
  Checkbox, FormControlLabel, Radio, RadioGroup,
  Switch, Slider, Rating, Fab, Select, MenuItem,
  FormControl, InputLabel, FormHelperText, IconButton,
} from '@mui/material'
import { Add, Edit, Delete, Send } from '@mui/icons-material'

export default function Inputs() {
  const [text, setText] = useState('')
  const [selectVal, setSelectVal] = useState('')
  const [radioVal, setRadioVal] = useState('female')
  const [checkVal, setCheckVal] = useState(true)
  const [switchVal, setSwitchVal] = useState(false)
  const [sliderVal, setSliderVal] = useState(30)
  const [ratingVal, setRatingVal] = useState(3)

  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        Inputs
      </Typography>

      {/* ===== Button Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Buttons</Typography>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', mb: 2 }}>
          <Button id="btn-contained" variant="contained">Contained</Button>
          <Button id="btn-outlined" variant="outlined">Outlined</Button>
          <Button id="btn-text" variant="text">Text</Button>
          <Button id="btn-disabled" disabled>Disabled</Button>
          <Button id="btn-with-icon" startIcon={<Send />}>Send</Button>
        </Box>
        <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
          <IconButton id="icon-btn-add" color="primary"><Add /></IconButton>
          <IconButton id="icon-btn-edit" color="secondary"><Edit /></IconButton>
          <IconButton id="icon-btn-delete" color="error"><Delete /></IconButton>
        </Box>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <ButtonGroup variant="contained">
            <Button id="btn-group-left">Left</Button>
            <Button id="btn-group-center">Center</Button>
            <Button id="btn-group-right">Right</Button>
          </ButtonGroup>
        </Box>
      </Box>

      {/* ===== Fab Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Floating Action Button</Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <Fab id="fab-primary" color="primary" size="medium"><Add /></Fab>
          <Fab id="fab-secondary" color="secondary" size="small"><Edit /></Fab>
          <Fab id="fab-extended" variant="extended"><Add />Extended</Fab>
        </Box>
      </Box>

      {/* ===== TextField Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Text Fields</Typography>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, maxWidth: 400 }}>
          <TextField
            id="tf-standard"
            label="Standard"
            variant="standard"
            value={text}
            onChange={(e) => setText(e.target.value)}
            helperText="Standard variant"
          />
          <TextField id="tf-outlined" label="Outlined" variant="outlined" defaultValue="Default value" />
          <TextField id="tf-filled" label="Filled" variant="filled" />
          <TextField id="tf-password" label="Password" type="password" />
          <TextField id="tf-disabled" label="Disabled" disabled defaultValue="Cannot edit" />
          <TextField
            id="tf-error"
            label="Error"
            error
            defaultValue="Error state"
            helperText="This field has an error"
          />
          <TextField
            id="tf-multiline"
            label="Multiline"
            multiline
            rows={3}
            defaultValue="Multiline text"
          />
          <TextField id="tf-helper-text" label="With Helper" helperText="Helper text below" />
          <TextField id="tf-required" label="Required" required />
        </Box>
      </Box>

      {/* ===== Select Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Select</Typography>
        <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
          <FormControl sx={{ minWidth: 200 }}>
            <InputLabel id="select-label-1">Framework</InputLabel>
            <Select
              id="select-framework"
              name="framework"
              labelId="select-label-1"
              value={selectVal}
              label="Framework"
              onChange={(e) => setSelectVal(e.target.value)}
            >
              <MenuItem value="">None</MenuItem>
              <MenuItem value="react">React</MenuItem>
              <MenuItem value="vue">Vue</MenuItem>
              <MenuItem value="angular">Angular</MenuItem>
            </Select>
            <FormHelperText>Select a framework</FormHelperText>
          </FormControl>

          <FormControl sx={{ minWidth: 200 }}>
            <InputLabel id="select-label-2">Size</InputLabel>
            <Select id="select-size" name="size" labelId="select-label-2" defaultValue="m" label="Size">
              <MenuItem value="xs">Extra Small</MenuItem>
              <MenuItem value="s">Small</MenuItem>
              <MenuItem value="m">Medium</MenuItem>
              <MenuItem value="l">Large</MenuItem>
            </Select>
          </FormControl>
        </Box>
      </Box>

      {/* ===== Checkbox & Radio Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Checkbox &amp; Radio</Typography>
        <Box sx={{ display: 'flex', gap: 3, flexWrap: 'wrap' }}>
          <Box>
            <FormControlLabel
              id="checkbox-basic"
              control={<Checkbox checked={checkVal} onChange={(e) => setCheckVal(e.target.checked)} />}
              label="Basic checkbox"
            />
            <FormControlLabel
              id="checkbox-disabled"
              control={<Checkbox disabled />}
              label="Disabled"
            />
            <FormControlLabel
              id="checkbox-checked"
              control={<Checkbox defaultChecked />}
              label="Checked"
            />
          </Box>
          <Box>
            <RadioGroup
              id="radio-group"
              name="gender"
              value={radioVal}
              onChange={(e) => setRadioVal(e.target.value)}
            >
              <FormControlLabel id="radio-female" value="female" control={<Radio />} label="Female" />
              <FormControlLabel id="radio-male" value="male" control={<Radio />} label="Male" />
              <FormControlLabel id="radio-other" value="other" control={<Radio />} label="Other" />
            </RadioGroup>
          </Box>
        </Box>
      </Box>

      {/* ===== Switch Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Switch</Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <FormControlLabel
            id="switch-basic"
            control={<Switch checked={switchVal} onChange={(e) => setSwitchVal(e.target.checked)} />}
            label="Basic switch"
          />
          <FormControlLabel id="switch-disabled" control={<Switch disabled />} label="Disabled" />
          <FormControlLabel id="switch-checked" control={<Switch defaultChecked />} label="Checked" />
        </Box>
      </Box>

      {/* ===== Slider Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Slider</Typography>
        <Box sx={{ maxWidth: 400 }}>
          <Slider
            id="slider-basic"
            value={sliderVal}
            onChange={(_, v) => setSliderVal(v)}
            aria-label="Basic slider"
          />
          <Slider id="slider-disabled" defaultValue={50} disabled aria-label="Disabled slider" />
          <Slider id="slider-marks" defaultValue={[20, 60]} marks step={10} aria-label="Marks slider" />
        </Box>
      </Box>

      {/* ===== Rating Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Rating</Typography>
        <Box sx={{ display: 'flex', gap: 3 }}>
          <Box>
            <Typography gutterBottom>Basic</Typography>
            <Rating id="rating-basic" value={ratingVal} onChange={(_, v) => setRatingVal(v)} />
          </Box>
          <Box>
            <Typography gutterBottom>Read Only</Typography>
            <Rating id="rating-readonly" value={4} readOnly />
          </Box>
          <Box>
            <Typography gutterBottom>Disabled</Typography>
            <Rating id="rating-disabled" value={2} disabled />
          </Box>
        </Box>
      </Box>
    </>
  )
}
