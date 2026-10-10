import {
  Typography, Box, Card, CardContent, CardMedia, CardActions, Button,
  Grid, Paper, Toolbar, IconButton,
} from '@mui/material'
import { Favorite, Share } from '@mui/icons-material'

export default function Surfaces() {
  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        Surfaces
      </Typography>

      {/* ===== Card Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Cards</Typography>
        <Grid container spacing={3}>
          <Grid item xs={12} sm={6} md={4}>
            <Card id="card-basic">
              <CardContent>
                <Typography variant="h6" gutterBottom>Basic Card</Typography>
                <Typography variant="body2" color="text.secondary">
                  A basic card with text content. Cards are surfaces that display content and actions.
                </Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} sm={6} md={4}>
            <Card id="card-with-media">
              <CardMedia
                id="card-media"
                component="div"
                sx={{ height: 140, bgcolor: 'primary.light', display: 'flex', alignItems: 'center', justifyContent: 'center' }}
              >
                <Typography variant="h3" color="white">M</Typography>
              </CardMedia>
              <CardContent>
                <Typography variant="h6" gutterBottom>Card with Media</Typography>
                <Typography variant="body2" color="text.secondary">
                  This card includes a media area and action buttons.
                </Typography>
              </CardContent>
              <CardActions>
                <Button id="card-btn-like" size="small" startIcon={<Favorite />}>Like</Button>
                <Button id="card-btn-share" size="small" startIcon={<Share />}>Share</Button>
              </CardActions>
            </Card>
          </Grid>
          <Grid item xs={12} sm={6} md={4}>
            <Card id="card-outlined" variant="outlined">
              <CardContent>
                <Typography variant="h6" gutterBottom>Outlined Card</Typography>
                <Typography variant="body2" color="text.secondary">
                  An outlined card variant with a border instead of shadow.
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Box>

      {/* ===== Paper Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Paper</Typography>
        <Grid container spacing={3}>
          {[1, 2, 4, 8, 16, 24].map((elevation) => (
            <Grid item xs={6} sm={4} md={2} key={elevation}>
              <Paper
                id={`paper-elevation-${elevation}`}
                elevation={elevation}
                sx={{ p: 2, textAlign: 'center' }}
              >
                <Typography variant="body2">Elevation {elevation}</Typography>
              </Paper>
            </Grid>
          ))}
        </Grid>
      </Box>

      {/* ===== Toolbar Section ===== */}
      <Box sx={{ mb: 4 }}>
        <Typography variant="h5" gutterBottom>Toolbar</Typography>
        <Paper>
          <Toolbar id="toolbar-basic" variant="dense">
            <Typography variant="h6" sx={{ flexGrow: 1 }}>Toolbar Title</Typography>
            <IconButton id="toolbar-btn-fav" color="primary"><Favorite /></IconButton>
            <IconButton id="toolbar-btn-share" color="primary"><Share /></IconButton>
          </Toolbar>
        </Paper>
      </Box>
    </>
  )
}
