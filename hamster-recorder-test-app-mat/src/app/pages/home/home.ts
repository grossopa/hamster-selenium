import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';

const sections = [
  { path: '/inputs', title: 'Inputs', desc: 'Button, TextField, Select, Checkbox, Radio, SlideToggle, Slider, ButtonToggle, Fab, Icon' },
  { path: '/data-display', title: 'Data Display', desc: 'Avatar, Badge, Chip, List, Divider, Table' },
  { path: '/feedback', title: 'Feedback', desc: 'Dialog, Snackbar, Alert, BottomSheet, ProgressSpinner, ProgressBar' },
  { path: '/navigation', title: 'Navigation', desc: 'Tabs, Accordion, Menu, Breadcrumbs, Stepper' },
  { path: '/surfaces', title: 'Surfaces', desc: 'Card, Paper (Elevation), Toolbar' },
  { path: '/locator-strategies', title: 'Locator Strategies', desc: 'id, name, data-testid, ancestor anchor, sibling list, text-based' },
  { path: '/html-elements', title: 'HTML Elements', desc: 'Native form, table, link, textarea, select' },
  { path: '/users/1', title: 'Dynamic Route', desc: 'Tests ContextPathPageStrategy with /users/:id and /products/:id' },
];

@Component({
  imports: [CommonModule, RouterLink, MatCardModule],
  selector: 'app-home',
  templateUrl: './home.html',
})
export class HomeComponent {
  protected readonly sections = sections;
}
