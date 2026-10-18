import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';

// Load Material Icons font at runtime (avoids build-time inlining issues)
const link = document.createElement('link');
link.rel = 'stylesheet';
link.href = 'https://fonts.googleapis.com/icon?family=Material+Icons';
document.head.appendChild(link);

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
