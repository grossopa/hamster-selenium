import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./pages/home/home').then(m => m.HomeComponent) },
  { path: 'inputs', loadComponent: () => import('./pages/inputs/inputs').then(m => m.InputsComponent) },
  { path: 'data-display', loadComponent: () => import('./pages/data-display/data-display').then(m => m.DataDisplayComponent) },
  { path: 'feedback', loadComponent: () => import('./pages/feedback/feedback').then(m => m.FeedbackComponent) },
  { path: 'navigation', loadComponent: () => import('./pages/navigation/navigation').then(m => m.NavigationComponent) },
  { path: 'surfaces', loadComponent: () => import('./pages/surfaces/surfaces').then(m => m.SurfacesComponent) },
  { path: 'locator-strategies', loadComponent: () => import('./pages/locator-strategies/locator-strategies').then(m => m.LocatorStrategiesComponent) },
  { path: 'html-elements', loadComponent: () => import('./pages/html-elements/html-elements').then(m => m.HtmlElementsComponent) },
  { path: 'users/:id', loadComponent: () => import('./pages/dynamic-route/dynamic-route').then(m => m.DynamicRouteComponent) },
  { path: 'products/:id', loadComponent: () => import('./pages/dynamic-route/dynamic-route').then(m => m.DynamicRouteComponent) },
  { path: '**', redirectTo: '' },
];
