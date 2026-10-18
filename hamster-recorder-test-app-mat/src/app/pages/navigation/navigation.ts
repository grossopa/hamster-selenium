import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatStepperModule } from '@angular/material/stepper';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  imports: [
    CommonModule, FormsModule, RouterModule,
    MatTabsModule, MatExpansionModule, MatMenuModule,
    MatButtonModule, MatStepperModule, MatCardModule, MatIconModule,
  ],
  selector: 'app-navigation',
  templateUrl: './navigation.html',
})
export class NavigationComponent {
  stepperIndex = 0;
  @ViewChild('stepper') stepper: any;

  steps = [
    { label: 'Select campaign settings', description: 'Choose the campaign type and configure basic settings.' },
    { label: 'Create an ad group', description: 'An ad group contains one or more ads.' },
    { label: 'Create an ad', description: 'Write your ad copy and add media.' },
  ];
}
