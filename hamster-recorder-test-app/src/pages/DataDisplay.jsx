import {
  Typography, Box, Avatar, AvatarGroup, Badge, Chip, Stack,
  List, ListItem, ListItemText, ListItemIcon, ListItemButton,
  Divider, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
} from '@mui/material'
import {
  Person, Email, Phone, ShoppingCart, Star, Favorite,
  CheckCircle, Cancel,
} from '@mui/icons-material'

const tableRows = [
  { id: 1, name: 'Alice', email: 'alice@example.com', role: 'Admin' },
  { id: 2, name: 'Bob', email: 'bob@example.com', role: 'User' },
  { id: 3, name: 'Charlie', email: 'charlie@example.com', role: 'Editor' },
  { id: 4, name: 'Diana', email: 'diana@example.com', role: 'User' },
]

export default function DataDisplay() {
  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        Data Display
      </Typography>

      {/* ===== Avatar Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Avatar</Typography>
        <Stack direction="row" spacing={2} sx={{ mb: 2 }}>
          <Avatar id="avatar-image" src="/broken-image.jpg" />
          <Avatar id="avatar-letter">A</Avatar>
          <Avatar id="avatar-icon" sx={{ bgcolor: 'primary.main' }}><Person /></Avatar>
        </Stack>
        <AvatarGroup id="avatar-group" max={4}>
          <Avatar>A</Avatar>
          <Avatar>B</Avatar>
          <Avatar>C</Avatar>
          <Avatar>D</Avatar>
          <Avatar>E</Avatar>
        </AvatarGroup>
      </Box>

      {/* ===== Badge Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Badge</Typography>
        <Stack direction="row" spacing={4}>
          <Badge id="badge-cart" badgeContent={4} color="primary">
            <ShoppingCart />
          </Badge>
          <Badge id="badge-dot" variant="dot" color="error">
            <Person />
          </Badge>
          <Badge id="badge-zero" badgeContent={0} color="primary" showZero>
            <Email />
          </Badge>
        </Stack>
      </Box>

      {/* ===== Chip Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Chip</Typography>
        <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap', mb: 2 }}>
          <Chip id="chip-basic" label="Basic" />
          <Chip id="chip-color" label="Primary" color="primary" />
          <Chip id="chip-outlined" label="Outlined" variant="outlined" />
          <Chip id="chip-clickable" label="Clickable" color="secondary" onClick={() => {}} />
          <Chip id="chip-deletable" label="Deletable" onDelete={() => {}} />
        </Stack>
        <Stack direction="row" spacing={1}>
          <Chip id="chip-success" label="Success" color="success" icon={<CheckCircle />} />
          <Chip id="chip-error" label="Error" color="error" icon={<Cancel />} />
          <Chip id="chip-star" label="Starred" color="warning" icon={<Star />} />
        </Stack>
      </Box>

      {/* ===== List Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>List</Typography>
        <Paper sx={{ maxWidth: 360 }}>
          <List id="list-contacts">
            <ListItem disablePadding>
              <ListItemButton id="list-item-alice">
                <ListItemIcon><Person /></ListItemIcon>
                <ListItemText primary="Alice" secondary="alice@example.com" />
              </ListItemButton>
            </ListItem>
            <Divider />
            <ListItem disablePadding>
              <ListItemButton id="list-item-bob">
                <ListItemIcon><Person /></ListItemIcon>
                <ListItemText primary="Bob" secondary="bob@example.com" />
              </ListItemButton>
            </ListItem>
            <Divider />
            <ListItem disablePadding>
              <ListItemButton id="list-item-charlie">
                <ListItemIcon><Person /></ListItemIcon>
                <ListItemText primary="Charlie" secondary="charlie@example.com" />
              </ListItemButton>
            </ListItem>
          </List>
        </Paper>
      </Box>

      {/* ===== Divider Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Divider</Typography>
        <Box sx={{ maxWidth: 400 }}>
          <Typography>Content above</Typography>
          <Divider id="divider-basic" />
          <Typography>Content below</Typography>
          <Divider id="divider-text">
            <Favorite color="error" />
          </Divider>
          <Typography>More content</Typography>
        </Box>
      </Box>

      {/* ===== Table Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Table</Typography>
        <TableContainer component={Paper} sx={{ maxWidth: 650 }}>
          <Table id="users-table" aria-label="users table">
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Email</TableCell>
                <TableCell>Role</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {tableRows.map((row) => (
                <TableRow key={row.id} id={`table-row-${row.id}`}>
                  <TableCell>{row.id}</TableCell>
                  <TableCell>{row.name}</TableCell>
                  <TableCell>{row.email}</TableCell>
                  <TableCell>{row.role}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    </>
  )
}
