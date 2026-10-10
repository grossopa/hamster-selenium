import { Typography, Grid, Card, CardContent, CardActionArea, Link } from '@mui/material'
import { useNavigate } from 'react-router-dom'

const sections = [
  { path: '/inputs', title: 'Inputs', desc: 'Button, TextField, Select, Checkbox, Radio, Switch, Slider, Rating, Fab, ButtonGroup' },
  { path: '/data-display', title: 'Data Display', desc: 'Avatar, Badge, Chip, List, Divider, Table' },
  { path: '/feedback', title: 'Feedback', desc: 'Dialog, Snackbar, Alert, Backdrop, CircularProgress, LinearProgress' },
  { path: '/navigation', title: 'Navigation', desc: 'Tabs, Accordion, Menu, Breadcrumbs, BottomNavigation, Stepper, Drawer, Link' },
  { path: '/surfaces', title: 'Surfaces', desc: 'AppBar, Card, Toolbar' },
  { path: '/locator-strategies', title: 'Locator Strategies', desc: 'id, name, data-testid, ancestor anchor, sibling list, text-based' },
  { path: '/html-elements', title: 'HTML Elements', desc: 'Native form, table, link, textarea, select' },
  { path: '/users/1', title: 'Dynamic Route', desc: 'Tests ContextPathPageStrategy with /users/:id and /products/:id' },
]

export default function Home() {
  const navigate = useNavigate()

  return (
    <>
      <Typography variant="h4" gutterBottom id="page-title" name="pageTitle">
        Hamster Recorder Test App
      </Typography>
      <Typography variant="body1" paragraph>
        A comprehensive MUI v5 test application for validating Hamster Selenium Recorder features.
        Navigate through the sidebar or click the cards below.
      </Typography>
      <Grid container spacing={3}>
        {sections.map((s) => (
          <Grid item xs={12} sm={6} md={4} key={s.path}>
            <Card>
              <CardActionArea onClick={() => navigate(s.path)}>
                <CardContent>
                  <Typography variant="h6" gutterBottom>{s.title}</Typography>
                  <Typography variant="body2" color="text.secondary">{s.desc}</Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        ))}
      </Grid>
    </>
  )
}
