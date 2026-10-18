import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

const menuItems = [
  { path: '/', label: 'Home', icon: 'home' },
  { path: '/inputs', label: 'Inputs', icon: 'text_fields' },
  { path: '/data-display', label: 'Data Display', icon: 'view_list' },
  { path: '/feedback', label: 'Feedback', icon: 'notifications' },
  { path: '/navigation', label: 'Navigation', icon: 'navigation' },
  { path: '/surfaces', label: 'Surfaces', icon: 'layers' },
  { path: '/locator-strategies', label: 'Locator Strategies', icon: 'my_location' },
  { path: '/html-elements', label: 'HTML Elements', icon: 'code' },
  { path: '/users/1', label: 'Dynamic Route', icon: 'route' },
];

@Component({
  imports: [
    RouterOutlet, RouterLink, RouterLinkActive,
    MatToolbarModule, MatSidenavModule, MatListModule, MatIconModule, MatButtonModule, MatDividerModule,
  ],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('hamster-recorder-test-app-mat');
  protected readonly menuItems = menuItems;
}
