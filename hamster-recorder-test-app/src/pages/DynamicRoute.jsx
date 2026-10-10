import { useParams } from 'react-router-dom'
import { Typography, Box, Paper, Button, TextField, Chip, Stack } from '@mui/material'

/**
 * Tests ContextPathPageStrategy with dynamic routes.
 * Both /users/:id and /products/:id should collapse numeric segments to {id}.
 * e.g., /users/1 → /users/{id}, /products/42 → /products/{id}
 */
export default function DynamicRoute() {
  const { id } = useParams()
  const isUser = window.location.pathname.startsWith('/users')
  const entity = isUser ? 'User' : 'Product'

  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        {entity} Detail
      </Typography>
      <Typography variant="body1" paragraph>
        Current path: <code>{window.location.pathname}</code> — ID: <strong>{id}</strong>
      </Typography>

      <Paper sx={{ p: 2, mb: 3 }}>
        <Typography variant="h5" gutterBottom>{entity} #{id} Information</Typography>
        <Stack direction="row" spacing={1} sx={{ mb: 2 }}>
          <Chip id="entity-type-chip" label={entity} color="primary" />
          <Chip id="entity-id-chip" label={`ID: ${id}`} variant="outlined" />
        </Stack>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, maxWidth: 400 }}>
          <TextField id={`${entity.toLowerCase()}-name`} label={`${entity} Name`} defaultValue={`${entity} ${id}`} />
          <TextField id={`${entity.toLowerCase()}-desc`} label="Description" multiline rows={2} defaultValue={`Description for ${entity} ${id}`} />
          <Box sx={{ display: 'flex', gap: 1 }}>
            <Button id="btn-save-entity" variant="contained">Save</Button>
            <Button id="btn-delete-entity" variant="outlined" color="error">Delete</Button>
          </Box>
        </Box>
      </Paper>

      <Paper sx={{ p: 2 }}>
        <Typography variant="h6" gutterBottom>Navigate to Other IDs</Typography>
        <Stack direction="row" spacing={1}>
          {isUser ? (
            <>
              <Button href="/users/1" variant="outlined" size="small">User 1</Button>
              <Button href="/users/2" variant="outlined" size="small">User 2</Button>
              <Button href="/users/99" variant="outlined" size="small">User 99</Button>
            </>
          ) : (
            <>
              <Button href="/products/1" variant="outlined" size="small">Product 1</Button>
              <Button href="/products/2" variant="outlined" size="small">Product 2</Button>
              <Button href="/products/99" variant="outlined" size="small">Product 99</Button>
            </>
          )}
        </Stack>
      </Paper>
    </>
  )
}
