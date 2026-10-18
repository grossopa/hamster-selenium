import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule, MatDialogActions, MatDialogContent, MatDialogTitle } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatBottomSheet, MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatListModule } from '@angular/material/list';

@Component({
  imports: [
    CommonModule,
    MatButtonModule, MatDialogModule, MatSnackBarModule,
    MatProgressSpinnerModule, MatProgressBarModule, MatCardModule, MatIconModule,
    MatBottomSheetModule,
  ],
  selector: 'app-feedback',
  templateUrl: './feedback.html',
})
export class FeedbackComponent {
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);
  private bottomSheet = inject(MatBottomSheet);

  openDialog() {
    this.dialog.open(TestDialogComponent);
  }

  openSnackbar() {
    this.snackBar.open('Operation completed successfully', 'Close', { duration: 3000 });
  }

  openBottomSheet() {
    this.bottomSheet.open(TestBottomSheetComponent);
  }
}

@Component({
  imports: [MatButtonModule, MatDialogModule, MatDialogTitle, MatDialogContent, MatDialogActions],
  selector: 'app-test-dialog',
  template: `
    <h2 mat-dialog-title id="test-dialog-title">Test Dialog</h2>
    <mat-dialog-content id="test-dialog-content">
      This is a test dialog for recorder validation. It contains text and action buttons.
    </mat-dialog-content>
    <mat-dialog-actions id="test-dialog-actions">
      <button mat-button id="dialog-cancel" [mat-dialog-close]>Cancel</button>
      <button mat-raised-button id="dialog-confirm" color="primary" [mat-dialog-close]>Confirm</button>
    </mat-dialog-actions>
  `,
})
export class TestDialogComponent {}

@Component({
  imports: [MatListModule],
  selector: 'app-test-bottom-sheet',
  template: `
    <mat-nav-list>
      <a mat-list-item (click)="$event.preventDefault()">
        <span matListItemTitle>Option 1</span>
        <span matListItemLine>Description for option 1</span>
      </a>
      <a mat-list-item (click)="$event.preventDefault()">
        <span matListItemTitle>Option 2</span>
        <span matListItemLine>Description for option 2</span>
      </a>
    </mat-nav-list>
  `,
})
export class TestBottomSheetComponent {}
